package com.clientservice.rest;

import com.clientservice.model.Client;
import com.clientservice.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@Validated
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @GetMapping("/{id}")
    public Client getClientById(@Positive(message = "Id should be more than 1")
                                @PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client saveClient(@RequestBody @Valid Client client) {
        return service.saveClient(client);
    }
}
