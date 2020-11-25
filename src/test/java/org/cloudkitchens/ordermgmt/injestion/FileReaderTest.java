package org.cloudkitchens.ordermgmt.injestion;

//
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.cloudkitchens.ordermgmt.CloudKitchensMain;
import org.cloudkitchens.ordermgmt.data.InputFile;
import org.cloudkitchens.ordermgmt.data.InputFileRepository;
import org.cloudkitchens.ordermgmt.data.InputFileService;
import org.cloudkitchens.ordermgmt.data.OrderRepository;
import org.cloudkitchens.ordermgmt.data.OrderService;
import org.cloudkitchens.ordermgmt.engine.OrderQueue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes=CloudKitchensMain.class)
//@DataJpaTest
//@ContextConfiguration(classes={Order.class, InputFile.class, OrderRepository.class, InputFileRepository.class,FileReader.class, InputFileService.class, OrderService.class}, loader=AnnotationConfigContextLoader.class)
@TestMethodOrder(OrderAnnotation.class)
public class FileReaderTest {

    private static Logger LOGGER = LoggerFactory.getLogger(FileReaderTest.class);

    private String path = "src/test/resources/input-files-1";

    private String file1 = "orders-test1.json";

    private boolean isInitialized = false;

    private static ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    FileReader fileReader;

    @Autowired
    InputFileService inputFileService;

    @Autowired
    OrderService orderService;

    @Before
    public void init() {

        try {

            threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

            threadPoolExecutor.submit(fileReader);

            Files.list(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .forEach((x) -> fileReader.process(x));

            isInitialized = true;

        } catch (Exception e) {

            LOGGER.error("initialization failed");
        }
    }

    @Test
    @Order(1)
    public void testAFileReader() {

        Assert.assertEquals(true, isInitialized);

        try {

            while (!inputFileService.containsFile(path + "/" + file1)) {

                Thread.sleep(500);
            }

            while(inputFileService.getInputFile(path + "/" + file1).getStatus() != 2) {

                Thread.sleep(250);
            }


            Assert.assertEquals("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd", orderService.getOrder("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd").getId());


        } catch (InterruptedException ie) {

            Assert.fail();
        }
    }

    @Test()
    @Order(2)
    public void testDuplicateInputsHandling() {

        fileReader.process(Paths.get(path + "/" + file1));
    }
}
