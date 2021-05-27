package com.crmApp.API.controller;

import com.crmApp.API.model.Client;
import com.crmApp.API.repository.ClientRepository;
import com.crmApp.API.repository.ProductRepository;
import com.crmApp.API.model.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client-management", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ClientRESTController {
    @Autowired
    private ClientRepository repository;

    //Getters and Setters

    public ClientRepository getRepository() {
        return repository;
    }

    public void setRepository(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/clients")
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    @PostMapping("/clients")
    Client createOrSaveClient(@RequestBody Client newClient) {
        return repository.save(newClient);
    }

    @GetMapping("/clients/{id}")
    Client getClientById(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @PutMapping("/clients/{id}")
    Client updateClient(@RequestBody Client newClient, @PathVariable Long id) {

        return repository.findById(id).map(Client -> {
            Client.setFirstName(newClient.getFirstName());
            Client.setLastName(newClient.getLastName());
            Client.setAddress(newClient.getAddress());
            Client.setEmail(newClient.getEmail());
            return repository.save(Client);
        }).orElseGet(() -> {
            newClient.setClientId(id);
            return repository.save(newClient);
        });
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }

}

