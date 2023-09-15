package com.orderservice.service;

import com.orderservice.model.ClientDto;
import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.orderservice.model.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private OrderService orderService;

    private final String clientPath = "http://localhost:8082/api/v1/clients/default";

    @Test
    void createOrderWithNoClient() {
        var clientDto = new ClientDto(4L);
        var orderWithoutClient = Order.builder()
                .status(OPENED)
                .description("second order")
                .build();
        var excpectedOrder = Order.builder()
                .id(5L)
                .clientId(clientDto.getId())
                .status(OPENED)
                .description("second order")
                .build();
        when(orderRepository.save(orderWithoutClient))
                .thenReturn(excpectedOrder);
        when(restTemplate.getForEntity(clientPath, ClientDto.class))
                .thenReturn(new ResponseEntity(clientDto, HttpStatus.OK));

        var actualOrder = orderService.save(orderWithoutClient);
        assertThat(actualOrder).isEqualTo(excpectedOrder);
    }
}
