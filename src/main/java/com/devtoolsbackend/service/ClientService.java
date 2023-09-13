package com.devtoolsbackend.service;

import com.devtoolsbackend.model.Client;
import com.devtoolsbackend.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Client findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No client with id = %s", id)));
    }

    public Client saveClient(Client client) {
        return repository.save(client);
    }
}
