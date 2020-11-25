package org.cloudkitchens.ordermgmt.engine;

import java.util.Queue;
import org.cloudkitchens.ordermgmt.data.Order;

public interface QueueWrapper {

    public Queue<Order> getQueue();
}
