package com.devtoolsbackend.service;

import com.devtoolsbackend.model.Order;
import com.devtoolsbackend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final ClientService clientService;

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No order with id = %d".formatted(id)));
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order save(Order order) {
        if (order.getClient() == null) order.setClient(clientService.createDefaultClient());

        return repository.save(order);
    }

    public Order updateStatus(Order orderWithNewStatus) {
        var orderId = orderWithNewStatus.getId();
        var order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect order with id = %d".formatted(orderId)));

        order.setStatus(orderWithNewStatus.getStatus());
        return repository.save(order);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
