package com.crmApp.API.controller;

import com.crmApp.API.model.Order;
import com.crmApp.API.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order-management", produces = {MediaType.APPLICATION_JSON_VALUE})
public class OrderRESTController {
    @Autowired
    private OrderRepository repository;

    //Getters and Setters

    public OrderRepository getRepository() {
        return repository;
    }

    public void setRepository(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/orders")
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @PostMapping("/orders")
    Order createOrSaveOrder(@RequestBody Order newOrder) {
        return repository.save(newOrder);
    }

    @GetMapping("/orders/{id}")
    Order getOrderById(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @PutMapping("/orders/{id}")
    Order updateOrder(@RequestBody Order newOrder, @PathVariable Long id) {

        return repository.findById(id).map(Order -> {
            Order.setStatus(newOrder.getStatus());
            Order.setDatetime(newOrder.getDatetime());
            return repository.save(Order);
        }).orElseGet(() -> {
            newOrder.setOrderId(id);
            return repository.save(newOrder);
        });
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }

}

