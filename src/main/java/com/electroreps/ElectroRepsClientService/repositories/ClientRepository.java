package com.electroreps.ElectroRepsClientService.repositories;

import com.electroreps.ElectroRepsClientService.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByName(String name);
    List<Client> findByEmail(String name);
    List<Client> findByNameAndEmail(String name, String email);
}
