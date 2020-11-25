package org.cloudkitchens.ordermgmt.templates;

import java.util.ArrayList;
import java.util.List;

public class OrdersFormatDescriptor {

    List<String> keys = new ArrayList<>();

    public OrdersFormatDescriptor() {

        keys.add("id");
        keys.add("name");
        keys.add("temp");
        keys.add("shelfLife");
        keys.add("decayRate");
    }

    public List<String> getKeys() {

        return keys;
    }
}
