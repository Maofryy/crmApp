package com.crmApp.API.controller;

import com.crmApp.API.model.Client;
import com.crmApp.API.model.User;
import com.crmApp.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user-management", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserRESTController {
    @Autowired
    private UserRepository repository;

    public UserRepository getRepository() {
        return repository;
    }

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User createOrSaveUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id).map(User -> {
            User.setFirstName(newUser.getFirstName());
            User.setLastName(newUser.getLastName());
            User.setAddress(newUser.getAddress());
            User.setEmail(newUser.getEmail());
            User.setPassword(newUser.getPassword());
            return repository.save(User);
        }).orElseGet(() -> {
            newUser.setUserId(id);
            return repository.save(newUser);
        });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }

}