package com.devtoolsbackend.service;

import com.devtoolsbackend.model.Client;
import com.devtoolsbackend.model.Order;
import com.devtoolsbackend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No order with id = %s", id)));
    }
    
    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order save(Order order) {
        if (order.getClient() == null) {
            Client defaultClient = Client.builder()
                    .email("%d.no.client@default.com".formatted(order.getId()))
                    .build();
            order.setClient(defaultClient);
        }
        return repository.save(order);
    }
}
