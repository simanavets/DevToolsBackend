package com.devtoolsbackend.repository;

import com.devtoolsbackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
