package com.electroreps.ElectroRepsClientService.services;

import com.electroreps.ElectroRepsClientService.dtos.ClientInfoDto;
import com.electroreps.ElectroRepsClientService.models.Client;
import com.electroreps.ElectroRepsClientService.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {


    private final ClientRepository clientRepository;

    public ResponseEntity<?> getClientById(Long clientId) {

        // Logic to retrieve the client by ID
        Optional<Client> client = clientRepository.findById(clientId);

        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.status(404).body("No client found with id " + clientId);
        }
    }

    public ResponseEntity<?> getClientWithParams(String name, String email) {

        if (name == null && email == null) {
            return ResponseEntity.ok(clientRepository.findAll());
        }else if (name != null && email == null) {
            return ResponseEntity.ok(clientRepository.findByName(name));
        } else if (name == null && email != null) {
            return ResponseEntity.ok(clientRepository.findByEmail(email));
        } else {
            return ResponseEntity.ok(clientRepository.findByNameAndEmail(name, email));
        }
    }

    public  ResponseEntity<?> createClient(Client client) {
        Client savedClient = clientRepository.save(client);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    public ResponseEntity<?> updateClient(Long id, ClientInfoDto client) {
        Optional<Client> existingClient = clientRepository.findById(id);

        if (existingClient.isEmpty()) {
            return ResponseEntity.status(404).body("No client found with id " + id);
        }
        if(client.getEmail() != null && !client.getEmail().isEmpty()) {
            existingClient.get().setEmail(client.getEmail());
        }
        if(client.getName() != null && !client.getName().isEmpty()) {
            existingClient.get().setName(client.getName());
        }

        Client savedClient = clientRepository.save(existingClient.get());
        // Logic to update the client
        return ResponseEntity.ok(savedClient);

    }

    public ResponseEntity<?> deleteClient(Long id) {
        // Logic to update the client's name
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isEmpty()) {
            return ResponseEntity.status(404).body("No client found with id " + id);
        }
        clientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
