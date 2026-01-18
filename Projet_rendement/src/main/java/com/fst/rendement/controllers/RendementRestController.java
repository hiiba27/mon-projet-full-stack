package com.fst.rendement.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fst.rendement.dto.RendementDTO;
import com.fst.rendement.dto.RendementSyntheseDTO;
import com.fst.rendement.entities.Production;
import com.fst.rendement.repositories.ProductionRepository;
import com.fst.rendement.services.RendementService;

@RestController
@RequestMapping("/api/rendements")
@CrossOrigin
public class RendementRestController {

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private RendementService rendementService;

    // 🔹 Rendement par production
    @GetMapping
    public List<RendementDTO> getAllRendements() {
        List<Production> productions = productionRepository.findAll();
        return productions.stream()
                .map(rendementService::calculerRendement)
                .collect(Collectors.toList());
    }

    // 🔹 Synthèse par employé
    @GetMapping("/synthese/employes")
    public List<RendementSyntheseDTO> getSyntheseParEmploye() {
        List<Production> productions = productionRepository.findAll();
        return rendementService.calculerSyntheseParEmploye(productions);
    }

    // 🔹 Synthèse par machine
    @GetMapping("/synthese/machines")
    public List<RendementSyntheseDTO> getSyntheseParMachine() {
        List<Production> productions = productionRepository.findAll();
        return rendementService.calculerSyntheseParMachine(productions);
    }
}



