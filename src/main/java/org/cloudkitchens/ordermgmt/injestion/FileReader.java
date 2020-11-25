package org.cloudkitchens.ordermgmt.injestion;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cloudkitchens.ordermgmt.data.InputFile;
import org.cloudkitchens.ordermgmt.data.InputFileService;
import org.cloudkitchens.ordermgmt.data.Order;
import org.cloudkitchens.ordermgmt.data.OrderService;
import org.cloudkitchens.ordermgmt.engine.OrderQueue;
import org.cloudkitchens.ordermgmt.exceptions.JsonFileFormatException;
import org.cloudkitchens.ordermgmt.templates.OrdersFormatDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileReader implements Runnable, WatcherCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    Deque<Path> inputFiles = new ArrayDeque<>();

    private final int thread_sleep = 1000;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InputFileService inputFileService;

    @Autowired
    private OrderQueue orderQueue;

    private OrdersFormatDescriptor ordersFormatDescriptor;

    private int recordTracker = 0;

    public FileReader() {

        ordersFormatDescriptor = new OrdersFormatDescriptor();
    }

    @Override
    public void process(Path inputFile) {

        String iFile = inputFile.toString();

        if(inputFileService.containsFile(iFile)
                && inputFileService.getInputFile(iFile).getStatus() >= 2) return;

        inputFileService.insertRecord(new InputFile(iFile));

        inputFiles.add(inputFile);

    }

    @Override
    public void run() {

        try {

            for(;;) {

                while(!inputFiles.isEmpty()){

                    if(Thread.interrupted()) throw new InterruptedException();

                    recordTracker = 0;

                    parseFile(inputFiles.poll());

                }

                Thread.sleep(thread_sleep);
            }

        } catch (InterruptedException  interruptedException) {

            LOGGER.info("Thread Interrupted");
        }
    }

    private void parseFile(Path inputFile) {

        try {

            String iFile = inputFile.toString();

            inputFileService.updateRecord(new InputFile(iFile, 1));

            File jsonFile = new File(iFile);

            JsonFactory jsonFactory = new JsonFactory();

            JsonParser jsonParser = jsonFactory.createParser(jsonFile);

            JsonToken jsonToken = jsonParser.nextToken();

            Map<String, String> hmap = new HashMap<>();

            if (jsonToken == JsonToken.START_ARRAY) {

                jsonToken = jsonParser.nextToken();

                while (jsonToken != JsonToken.END_ARRAY) {

                    recordTracker++;

                    if (jsonToken == JsonToken.START_OBJECT) {

                        hmap.clear();

                        jsonToken = jsonParser.nextToken();

                        while (jsonToken != jsonToken.END_OBJECT) {

                            String key = jsonParser.getCurrentName();

                            jsonParser.nextToken();

                            String value = jsonParser.getText();

                            hmap.put(key, value);

                            jsonToken = jsonParser.nextToken();

                            System.out.println(hmap.toString());

                        }

                        jsonToken = jsonParser.nextToken();
                    } else {

                        jsonToken = jsonParser.nextToken();
                    }

                    updateOrders(hmap);

                }

                inputFileService.updateRecord(new InputFile(inputFile.toString(), 2));

            } else {

                throw new JsonFileFormatException("Incorrect file format. JsonArray token missing"
                        + "in the beginning of the file");

            }

        } catch (Exception exception) {

            inputFileService.updateRecord(new InputFile(inputFile.toString(), 3));

            LOGGER.error("Error in file {} while parsing. Exception Reason: {}", inputFile.toString(),
                                    exception.getMessage());

        }

    }


    private void updateOrders(Map<String, String> ordersMap) throws JsonFileFormatException {

        Order order = orderService.getOrder(ordersMap.get("id"));

        if(order != null) {

            if(order.getStatus() <= 2) {

                orderQueue.getQueue().add(order);
            }

            return;
        }

        List<String> keys = ordersFormatDescriptor.getKeys();

        String id = null, name = null, temp = null;
        int shelfLife = -1;
        double decayRate = 0.0;

        for(String key : keys) {

            if(!ordersMap.containsKey(key)) throw new JsonFileFormatException(
                    String.format("Expected keys are not present in record %d", recordTracker));


            switch(key) {

                case "id":
                    id = ordersMap.get(key);
                    break;
                case "name":
                    name = ordersMap.get(key);
                    break;
                case "temp":
                    temp = ordersMap.get(key);
                    break;
                case "shelfLife":
                    shelfLife = Integer.parseInt(ordersMap.get(key));
                    break;
                case "decayRate":
                    decayRate = Double.parseDouble(ordersMap.get(key));
                    break;
            }


        }

        order = new Order(id, name, temp, shelfLife, decayRate);

        synchronized (this) {

            orderService.insertOrder(order);

            orderQueue.getQueue().add(order);

        }

    }


}
