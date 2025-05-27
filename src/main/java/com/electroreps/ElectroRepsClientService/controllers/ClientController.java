package com.electroreps.ElectroRepsClientService.controllers;

import com.electroreps.ElectroRepsClientService.dtos.ClientInfoDto;
import com.electroreps.ElectroRepsClientService.models.Client;
import com.electroreps.ElectroRepsClientService.repositories.ClientRepository;
import com.electroreps.ElectroRepsClientService.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @GetMapping
    public ResponseEntity<?> getClientWithParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        return clientService.getClientWithParams(name, email);
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody @Valid Client client) {
        return clientService.createClient(client);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @RequestBody ClientInfoDto client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }
}
