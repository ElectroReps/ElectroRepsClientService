package com.electroreps.ElectroRepsClientService.services;

import com.electroreps.ElectroRepsClientService.dtos.ClientInfoDto;
import com.electroreps.ElectroRepsClientService.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    public ResponseEntity<?> getClientById(String clientId) {
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getClientWithParams(String name, String email) {
        return ResponseEntity.noContent().build();
    }

    public  ResponseEntity<?> createClient(Client client) {
        return  ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> updateClient(Long id, ClientInfoDto client) {
        // Logic to update the client
        return ResponseEntity.noContent().build();

    }

    public ResponseEntity<?> deleteClient(Long id) {
        // Logic to update the client's name
        return ResponseEntity.noContent().build();
    }
}
