package org.cloudkitchens.ordermgmt.engine;

import org.cloudkitchens.ordermgmt.data.Order;

public interface Processor {

    public void process(Order order);

}
