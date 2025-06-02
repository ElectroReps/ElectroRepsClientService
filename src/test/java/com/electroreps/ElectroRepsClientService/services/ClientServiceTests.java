package com.electroreps.ElectroRepsClientService.services;

import com.electroreps.ElectroRepsClientService.dtos.ClientInfoDto;
import com.electroreps.ElectroRepsClientService.models.Client;
import com.electroreps.ElectroRepsClientService.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
public class ClientServiceTests {

    @MockitoBean
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    public void test_get_client_by_id() {
        Client client = new Client();
        client.setName("John Doe");
        client.setEmail("example@example.com");
        Mockito.when( clientRepository.findById(Mockito.any(Long.class)))
                .thenReturn(java.util.Optional.of(client));
        var response = clientService.getClientById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", ((Client) response.getBody()).getName());
        assertEquals("example@example.com", ((Client) response.getBody()).getEmail());




    }

    @Test
    public void test_get_client_by_id_not_found() {
        Mockito.when(clientRepository.findById(Mockito.any
(Long.class)))
                .thenReturn(java.util.Optional.empty());
        var response = clientService.getClientById(1L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No client found with id 1", response.getBody());
    }

    @Test
    public void test_get_client_with_params_no_params(){
        Mockito.when(clientRepository.findAll()).thenReturn(
                java.util.List.of(new Client(1L, "John Doe", "example@example.com" ),
                        new Client(2L, "Jane Doe", "example@example.com" )));

        var response = clientService.getClientWithParams(null, null);
        assertEquals(200, response.getStatusCodeValue());
        var clients = (java.util.List<Client>) response.getBody();
        assertEquals(2, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com", clients.get(1).getEmail());

    }

    @Test
    public void test_get_client_with_params_name() {
        Mockito.when(clientRepository.findByName(Mockito.any(String.class)))
                .thenReturn(java.util.List.of(new Client(1L, "John Doe", "example@example.com"  )));
        var response = clientService.getClientWithParams("John Doe", null);
        assertEquals(200, response.getStatusCodeValue());
        var clients = (java.util.List<Client>) response.getBody();
        assertEquals(1, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com" , clients.get(0).getEmail());

    }

    @Test
    public void test_get_client_with_params_email() {

        Mockito.when(clientRepository.findByEmail(Mockito.any(String.class)))
                .thenReturn(java.util.List.of(new Client(1L, "John Doe", "example@example.com" )));
        var response = clientService.getClientWithParams(null, "example@example.com" );
        assertEquals(200, response.getStatusCodeValue());
        var clients = (java.util.List<Client>) response.getBody();
        assertEquals(1, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com" , clients.get(0).getEmail());
    }

    @Test
    public void test_get_client_with_params_name_and_email() {
        Mockito.when(clientRepository.findByNameAndEmail(Mockito.any(String.class), Mockito.any(String.class)))
                .thenReturn(java.util.List.of(new Client(1L, "John Doe", "example@example.com" )));
        var response = clientService.getClientWithParams("John Doe", "exampl@example.com" );
        assertEquals(200, response.getStatusCodeValue());
        var clients = (java.util.List<Client>) response.getBody();
        assertEquals(1, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
        assertEquals("example@example.com" , clients.get(0).getEmail());

    }

    @Test
    public void test_create_client() {
        Client client = new Client();
        client.setName("John Doe");
        client.setEmail("example@example.com");
        Mockito.when(clientRepository.save(Mockito.any(Client.class)))
                .thenReturn(client);
        var response = clientService.createClient(client);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("John Doe", ((Client) response.getBody()).getName());

    }

    @Test
    public void test_update_client() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setEmail("example@example.com" );
        Mockito.when(clientRepository.findById(Mockito.any(Long.class)))
                .thenReturn(java.util.Optional.of(client));
        Mockito.when(clientRepository.save(Mockito.any(Client.class)))
                .thenReturn(client);
        var response = clientService.updateClient(1L, new ClientInfoDto("Jane Doe", "example@example.com"));
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Jane Doe", ((Client) response.getBody()).getName());
        assertEquals("example@example.com" , ((Client) response.getBody()).getEmail());

    }

    @Test
    public void test_update_client_not_found() {
        Mockito.when(clientRepository.findById(Mockito.any(Long.class)))
                .thenReturn(java.util.Optional.empty());
        var response = clientService.updateClient(1L, new ClientInfoDto("Jane Doe", "example@example.com"));
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No client found with id 1", response.getBody());


    }

    @Test
    public void test_delete_client() {
        Client client = new Client();
        client.setId(1L);
        client.setName("John Doe");
        client.setEmail("example@example.com" );
        Mockito.when(clientRepository.findById(Mockito.any(Long.class)))
                .thenReturn(java.util.Optional.of(client));

        Mockito.doNothing().when(clientRepository).deleteById(Mockito.any(Long.class));
        var response = clientService.deleteClient(1L);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
    }

    @Test
    public void test_delete_client_not_found() {
        Mockito.when(clientRepository.findById(Mockito.any(Long.class)))
                .thenReturn(java.util.Optional.empty());
        var response = clientService.deleteClient(1L);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertEquals("No client found with id 1", response.getBody());



    }
}
