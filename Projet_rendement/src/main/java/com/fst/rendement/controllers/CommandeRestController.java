package com.fst.rendement.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fst.rendement.entities.Commande;
import com.fst.rendement.repositories.CommandeRepository;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin
public class CommandeRestController {

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Commande> getCommandeById(@PathVariable Integer id) {
        return commandeRepository.findById(id);
    }

    @PostMapping
    public Commande createCommande(@RequestBody Commande c) {
        return commandeRepository.save(c);
    }

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Integer id, @RequestBody Commande updated) {
        return commandeRepository.findById(id).map(c -> {
            c.setClient(updated.getClient());
            c.setDate_commande(updated.getDate_commande());
            c.setQuantite_commandee(updated.getQuantite_commandee());
            c.setProduit(updated.getProduit());
            return commandeRepository.save(c);
        }).orElseGet(() -> {
            updated.setId(id);
            return commandeRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Integer id) {
        commandeRepository.deleteById(id);
    }

    // 🔹 Statistiques utiles
    @GetMapping("/stats/client")
    public Map<String, Long> getStatsByClient() {
        List<Commande> commandes = commandeRepository.findAll();
        Map<String, Long> stats = new HashMap<>();
        for (Commande c : commandes) {
            stats.put(c.getClient(), stats.getOrDefault(c.getClient(), 0L) + c.getQuantite_commandee());
        }
        return stats;
    }

    @GetMapping("/stats/produit")
    public Map<String, Long> getStatsByProduit() {
        List<Commande> commandes = commandeRepository.findAll();
        Map<String, Long> stats = new HashMap<>();
        for (Commande c : commandes) {
            String produitNom = c.getProduit() != null ? c.getProduit().getNom() : "Inconnu";
            stats.put(produitNom, stats.getOrDefault(produitNom, 0L) + c.getQuantite_commandee());
        }
        return stats;
    }
}

