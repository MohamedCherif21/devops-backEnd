package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Catalogue;
import com.example.cocomarket.Entity.Produit;
import com.example.cocomarket.Interfaces.ICatalogue;
import com.example.cocomarket.Repository.Catalogue_Repository;
import com.example.cocomarket.Repository.Produit__Repository;
import com.example.cocomarket.Repository.User_Repository;
import com.example.cocomarket.config.EmailSenderService;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


import org.apache.naming.factory.SendMailFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Catalogue_Service implements ICatalogue {

    @Autowired
    private Catalogue_Repository catalogueRepository;
    @Autowired
    private Produit__Repository produitRepository;

    @Autowired
    private EmailSenderService senderService;
    @Autowired
    private User_Repository user_Repository;


    public List<Catalogue> getAllCatalogues() {
        return catalogueRepository.findAll();
    }

    public Optional<Catalogue> getCatalogueById(Integer id) {
        return catalogueRepository.findById(id);
    }

    public Catalogue addCatalogue(Catalogue catalogue) throws IOException, WriterException, com.google.zxing.WriterException {
        catalogue = catalogueRepository.save(catalogue);
        byte[] qrCode = generateQRCode(catalogue);
        catalogue.setQrCode(qrCode);
        return catalogueRepository.save(catalogue);
    }

    public byte[] generateQRCode(Catalogue catalogue) throws WriterException, IOException, com.google.zxing.WriterException {
        int width = 256;
        int height = 256;
        String charset = "UTF-8";
        int margin = 1;

        String data = "catalogue:" + catalogue.getId();

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
        return baos.toByteArray();
    }

    public Catalogue updateCatalogue(Catalogue catalogue) {
        return catalogueRepository.save(catalogue);
    }



    public void deleteCatalogueById(Integer id) {
        catalogueRepository.deleteById(id);
    }

    public void deleteAllCatalogues() {
        catalogueRepository.deleteAll();
    }

    public void addProduitToCatalogue(Integer catalogueId, Integer produitId) {
        Optional<Catalogue> optionalCatalogue = catalogueRepository.findById(catalogueId);
        Optional<Produit> optionalProduit = produitRepository.findById(produitId);
        if (optionalCatalogue.isPresent() && optionalProduit.isPresent()) {
            Catalogue catalogue = optionalCatalogue.get();
            Produit produit = optionalProduit.get();
            catalogue.getProduits().add(produit);
            produit.getCatalogues().add(catalogue);
            catalogueRepository.save(catalogue);
            produitRepository.save(produit);
        }


    }

    public void removeProduitFromCatalogue(Integer catalogueId, Integer produitId) {
        Optional<Catalogue> optionalCatalogue = catalogueRepository.findById(catalogueId);
        Optional<Produit> optionalProduit = produitRepository.findById(produitId);
        if (optionalCatalogue.isPresent() && optionalProduit.isPresent()) {
            Catalogue catalogue = optionalCatalogue.get();
            Produit produit = optionalProduit.get();
            catalogue.getProduits().remove(produit);
            produit.getCatalogues().remove(catalogue);
            catalogueRepository.save(catalogue);
            produitRepository.save(produit);
        }
    }

    public Catalogue createTop50Catalogue() {
        List<Produit> top50Produits = produitRepository.findTop50ByOrderByQuantiteVendueDesc();
        Catalogue catalogue = new Catalogue();
        catalogue.setNom("Top 50 Produits");
        catalogue.setDescription("Les 50 produits les plus vendus");

        Set<Produit> produits = new HashSet<>(top50Produits);
        catalogue.setProduits(produits);

        senderService.sendSimpleEmail("hamza.amdouni@esprit.tn","kjnjkn","kjjkhjk");


        /*byte[] qrCode = generateQRCode(catalogue);
        catalogue.setQrCode(qrCode);*/
        return catalogueRepository.save(catalogue);
    }



    public void createTopRatedProductsCatalogue() {
        // Récupérer les 5 produits les mieux notés
        List<Produit> topRatedProducts = produitRepository.findAll()
                .stream()
                .filter(p -> p.getRaiting_products() != null && !p.getRaiting_products().isEmpty())
                .sorted(Comparator.comparing(p -> -getAverageScore(p)))
                .limit(2)
                .collect(Collectors.toList());

        // Créer le catalogue contenant les 5 produits les mieux notés
        Catalogue topRatedProductsCatalogue = new Catalogue();
        topRatedProductsCatalogue.setNom("Top Rated Products");
        topRatedProductsCatalogue.setDescription("Catalogue contenant les 5 produits les plus notés.");
        topRatedProductsCatalogue.setImg("https://example.com/top-rated-products.png");
        topRatedProductsCatalogue.setProduits(new HashSet<>(topRatedProducts));

       /* byte[] qrCode = generateQRCode(topRatedProductsCatalogue);
        topRatedProductsCatalogue.setQrCode(qrCode);
        // Enregistrer le catalogue dans la base de données*/
        catalogueRepository.save(topRatedProductsCatalogue);
    }

    private Double getAverageScore(Produit produit) {
        return produit.getRaiting_products().stream()
                .mapToInt(r -> r.getScore())
                .average()
                .orElse(0.0);
    }

    public Catalogue createLatestProductsCatalogue()  {
        int last = catalogueRepository.findLastCatalogueId();
        int first = catalogueRepository.findFirstCatalogueId();
        Catalogue catalogue = new Catalogue();
        catalogue.setNom("Dernières nouveautés");
        catalogue.setDescription("Les derniers produits ajoutés au site "+ last + " "+ first );
        catalogue.setImg("https://example.com/images/latest-products.jpg");

        List<Produit> latestProducts = produitRepository.findTop10ByOrderByDatePublicationDesc();
        catalogue.setProduits(new HashSet<>(latestProducts));


        /*byte[] qrCode = generateQRCode(catalogue);
        catalogue.setQrCode(qrCode);*/

        return catalogueRepository.save(catalogue);


    }

    public Catalogue createPromoCatalogue() {
        Catalogue catalogue = new Catalogue();
        catalogue.setNom("PromotionCatalogue");
        catalogue.setDescription("Les produits en promo");
        catalogue.setImg("https://example.com/images/latest-products.jpg");


        List<Produit> promoProducts = produitRepository.findByPourcentagePromotionGreaterThan(0);

        catalogue.setProduits(new HashSet<>(promoProducts));

        return catalogueRepository.save(catalogue);
    }

    public Catalogue createPromoCatalogue(String nom, String description, String img, Integer pourcentagePromotion) {

        Catalogue catalogue = new Catalogue();
        catalogue.setNom(nom);
        catalogue.setDescription(description);
        catalogue.setImg(img);

        //Récupérer tous les produits qui ont un pourcentage de promotion non nul
        List<Produit> produitsEnPromotion = produitRepository.findByPourcentagePromotionGreaterThan(0);

        //Associer les produits au catalogue
        for (Produit produit : produitsEnPromotion) {
            catalogue.getProduits().add(produit);
        }

        //Calculer le prix de promotion pour chaque produit
        for (Produit produit : catalogue.getProduits()) {
            Float nouveauPrix = produit.getPrix() - (produit.getPrix() * (pourcentagePromotion / 100f));
            produit.setPrixPromotion(nouveauPrix);
        }

        return catalogueRepository.save(catalogue);
    }














}


