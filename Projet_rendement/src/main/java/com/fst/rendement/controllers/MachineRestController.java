package com.fst.rendement.controllers;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fst.rendement.entities.Machine;
import com.fst.rendement.repositories.MachineRepository;

@RestController
@RequestMapping("/api/machines")
@CrossOrigin
public class MachineRestController {

    @Autowired
    private MachineRepository machineRepository;

    @GetMapping
    public List<Machine> getAllMachines() {
        return machineRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Machine> getMachineById(@PathVariable Integer id) {
        return machineRepository.findById(id);
    }

    @PostMapping
    public Machine createMachine(@RequestBody Machine m) {
        return machineRepository.save(m);
    }

    @PutMapping("/{id}")
    public Machine updateMachine(@PathVariable Integer id, @RequestBody Machine updated) {
        return machineRepository.findById(id).map(m -> {
            m.setNom(updated.getNom());
            m.setEtat(updated.getEtat());
            m.setTaux_utilisation(updated.getTaux_utilisation());
            m.setHeures_fonctionnement(updated.getHeures_fonctionnement());
            m.setProduction_totale(updated.getProduction_totale());
            m.setDerniere_maintenance(updated.getDerniere_maintenance());
            m.setLigne(updated.getLigne());
            return machineRepository.save(m);
        }).orElseGet(() -> {
            updated.setId(id);
            return machineRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteMachine(@PathVariable Integer id) {
        machineRepository.deleteById(id);
    }

    // 🔹 Meilleure machine
    @GetMapping("/best")
    public Machine getBestMachine() {
        return machineRepository.findAll().stream()
            .max((m1, m2) -> Integer.compare(m1.getProduction_totale(), m2.getProduction_totale()))
            .orElse(null);
    }

    // 🔹 Statistiques par état
    @GetMapping("/stats/etat")
    public Map<String, Long> getMachinesStatsByEtat() {
        List<Machine> machines = machineRepository.findAll();

        long actives = machines.stream()
            .filter(m -> m.getEtat() != null && m.getEtat().equalsIgnoreCase("En marche"))
            .count();

        long eteintes = machines.stream()
            .filter(m -> m.getEtat() != null &&
                (m.getEtat().equalsIgnoreCase("Arrêtée")
                || m.getEtat().equalsIgnoreCase("Éteinte")
                || m.getEtat().equalsIgnoreCase("Arreter")))
            .count();

        long panne = machines.stream()
            .filter(m -> m.getEtat() != null &&
                (m.getEtat().equalsIgnoreCase("Panne")
                || m.getEtat().equalsIgnoreCase("Maintenance")))
            .count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("Actives", actives);
        stats.put("Éteintes", eteintes);
        stats.put("En panne", panne);

        return stats;
    }
}
