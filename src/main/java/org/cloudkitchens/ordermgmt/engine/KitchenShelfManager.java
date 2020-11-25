package org.cloudkitchens.ordermgmt.engine;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.cloudkitchens.ordermgmt.configuration.CloudKitchensConfig;
import org.cloudkitchens.ordermgmt.data.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenShelfManager implements ShelfManager {

    @Autowired
    private CloudKitchensConfig config;

    @Autowired
    private KitchenQueue kitchenQueue;

    private Queue<Order> hotShelf = new LinkedBlockingQueue<>(
            config.getShelf().getHotShelfSize());

    private Queue<Order> coldShelf = new LinkedBlockingQueue<>(
            config.getShelf().getColdShelfSize());

    private Queue<Order> frozenShelf = new LinkedBlockingQueue<>(
            config.getShelf().getFrozenShelfSize());

    private Queue<Order> overflowShelf = new LinkedBlockingQueue<>(
            config.getShelf().getOverflowShelfSize());


    @Override
    public void addOrderToShelf(Order order) {

        order.setStatus(2);

        switch(order.getTemp()) {

            case "hot":
                addOrder(order, hotShelf);
                break;
            case "cold":
                addOrder(order, coldShelf);
                break;
            case "frozen":
                addOrder(order, frozenShelf);
                break;

        }
    }

    @Override
    public void removeOrderFromShelf(Order order) {

    }

    private void addOrder(Order order, Queue<Order> shelf) {

        if(!shelf.add(order)) {

            if(!overflowShelf.add(order)) {

                synchronized (this) {

                    Order eorder = overflowShelf.poll();

                    overflowShelf.add(order);

                    eorder.setStatus(3);

                }
            }

        }
    }
}
