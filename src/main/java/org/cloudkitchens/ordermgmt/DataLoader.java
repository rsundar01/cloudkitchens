package org.cloudkitchens.ordermgmt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.cloudkitchens.ordermgmt.configuration.CloudKitchensConfig;
import org.cloudkitchens.ordermgmt.data.OrderService;
import org.cloudkitchens.ordermgmt.injestion.WatcherCallback;
import org.cloudkitchens.ordermgmt.injestion.SourceWatcherAdapter;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner, SourceWatcherAdapter {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CloudKitchensConfig config;

    @Override
    public void run(String... strings) throws Exception {

        logger.info("Data injestion process started...");

        logger.info(config.getInputfolders().get(0));

        StringBuffer sb = new StringBuffer();

        BufferedReader br;

        String line;

        try (InputStream is =
                getClass().getClassLoader().getResourceAsStream("orders.json")) {

            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }


            JSONArray jsonArray = new JSONArray(sb.toString());





            System.out.println(applicationContext.toString());

            logger.info(orderService.toString());

        } catch (Exception e) {

            logger.error(e.getMessage());
        }

    }

    @Override
    public void registerCallback(WatcherCallback icb) {

    }
}
