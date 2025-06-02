package com.electroreps.ElectroRepsClientService.repositories;

import com.electroreps.ElectroRepsClientService.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientRepoTests {

    @Autowired
    private ClientRepository clientRepository;

    Client exampleClient;

    @BeforeEach
    public void setUp() {
        clientRepository.deleteAll();

        exampleClient = new Client();
        exampleClient.setName("John Doe");
        exampleClient.setEmail("example@example.com");
       exampleClient = clientRepository.save(exampleClient);


    }

    @Test
    public void test_find_all(){
        var clients = clientRepository.findAll();
        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com", clients.get(0).getEmail());

    }

    @Test
    public void test_find_by_email() {
        var clients = clientRepository.findByEmail("example@example.com");
        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com", clients.get(0).getEmail());

    }

    @Test
    public void test_find_by_name() {

        var clients = clientRepository.findByName("John Doe");
        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com", clients.get(0).getEmail());
    }

    @Test
    public void test_find_by_name_and_email() {
        var clients = clientRepository.findByNameAndEmail("John Doe", "example@example.com");
        assertNotNull(clients);
        assertFalse(clients.isEmpty());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com", clients.get(0).getEmail());

    }

    @Test
    public void test_save_client() {

        assertNotNull(exampleClient);
        assertNotNull(exampleClient.getId());
        assertEquals("John Doe", exampleClient.getName());
        assertEquals("example@example.com", exampleClient.getEmail());
    }

    @Test
    public void test_update_client() {
        exampleClient.setName("Jane Doe");
        exampleClient.setEmail("exampol@exampol.com");
        Client updatedClient = clientRepository.save(exampleClient);
        assertNotNull(updatedClient);
        assertEquals("Jane Doe", updatedClient.getName());
        assertEquals("exampol@exampol.com", updatedClient.getEmail());
    }

    @Test
    public void test_delete_client() {
        Long id = exampleClient.getId();
        assertNotNull(id);
        clientRepository.deleteById(id);
        var deletedClient = clientRepository.findById(id);
        assertTrue(deletedClient.isEmpty(), "Client should be deleted");
    }
}
