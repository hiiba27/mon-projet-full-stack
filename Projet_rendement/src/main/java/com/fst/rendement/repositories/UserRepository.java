package com.fst.rendement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.rendement.entities.User;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Permet de retrouver un utilisateur par son username
    Optional<User> findByUsername(String username);

    // Vérifie si un utilisateur existe déjà
    boolean existsByUsername(String username);
}

