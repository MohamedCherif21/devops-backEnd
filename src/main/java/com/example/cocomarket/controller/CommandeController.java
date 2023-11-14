package com.example.cocomarket.controller;

import com.example.cocomarket.entity.*;

import com.example.cocomarket.interfaces.ICommande;
import com.example.cocomarket.repository.CartRepository;
import com.example.cocomarket.repository.Commande_Repository;
import com.example.cocomarket.repository.CouponRepository;
import com.example.cocomarket.services.CommandeService;
import com.example.cocomarket.services.CouponService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@Slf4j
@RequestMapping("/Commande")
public class   CommandeController {

    @Autowired
    CommandeService commandeService;

    @Autowired
    CartRepository car;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository ;

    @Autowired
    Commande_Repository commandeRepository;
    @Autowired
    ICommande ic;

    @Scheduled(cron = "0 0 0 * * ?")
    @PostMapping("/envoyerCouponPanier3Commandes")
    public void envoyerCouponPanier3Commandes() {
        commandeService.envoyerCouponPanier3Commandes();
    }


    @PostMapping("/Confirm-Commande/{idcart}")
    public Commande confirmerCommande(@RequestBody Commande commande,
                                      @PathVariable("idcart") Integer idcart,
                                      @RequestParam(value = "coupon", required = false) String coupon) {
        return commandeService.confirmerCommande(commande,idcart,coupon);

    }



    @DeleteMapping("/remove-Commande/{Commande-id}")
    public void removeCommande(@PathVariable("Commande-id") Integer idCommande) {
        commandeService.annulerCommande(idCommande);
    }


    @GetMapping("/retrieve-Commande/{Cart-id}/{Commande-id}")
    public Commande retrieveCommande(@PathVariable("Cart-id") Integer idcart,
                                     @PathVariable("Commande-id") Integer idCommande) {
        return commandeService.afficherCommandes(idcart,idCommande);
    }


    @GetMapping("/retrieve-all-Commandes/{Cart-id}")
    public Set<Commande> getCommandes(@PathVariable("Cart-id") Integer idcart) {
        return commandeService.afficherAllCommandes(idcart);
    }

    @GetMapping("/admin_retrieve-all-Commandes")
    public List<Commande> getCommandes() {
        return commandeService.afficherAllCommandesadmin();
    }

    @PutMapping("/{id}/accepter")
    public ResponseEntity<String> accepterCommande(@PathVariable("id") Integer idCommande) {
        commandeService.accepterCommande(idCommande);
        return ResponseEntity.ok("La commande a été acceptée avec succès.");
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<String> refuserCommande(@PathVariable("id") Integer idCommande) {
        commandeService.refuserCommande(idCommande);
        return ResponseEntity.ok("La commande a été refusée avec succès.");
    }

    @GetMapping("/rechercher/{parametre}")
    public ResponseEntity<List<Commande>> rechercher(@PathVariable String parametre) {
        List<Commande> commandes = commandeService.rechercher(parametre);

        if (commandes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(commandes, HttpStatus.OK);
        }
    }

    @GetMapping("/facture/{commandId}")
    public void generateInvoice(@PathVariable Integer commandId, HttpServletResponse response) throws Exception {
        // récupérer les informations de la commande à partir de votre base de données
        Optional<Commande> optionalOrder = commandeRepository.findById(commandId);

        if (optionalOrder.isPresent()) {
            Commande order = optionalOrder.get();

        // création du document PDF
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        Image logo = Image.getInstance("src/main/java/com/example/cocomarket/Images/logo.jpg");
        logo.scaleToFit(90, 90);
        logo.setAbsolutePosition(document.getPageSize().getWidth() - 100, document.getPageSize().getHeight() - 80);
        logo.setIndentationLeft(-50f);
        document.add(logo);

        Image signature = Image.getInstance("src/main/java/com/example/cocomarket/Images/signature.jpg");
        signature.scaleToFit(100, 100);
        signature.setAbsolutePosition(document.getPageSize().getWidth() - signature.getScaledWidth() - 30, 30);
        document.add(signature);

        Paragraph title = new Paragraph("CoCo Market", new Font(Font.TIMES_ROMAN, 22, Font.BOLD));
        title.setAlignment(Element.ALIGN_LEFT);
        title.add(new Chunk(Chunk.NEWLINE));
        title.add(new Chunk(Chunk.NEWLINE));
        title.add(new Chunk("Invoice", new Font(Font.TIMES_ROMAN, 16)));
        title.setAlignment(Element.ALIGN_CENTER);
        title.add(new Chunk(Chunk.NEWLINE));
        title.add(new Chunk(Chunk.NEWLINE));
        document.add(title);

        title.add(new Chunk(Chunk.NEWLINE));
        title.add(new Chunk(Chunk.NEWLINE));
        title.add(new Chunk(Chunk.NEWLINE));
        title.add(new Chunk(Chunk.NEWLINE));
        // ajouter les informations de la facture
        document.add(new Paragraph("Invoice for the order N°:" + order.getId(),new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
        document.add(new Paragraph("Customer's email: " + order.getBuyeremail(),new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));
        document.add(new Paragraph("Customer's address: " + order.getBuyeraddress(),new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));

        // ajouter les articles commandés
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Item name");
        table.addCell("Quantity");
        table.addCell("Unit price");
        table.addCell("Tax");
        table.addCell("Total price");

        List<Integer> qte = new ArrayList<>();
        List<String> productNames = new ArrayList<>();
        for (ProduitCart item : order.getCommandeCart().getItems()) {
            Produit produit2 = item.getProduit();
            Integer quantite = item.getQuantity();
            String produitname = produit2.getNom();
            qte.add(quantite);
            productNames.add(produitname);

            table.addCell(produitname);
            table.addCell(String.valueOf(quantite));
            table.addCell(String.valueOf(item.getProduit().getPrix())+"DT");
            table.addCell(String.valueOf(order.getTax())+"DT");
            table.addCell(String.valueOf(item.getProduit().getPrix() * item.getQuantity())+"DT");

        }

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(table);

        // ajouter le total de la facture
        document.add(new Paragraph(" Price includes VAT: " + order.getTotalprice()+"DT",new Font(Font.TIMES_ROMAN, 14, Font.BOLD)));

        document.close();

        // envoi du document PDF en réponse
        response.setContentType("facture/pdf");
        response.setContentLength(out.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"facture.pdf\"");
        OutputStream outStream = response.getOutputStream();
        outStream.write(out.toByteArray());
        outStream.flush();
        outStream.close();
        } else {
            // Handle the case when the Optional is empty
            throw new NullPointerException("Order not found for ID: " + commandId);
        }
    }

    @GetMapping("/cart-with-three-orders")
    public String findCartIdWith3Orders() {
        return commandeService.findCartIdWith3Orders() ;
    }

    @PutMapping("/add-assign-liv/{region}")
    @ResponseBody
    public Livraison addLivwithcommand(@RequestBody Livraison l,
                                       @PathVariable("region") String region)
    {
        return ic.affectercamandtolivaison(region,l);
    }







}
