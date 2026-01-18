package com.fst.rendement.services;

import com.fst.rendement.dto.RapportRequestDTO;
import com.fst.rendement.dto.RendementDTO;
import com.fst.rendement.entities.Production;
import com.fst.rendement.repositories.ProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RapportService {

    @Autowired
    private ProductionRepository productionRepository;

    public List<RendementDTO> genererRapport(RapportRequestDTO request) {
        List<Production> productions = filtrerProductions(request);

        return productions.stream()
                .map(p -> {
                    float rendement = p.getTemps_travail() > 0 ? (float)p.getQuantite_produite() / p.getTemps_travail() : 0;
                    float qualite = p.getQuantite_produite() > 0 ? ((float)(p.getQuantite_produite() - p.getDefaults()) / p.getQuantite_produite()) * 100 : 0;
                    float global = rendement * (qualite / 100);

                    return new RendementDTO(
                            p.getId(),
                            p.getDate() != null ? p.getDate().toString() : "",
                            rendement,
                            qualite,
                            global,
                            p.getEmploye() != null ? p.getEmploye().getUsername() : null,
                            p.getMachine() != null ? p.getMachine().getNom() : null
                    );
                }).collect(Collectors.toList());
    }

    private List<Production> filtrerProductions(RapportRequestDTO request) {
        List<Production> all = productionRepository.findAll();

        return all.stream().filter(p -> {
            boolean match = true;

            if (request.getDateDebut() != null && request.getDateFin() != null) {
                try {
                    Date debut = new SimpleDateFormat("yyyy-MM-dd").parse(request.getDateDebut());
                    Date fin = new SimpleDateFormat("yyyy-MM-dd").parse(request.getDateFin());
                    match &= p.getDate() != null && !p.getDate().before(debut) && !p.getDate().after(fin);
                } catch (Exception e) {
                    // ignore parse errors
                }
            }

            if ("employe".equalsIgnoreCase(request.getTypeRapport()) && request.getEmployeId() != null) {
                match &= p.getEmploye() != null && p.getEmploye().getId().equals(request.getEmployeId());
            }

            if ("machine".equalsIgnoreCase(request.getTypeRapport()) && request.getMachineId() != null) {
                match &= p.getMachine() != null && p.getMachine().getId().equals(request.getMachineId());
            }

            return match;
        }).collect(Collectors.toList());
    }
}
