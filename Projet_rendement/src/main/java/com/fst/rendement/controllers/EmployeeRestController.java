package com.fst.rendement.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fst.rendement.entities.Employee;
import com.fst.rendement.repositories.EmployeeRepository;


@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeRestController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Integer id) {
        return employeeRepository.findById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee e) {
        return employeeRepository.save(e);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee updated) {
        return employeeRepository.findById(id).map(e -> {
            e.setUsername(updated.getUsername());
            e.setPoste(updated.getPoste());
            e.setRendement_moyen(updated.getRendement_moyen());
            return employeeRepository.save(e);
        }).orElseGet(() -> {
            updated.setId(id);
            return employeeRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeRepository.deleteById(id);
    }

    @GetMapping("/stats")
    public StatsResponse getStats() {
        List<Employee> employees = employeeRepository.findAll();
        int total = employees.size();
        double rendementMoyen = employees.stream()
                .mapToDouble(Employee::getRendement_moyen)
                .average()
                .orElse(0);

        int heuresTotales = employees.stream()
                .mapToInt(Employee::getHeures_travaillees)
                .sum();

        int productionTotale = employees.stream()
                .mapToInt(Employee::getProduction_totale)
                .sum();

        return new StatsResponse(total, rendementMoyen, heuresTotales, productionTotale);
    }

    static class StatsResponse {
        public int total;
        public double rendement_moyen;
        public int heures_travaillees;
        public int production_totale;

        public StatsResponse(int total, double rendement_moyen, int heures_travaillees, int production_totale) {
            this.total = total;
            this.rendement_moyen = rendement_moyen;
            this.heures_travaillees = heures_travaillees;
            this.production_totale = production_totale;
        }
    }


}






