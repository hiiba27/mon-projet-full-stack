package com.fst.rendement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.rendement.entities.Machine;

public interface MachineRepository extends JpaRepository<Machine, Integer> {
}

