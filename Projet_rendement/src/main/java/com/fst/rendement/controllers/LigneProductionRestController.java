package com.fst.rendement.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fst.rendement.entities.LigneProduction;
import com.fst.rendement.repositories.LigneProductionRepository;

@RestController
@RequestMapping("/api/lignes")
@CrossOrigin
public class LigneProductionRestController {

    @Autowired
    private LigneProductionRepository ligneRepository;

    @GetMapping
    public List<LigneProduction> getAllLignes() {
        return ligneRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<LigneProduction> getLigneById(@PathVariable Integer id) {
        return ligneRepository.findById(id);
    }

    @PostMapping
    public LigneProduction createLigne(@RequestBody LigneProduction l) {
        return ligneRepository.save(l);
    }

    @PutMapping("/{id}")
    public LigneProduction updateLigne(@PathVariable Integer id, @RequestBody LigneProduction updated) {
        return ligneRepository.findById(id).map(l -> {
            l.setNom(updated.getNom());
            l.setCapacite_max(updated.getCapacite_max());
            l.setChef(updated.getChef());
            return ligneRepository.save(l);
        }).orElseGet(() -> {
            updated.setId(id);
            return ligneRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteLigne(@PathVariable Integer id) {
        ligneRepository.deleteById(id);
    }
}

