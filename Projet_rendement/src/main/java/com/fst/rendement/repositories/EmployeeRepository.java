package com.fst.rendement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.rendement.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}

