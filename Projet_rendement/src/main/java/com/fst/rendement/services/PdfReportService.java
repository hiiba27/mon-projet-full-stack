package com.fst.rendement.services;

import com.fst.rendement.dto.RendementDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {

    public byte[] generateRapportPdf(List<RendementDTO> rendements, String titre) {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Titre
            Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Paragraph titreParagraphe = new Paragraph(titre, fontTitre);
            titreParagraphe.setAlignment(Element.ALIGN_CENTER);
            document.add(titreParagraphe);
            document.add(new Paragraph("\n"));

            // Tableau
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            addHeader(table, "Production ID");
            addHeader(table, "Date");
            addHeader(table, "Employé");
            addHeader(table, "Machine");
            addHeader(table, "Taux Rendement");
            addHeader(table, "Taux Qualité");
            addHeader(table, "Rendement Global");

            for (RendementDTO r : rendements) {
                table.addCell(String.valueOf(r.getProductionId()));
                table.addCell(r.getDate());
                table.addCell(r.getEmploye() != null ? r.getEmploye() : "—");
                table.addCell(r.getMachine() != null ? r.getMachine() : "—");
                table.addCell(String.format("%.2f", r.getTauxRendement()));
                table.addCell(String.format("%.2f %%", r.getTauxQualite()));
                table.addCell(String.format("%.2f", r.getRendementGlobal()));
            }

            document.add(table);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erreur génération PDF", e);
        }
    }

    private void addHeader(PdfPTable table, String titre) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setPhrase(new Phrase(titre));
        table.addCell(header);
    }
}
