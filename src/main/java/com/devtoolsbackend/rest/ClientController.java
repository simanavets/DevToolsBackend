package com.devtoolsbackend.rest;

import com.devtoolsbackend.model.Client;
import com.devtoolsbackend.service.ClientService;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
@Validated
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Client getClientById(@Positive(message = "Id should be more than 1")
                                @PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        return service.saveClient(client);
    }
}
