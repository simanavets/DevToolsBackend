package com.orderservice.service;

import com.orderservice.model.Order;
import com.orderservice.model.OrderStatus;
import com.orderservice.repository.OrderRepository;
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

    public Order updateStatus(Long id, OrderStatus newStatus) {
        var order = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No order with id = %d".formatted(id)));

        order.setStatus(newStatus);
        return repository.save(order);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
