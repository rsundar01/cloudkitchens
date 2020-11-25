package org.cloudkitchens.ordermgmt.engine;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import org.cloudkitchens.ordermgmt.data.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderQueue implements QueueWrapper {

    private Deque<Order> messageQueue = new LinkedBlockingDeque<>();


    public Queue<Order> getQueue() {

        return messageQueue;

    }
}
