package com.fst.rendement.services;

import org.springframework.stereotype.Service;
import com.fst.rendement.dto.RendementDTO;
import com.fst.rendement.dto.RendementSyntheseDTO;
import com.fst.rendement.entities.Production;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendementService {

    public RendementDTO calculerRendement(Production p) {
        float tauxRendement = p.getTemps_travail() > 0 ? (float)p.getQuantite_produite() / p.getTemps_travail() : 0;
        float tauxQualite = p.getQuantite_produite() > 0 ? ((float)(p.getQuantite_produite() - p.getDefaults()) / p.getQuantite_produite()) * 100 : 0;
        float rendementGlobal = tauxRendement * (tauxQualite / 100);

        return new RendementDTO(
            p.getId(),
            p.getDate() != null ? p.getDate().toString() : "",
            tauxRendement,
            tauxQualite,
            rendementGlobal,
            p.getEmploye() != null ? p.getEmploye().getUsername() : null,
            p.getMachine() != null ? p.getMachine().getNom() : null
        );
    }

    // 🔹 Moyenne par employé
    public List<RendementSyntheseDTO> calculerSyntheseParEmploye(List<Production> productions) {
        return productions.stream()
            .filter(p -> p.getEmploye() != null && p.getEmploye().getUsername() != null)
            .collect(Collectors.groupingBy(p -> p.getEmploye().getUsername()))
            .entrySet().stream()
            .map(entry -> {
                String employe = entry.getKey();
                List<Production> prodList = entry.getValue();

                float totalRendement = 0, totalQualite = 0;
                for (Production p : prodList) {
                    float r = p.getTemps_travail() > 0 ? (float)p.getQuantite_produite() / p.getTemps_travail() : 0;
                    float q = p.getQuantite_produite() > 0 ? ((float)(p.getQuantite_produite() - p.getDefaults()) / p.getQuantite_produite()) * 100 : 0;
                    totalRendement += r;
                    totalQualite += q;
                }

                int n = prodList.size();
                float moyenneRendement = n > 0 ? totalRendement / n : 0;
                float moyenneQualite = n > 0 ? totalQualite / n : 0;
                float moyenneGlobale = moyenneRendement * (moyenneQualite / 100);

                return new RendementSyntheseDTO(employe, "employe", moyenneRendement, moyenneQualite, moyenneGlobale);
            }).collect(Collectors.toList());
    }

    // 🔹 Moyenne par machine
    public List<RendementSyntheseDTO> calculerSyntheseParMachine(List<Production> productions) {
        return productions.stream()
            .filter(p -> p.getMachine() != null && p.getMachine().getNom() != null)
            .collect(Collectors.groupingBy(p -> p.getMachine().getNom()))
            .entrySet().stream()
            .map(entry -> {
                String machine = entry.getKey();
                List<Production> prodList = entry.getValue();

                float totalRendement = 0, totalQualite = 0;
                for (Production p : prodList) {
                    float r = p.getTemps_travail() > 0 ? (float)p.getQuantite_produite() / p.getTemps_travail() : 0;
                    float q = p.getQuantite_produite() > 0 ? ((float)(p.getQuantite_produite() - p.getDefaults()) / p.getQuantite_produite()) * 100 : 0;
                    totalRendement += r;
                    totalQualite += q;
                }

                int n = prodList.size();
                float moyenneRendement = n > 0 ? totalRendement / n : 0;
                float moyenneQualite = n > 0 ? totalQualite / n : 0;
                float moyenneGlobale = moyenneRendement * (moyenneQualite / 100);

                return new RendementSyntheseDTO(machine, "machine", moyenneRendement, moyenneQualite, moyenneGlobale);
            }).collect(Collectors.toList());
    }
}
