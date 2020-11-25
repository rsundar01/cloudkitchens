package org.cloudkitchens.ordermgmt.engine;

import org.cloudkitchens.ordermgmt.data.Order;

public interface ShelfManager {

    public void addOrderToShelf(Order order);

    public void removeOrderFromShelf(Order order);
}
