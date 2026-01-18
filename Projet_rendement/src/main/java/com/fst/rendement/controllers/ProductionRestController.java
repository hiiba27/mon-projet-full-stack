package com.fst.rendement.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fst.rendement.entities.Production;
import com.fst.rendement.repositories.ProductionRepository;

@RestController
@RequestMapping("/api/productions")
@CrossOrigin
public class ProductionRestController {

    @Autowired
    private ProductionRepository productionRepository;

    @GetMapping
    public List<Production> getAllProductions() {
        return productionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Production> getProductionById(@PathVariable Integer id) {
        return productionRepository.findById(id);
    }

    @PostMapping
    public Production createProduction(@RequestBody Production p) {
        return productionRepository.save(p);
    }

    @PutMapping("/{id}")
    public Production updateProduction(@PathVariable Integer id, @RequestBody Production updated) {
        return productionRepository.findById(id).map(p -> {
            p.setDate(updated.getDate());
            p.setQuantite_produite(updated.getQuantite_produite());
            p.setTemps_travail(updated.getTemps_travail());
            p.setDefaults(updated.getDefaults());
            p.setEmploye(updated.getEmploye());
            p.setMachine(updated.getMachine());
            p.setCommande(updated.getCommande());
            return productionRepository.save(p);
        }).orElseGet(() -> {
            updated.setId(id);
            return productionRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteProduction(@PathVariable Integer id) {
        productionRepository.deleteById(id);
    }
}

