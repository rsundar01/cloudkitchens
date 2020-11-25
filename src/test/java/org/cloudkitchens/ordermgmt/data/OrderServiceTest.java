package org.cloudkitchens.ordermgmt.data;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @TestConfiguration
    static class OrderServiceTestContextConfiguration {

        @Bean
        public OrderService orderService() {

            return new OrderService();
        }
    }

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    private Map<String, Optional<Order>> store = new HashMap<>();


    @Before
    public void setUp() {

        Order order = new Order("11111", "item1", "hot",
                2, 0.2);
        store.put(order.getId(), Optional.of(order));

        order = new Order("22222", "item2", "cold",
                1, 0.4);
        store.put(order.getId(), Optional.of(order));

        ArgumentCaptor<String> orderIDCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.when(orderRepository.findById(anyString())).thenAnswer(new Answer() {

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return store.get(invocationOnMock.getArgument(0));
            }
        });


        Mockito.when(orderRepository.save(any(Order.class))).thenAnswer(new Answer(){

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Order order = invocationOnMock.getArgument(0);
                store.put(order.getId(), Optional.of(order));
                return order;
            }
        });


    }


    @Test
    public void testOrderService() {

        Order order = orderService.getOrder("11111");

        Assert.assertEquals("11111", order.getId());
        Assert.assertEquals("item1", order.getName());
        Assert.assertEquals("hot", order.getTemp());
        Assert.assertEquals(2, order.getShelfLife());
        Assert.assertEquals(0.2, order.getDecayRate(), 0.0);
    }

    @Test
    public void testUpdateOrder() {

        Order order = new Order("11111", "item1", "cold",
                3, 0.4);
        orderService.insertOrder(order);

        order = store.get("11111").get();
        Assert.assertEquals(order.getTemp(), "cold");
        Assert.assertEquals(order.getShelfLife(), 3);
        Assert.assertEquals(order.getDecayRate(), 0.4, 0.0);


    }
}
