package com.electroreps.ElectroRepsClientService.controllers;

import com.electroreps.ElectroRepsClientService.dtos.ClientInfoDto;
import com.electroreps.ElectroRepsClientService.models.Client;
import com.electroreps.ElectroRepsClientService.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ClientControllerTests {

    @MockitoBean
    private ClientService clientService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGET_Id() throws Exception {

        Mockito.when(clientService.getClientById(Mockito.any(Long.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(get("/clients/9000"))
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testGET_Id_NotFound() throws Exception {
        Mockito.when(clientService.getClientById(Mockito.any(Long.class)))
                .thenReturn(new ResponseEntity<>( HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/clients/9000"))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void testPOST_Client() throws Exception {

        Client client = new Client();
        client.setName("Test Client");
        client.setEmail("example@example.com");

        Mockito.when(clientService.createClient(Mockito.any(Client.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/clients")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testPOST_Client_Invalid() throws Exception {

        Client client = new Client();// Invalid name

        Mockito.when(clientService.createClient(Mockito.any(Client.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        MvcResult result = mockMvc.perform(post("/clients")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("Validation failed, please send a valid Client entity as request body ", responseBody);
    }

    @Test
    public void testGET_ClientWithParams() throws Exception {
        Mockito.when(clientService.getClientWithParams(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(get("/clients")
                .param("name", "Test Client")
                .param("email", "example@example.com")).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testPATCH_Client() throws Exception {
        Client client = new Client();
        client.setId(1L);
        client.setName("Updated Client");
        client.setEmail("example@example.com");
        Mockito.when(clientService.updateClient(Mockito.any(Long.class), Mockito.any(ClientInfoDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(patch("/clients/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new ClientInfoDto("Updated Client", "example@example.com"))))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void testPATCH_Client_NotFound() throws Exception {
        Mockito.when(clientService.updateClient(Mockito.any(Long.class), Mockito.any(ClientInfoDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(patch("/clients/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new ClientInfoDto("Updated Client", "example@example.com"))))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testDELETE_Client() throws Exception {
        Mockito.when(clientService.deleteClient(Mockito.any(Long.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void testDELETE_Client_NotFound() throws Exception {
        Mockito.when(clientService.deleteClient(Mockito.any(Long.class)))
                .thenReturn(new ResponseEntity<>( HttpStatus.NOT_FOUND));

         mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNotFound())
                .andReturn();

    }




}
