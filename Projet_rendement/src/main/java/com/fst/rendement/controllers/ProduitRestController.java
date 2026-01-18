package com.fst.rendement.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fst.rendement.entities.Produit;
import com.fst.rendement.repositories.ProduitRepository;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin
public class ProduitRestController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Produit> getProduitById(@PathVariable Integer id) {
        return produitRepository.findById(id);
    }

    @PostMapping
    public Produit createProduit(@RequestBody Produit p) {
        return produitRepository.save(p);
    }

    @PutMapping("/{id}")
    public Produit updateProduit(@PathVariable Integer id, @RequestBody Produit updated) {
        return produitRepository.findById(id).map(p -> {
            p.setNom(updated.getNom());
            p.setType(updated.getType());
            p.setTemps_standard(updated.getTemps_standard());
            return produitRepository.save(p);
        }).orElseGet(() -> {
            updated.setId(id);
            return produitRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Integer id) {
        produitRepository.deleteById(id);
    }
}

