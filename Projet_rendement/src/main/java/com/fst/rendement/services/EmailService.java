package com.fst.rendement.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envoie un rapport PDF en pièce jointe
     *
     * @param destinataire adresse email du destinataire
     * @param sujet sujet de l'email
     * @param contenu corps du message
     * @param pdfBytes contenu du PDF en bytes
     */
    public void envoyerRapportAvecPdf(String destinataire, String sujet, String contenu, byte[] pdfBytes) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(destinataire);
        helper.setSubject(sujet);
        helper.setText(contenu);

        // 🔹 Ajout du PDF en pièce jointe
        helper.addAttachment("rapport.pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }
}

