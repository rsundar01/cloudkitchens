package org.cloudkitchens.ordermgmt.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    //@Autowired
    //private TestEntityManager testEntityManager;

    @Autowired
    OrderRepository orderRepository;

    @Before
    public void setUp() {

        Order order = new Order("11111", "item1", "hot", 1, 0.2);

        orderRepository.saveAndFlush(order);

    }

    @Test
    public void testInsert() {

        Order order = orderRepository.getOne("11111");

        Assert.assertNotNull(order);

        Assert.assertEquals("item1", order.getName());
        Assert.assertEquals("hot", order.getTemp());
        Assert.assertEquals(1, order.getShelfLife());
        Assert.assertEquals(0.2, order.getDecayRate(), 0);
    }

    @Test
    public void testUpdate() {

        Order order = new Order("11111", "item1", "cold", 1, 0.3);

        orderRepository.saveAndFlush(order);

        Assert.assertEquals("cold", order.getTemp());
        Assert.assertEquals(0.2, order.getDecayRate(), 0.3);

    }

}

