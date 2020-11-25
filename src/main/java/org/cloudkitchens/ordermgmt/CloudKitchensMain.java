package org.cloudkitchens.ordermgmt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
        //(exclude = {DataSourceAutoConfiguration.class})
public class CloudKitchensMain {

    private final Logger logger = LoggerFactory.getLogger(CloudKitchensMain.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        applicationContext = SpringApplication.run(CloudKitchensMain.class, args);

        System.out.println(applicationContext.toString());

        displayAllBeans();

    }


    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }
}
