package org.cloudkitchens.ordermgmt.simulators;

import java.util.Random;
import org.cloudkitchens.ordermgmt.data.Order;
import org.cloudkitchens.ordermgmt.engine.KitchenShelfManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class KitchenProcessSimulator implements Runnable {

    private static Logger LOGGER =
            LoggerFactory.getLogger(KitchenProcessSimulator.class);

    @Autowired
    private KitchenShelfManager kitchenShelfManager;

    private int minDelay;
    private int maxDelay;
    private Order order;
    private Random random = new Random();


    public KitchenProcessSimulator(Order order, int minDelay, int maxDelay) {

        this.order = order;

        this.minDelay = minDelay;

        this.maxDelay = maxDelay;
    }

    @Override
    public void run() {

        try {

            int delay = (maxDelay - minDelay) + 1;

            Thread.sleep(minDelay + random.nextInt(delay));

            kitchenShelfManager.addOrderToShelf(order);

        } catch (InterruptedException ie) {

            LOGGER.info("Kitchen processor interrupted. Exiting kitchen processor");
        }

    }
}
