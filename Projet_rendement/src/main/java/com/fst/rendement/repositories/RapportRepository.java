package com.fst.rendement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.rendement.entities.Rapport;

public interface RapportRepository extends JpaRepository<Rapport, Integer> {
}

