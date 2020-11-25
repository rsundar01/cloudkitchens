package org.cloudkitchens.ordermgmt.data;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class OrderService {


    //@PersistenceContext
    //EntityManager entityManager;

    @Autowired
    private OrderRepository repository;

    public Order getOrder(String id) {

        //return entityManager.find(Order.class, id);

        Optional<Order> order = repository.findById(id);

        if(order.isPresent()) return order.get();
        else return null;

    }

    public Order insertOrder(Order order) {

        return repository.save(order);

        //return entityManager.merge(order);

    }

}
