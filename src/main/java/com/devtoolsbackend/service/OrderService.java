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
    private final ClientService clientService;

    public OrderService(OrderRepository repository, ClientService clientService) {
        this.repository = repository;
        this.clientService = clientService;
    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No order with id = %d".formatted(id)));
    }
    
    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order save(Order order) {
        if (order.getClient() == null) {
            Client defaultClient = clientService.findById(1L);
            order.setClient(defaultClient);
        }
        return repository.save(order);
    }
}
