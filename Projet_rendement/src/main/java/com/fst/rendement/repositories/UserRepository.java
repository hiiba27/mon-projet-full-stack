package com.fst.rendement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.rendement.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}

