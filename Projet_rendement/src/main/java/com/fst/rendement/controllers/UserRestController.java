package com.fst.rendement.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fst.rendement.entities.User;
import com.fst.rendement.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User u) {
        return userRepository.save(u);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User updated) {
        return userRepository.findById(id).map(u -> {
            u.setUsername(updated.getUsername());
            u.setPassword(updated.getPassword());
            u.setRole(updated.getRole());
            return userRepository.save(u);
        }).orElseGet(() -> {
            updated.setId(id);
            return userRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
}
