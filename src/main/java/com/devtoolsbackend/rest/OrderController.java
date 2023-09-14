package com.devtoolsbackend.rest;

import com.devtoolsbackend.model.Order;
import com.devtoolsbackend.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Order save(@RequestBody @Valid Order order) {
        return service.save(order);
    }

    @PutMapping("/{id}")
    public Order updateStatus(@Positive(message = "Id should be more than 1")
                              @PathVariable Long id, @RequestBody @Valid Order order) {
        return service.updateStatus(id, order.getStatus());
//        return service.updateStatus(id, order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Positive(message = "Id should be more than 1")
                           @PathVariable Long id) {
        service.delete(id);
    }
}
