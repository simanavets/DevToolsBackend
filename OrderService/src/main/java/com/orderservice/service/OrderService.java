package com.orderservice.service;

import com.orderservice.model.ClientDto;
import com.orderservice.model.Order;
import com.orderservice.model.OrderStatus;
import com.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;
    private final String clientPath = "http://localhost:8082/api/v1/clients/default";

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No order with id = %d".formatted(id)));
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order save(Order order) {
        if (order.getClientId() == null) {
            var response = restTemplate.getForEntity(clientPath, ClientDto.class);
            var client = response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
            if (client != null) {
                order.setClientId(client.getId());
            } else {
                log.error("No client from Client Service");
            }
        }

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
