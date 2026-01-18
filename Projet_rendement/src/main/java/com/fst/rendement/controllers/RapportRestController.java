package com.fst.rendement.controllers;

import com.fst.rendement.dto.RapportRequestDTO;
import com.fst.rendement.dto.RapportEmailDTO;
import com.fst.rendement.dto.RendementDTO;
import com.fst.rendement.services.RapportService;
import com.fst.rendement.services.PdfReportService;
import com.fst.rendement.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api/rapports")
@CrossOrigin
public class RapportRestController {

    @Autowired
    private RapportService rapportService;

    @Autowired
    private PdfReportService pdfReportService;

    @Autowired
    private EmailService emailService;

    /**
     * Générer un rapport filtré (global, par employé, par machine, par commande)
     */
    @PostMapping("/generer")
    public List<RendementDTO> genererRapport(@RequestBody RapportRequestDTO request) {
        return rapportService.genererRapport(request);
    }

    /**
     * Exporter le rapport en PDF
     */
    @PostMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestBody RapportRequestDTO request) {
        List<RendementDTO> rendements = rapportService.genererRapport(request);

        if (rendements.isEmpty()) {
            return ResponseEntity.badRequest()
                .body("Aucune donnée trouvée pour ce rapport".getBytes());
        }

        byte[] pdf = pdfReportService.generateRapportPdf(rendements, "Rapport de Performance");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    /**
     * Envoyer le rapport par email avec PDF en pièce jointe
     */
    @PostMapping("/envoyer")
    public ResponseEntity<String> envoyerRapport(@RequestBody RapportEmailDTO request) {
        try {
            if (request.getDestinataires() == null || request.getDestinataires().isEmpty()) {
                return ResponseEntity.badRequest().body("Aucun destinataire fourni");
            }

            // 🔹 Conversion RapportEmailDTO → RapportRequestDTO
            RapportRequestDTO req = new RapportRequestDTO();
            req.setTypeRapport(request.getTypeRapport());
            req.setDateDebut(request.getDateDebut());
            req.setDateFin(request.getDateFin());
            req.setEmployeId(request.getEmployeId());
            req.setMachineId(request.getMachineId());

            // Générer les données du rapport
            List<RendementDTO> rendements = rapportService.genererRapport(req);

            if (rendements.isEmpty()) {
                return ResponseEntity.badRequest().body("Aucune donnée trouvée pour ce rapport");
            }

            // Générer le PDF
            byte[] pdf = pdfReportService.generateRapportPdf(rendements, "Rapport de Performance");

            // Envoyer à chaque destinataire
            for (String dest : request.getDestinataires()) {
                emailService.envoyerRapportAvecPdf(dest,
                        "Rapport de Performance",
                        "Veuillez trouver ci-joint votre rapport.",
                        pdf);
            }

            return ResponseEntity.ok("Rapport envoyé avec succès à : " + String.join(", ", request.getDestinataires()));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi du rapport : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne : " + e.getMessage());
        }
    }
}


