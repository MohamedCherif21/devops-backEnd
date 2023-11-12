package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.Commande;
import com.example.cocomarket.Entity.Produit_Cart;
import com.example.cocomarket.Interfaces.StripeService;
import com.example.cocomarket.Repository.Commande_Repository;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/stripe)")
@Slf4j
public class StripeController {


    @Autowired
    private StripeService stripeService;

    @Autowired
    private Commande_Repository cr ;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/paiement")
    public ResponseEntity<String> effectuerPaiement(@RequestParam("commandeId") Integer commandeId,
                                                    @RequestParam("devise") String devise,
                                                    @RequestParam("token") String token ,HttpServletResponse response) throws StripeException, IOException {
        Commande order = cr.findById(commandeId).get();
        stripeService.effectuerPaiement(order.getTotal_price(), devise, token);
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Facture pour la commande #" + order.getId()));
        document.add(new Paragraph("Email client: " + order.getBuyer_email()));
        document.add(new Paragraph("Adresse client: " + order.getBuyer_address()));

        // ajouter les articles commandés
        PdfPTable table = new PdfPTable(3);
        table.addCell("Nom de l'article");
        table.addCell("Quantité");
        table.addCell("Prix");
        for (Produit_Cart item : order.getCommande_cart().getItems() ) {
            table.addCell(item.getProduit().getNom());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getProduit().getPrix()));
        }
        document.add(table);

        // ajouter le total de la facture
        document.add(new Paragraph("Total: " + order.getTotal_price()));
        document.add(new Paragraph("Tax: " + order.getTax()));
        document.close();

        // envoi du document PDF en réponse
        response.setContentType("facture/pdf");
        response.setContentLength(out.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"facture.pdf\"");
        OutputStream outStream = response.getOutputStream();
        outStream.write(out.toByteArray());
        outStream.flush();
        outStream.close();
        return new ResponseEntity<>("paiement effectué avec succés, vous pouvez télécharger votre facture", HttpStatus.OK);
    }
}

