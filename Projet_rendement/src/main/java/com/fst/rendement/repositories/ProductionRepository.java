package com.fst.rendement.repositories;


import com.fst.rendement.entities.Production;
import com.fst.rendement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Integer> {
    List<Production> findByEmploye(Employee employe);
}

