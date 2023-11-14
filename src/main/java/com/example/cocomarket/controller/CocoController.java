package com.example.cocomarket.controller;

import com.example.cocomarket.entity.Catalogue;
import com.example.cocomarket.repository.CatalogueRepository;
import com.example.cocomarket.services.*;
import com.example.cocomarket.config.EmailSenderService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api/catalogue")
public class CocoController {

    @Autowired
    private EmailSenderService senderService;
    @Autowired
    private CatalogueService catalogueService;


    @Autowired
    private CatalogueRepository catalogueRepository;



    @GetMapping("")
    public List<Catalogue> getAllCatalogues() {
        return catalogueService.getAllCatalogues();
    }

    @GetMapping("/{id}")
    public Optional<Catalogue> getCatalogueById(@PathVariable Integer id) {
        return catalogueService.getCatalogueById(id);
    }

    @PostMapping("")
    public Catalogue addCatalogue(@RequestBody Catalogue catalogue) throws IOException, com.google.zxing.WriterException {
        return catalogueService.addCatalogue(catalogue);
    }

    @PutMapping("")
    public Catalogue updateCatalogue(@RequestBody Catalogue catalogue) {
        return catalogueService.updateCatalogue(catalogue);
    }

    @DeleteMapping("/{id}")
    public void deleteCatalogueById(@PathVariable Integer id) {
        catalogueService.deleteCatalogueById(id);
    }

    @DeleteMapping("")
    public void deleteAllCatalogues() {
        catalogueService.deleteAllCatalogues();
    }



    @PostMapping("/{catalogueId}/produits/{produitId}")
    public void addProduitToCatalogue(@PathVariable Integer catalogueId, @PathVariable Integer produitId) {
        senderService.sendSimpleEmail("hamza.amdouni@esprit.tn", "top 50 created","hala");
        catalogueService.addProduitToCatalogue(catalogueId, produitId);
    }

    @DeleteMapping("/{catalogueId}/produits/{produitId}")
    public void removeProduitFromCatalogue(@PathVariable Integer catalogueId, @PathVariable Integer produitId) {
        catalogueService.removeProduitFromCatalogue(catalogueId, produitId);
    }


    @Scheduled(cron="*/1 * * * * *")
    @DeleteMapping("/latestCatalogue")
    public void deleteLatestCatalogues() {
        int lastCatalogue ;
        int firstCatalogue;
        firstCatalogue= catalogueRepository.findFirstCatalogueId();
        lastCatalogue= catalogueRepository.findLastCatalogueId();
        for (int i = firstCatalogue; i < lastCatalogue-2; i++) {
            catalogueService.deleteCatalogueById(i);
        }
    }

    @PostMapping("/promo")
    public ResponseEntity<?> createPromoCatalogue(@RequestParam String nom,
                                                  @RequestParam String description,
                                                  @RequestParam String img,
                                                  @RequestParam Integer pourcentagePromotion)  {
        Catalogue catalogue = catalogueService.createPromoCatalogue(nom, description, img, pourcentagePromotion);
        return ResponseEntity.ok(catalogue);
    }

    @GetMapping("/users/export/pdf")
    @PreAuthorize("hasAuthority('USER')")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Catalogue> listUsers = catalogueService.getAllCatalogues();

        CataloguePDFExport exporter = new CataloguePDFExport(listUsers);
        exporter.export(response);

    }

    @GetMapping(path = "/getqrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage generateQRCodeImage(@RequestParam String url) throws WriterException {
//QRcode generator logic
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> getCatalogueQRCode(@PathVariable Integer id) {
        Optional<Catalogue> optionalCatalogue = catalogueService.getCatalogueById(id);
        if (optionalCatalogue.isPresent()) {
            Catalogue catalogue = optionalCatalogue.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(catalogue.getQrCode().length);
            return new ResponseEntity<>(catalogue.getQrCode(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }









}
