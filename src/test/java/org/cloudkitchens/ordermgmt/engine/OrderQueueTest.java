package org.cloudkitchens.ordermgmt.engine;

import java.util.Queue;
import org.cloudkitchens.ordermgmt.data.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=OrderQueue.class, loader=AnnotationConfigContextLoader.class)
public class OrderQueueTest {

    @Autowired
    OrderQueue orderQueue1;

    @Autowired
    OrderQueue orderQueue2;

    @Test
    public void testOrderQueueReferences() {

        Order order1 = new Order("11111", "item1", "hot", 1, 0.3);
        Order order2 = new Order("22222", "item2", "cold", 2, 0.2);

        Queue<Order> queue1 = orderQueue1.getQueue();
        queue1.add(order1);

        Queue<Order> queue2 = orderQueue2.getQueue();
        queue2.add(order2);

        Assert.assertEquals(order1.getId(), queue1.poll().getId());
        Assert.assertEquals(order2.getId(), queue1.peek().getId());
    }
}
