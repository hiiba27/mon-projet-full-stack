package com.fst.rendement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.rendement.entities.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Integer> {
}
