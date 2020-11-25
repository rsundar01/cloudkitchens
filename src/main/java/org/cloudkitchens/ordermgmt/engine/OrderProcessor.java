package org.cloudkitchens.ordermgmt.engine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.cloudkitchens.ordermgmt.configuration.CloudKitchensConfig;
import org.cloudkitchens.ordermgmt.data.Order;
import org.cloudkitchens.ordermgmt.simulators.KitchenProcessSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor implements Runnable, Processor {

    private static Logger LOGGER = LoggerFactory.getLogger(OrderProcessor.class);

    @Autowired
    private OrderQueue orderQueue;

    @Autowired
    private CloudKitchensConfig config;

    private ExecutorService executorService;

    @Override
    public void run() {

        executorService = Executors.newFixedThreadPool(config.getNthreads());

        BlockingQueue<Order> orders = (BlockingQueue<Order>) orderQueue.getQueue();

        while(true) {

            try {

                process(orders.take()); // Blocking call

                Thread.sleep(config.getProcessdelay() * 1000);

            } catch (InterruptedException ie) {

                LOGGER.info("Order_processor thread interrupted. Exiting thread.");

                return;

            }
        }
    }

    @Override
    public void process(Order order) {

        order.setStatus(1);

        executorService.submit(new KitchenProcessSimulator(order, config.getMin() * 1000
                                    , config.getMax() * 1000));

    }
}
