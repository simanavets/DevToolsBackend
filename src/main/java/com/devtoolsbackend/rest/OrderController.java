package com.devtoolsbackend.rest;

import com.devtoolsbackend.model.Order;
import com.devtoolsbackend.service.OrderService;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Order findById(@Positive(message = "Id should be more than 1")
                          @PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<Order> findAll() {
        return service.findAll();
    }

    @PostMapping
    public Order save(@RequestBody Order order) {
        return service.save(order);
    }
}
