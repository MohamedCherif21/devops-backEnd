package com.example.cocomarket.controller;


import com.example.cocomarket.entity.*;
import com.example.cocomarket.interfaces.*;
import com.example.cocomarket.repository.ImageRepository;
import com.example.cocomarket.repository.ProduitRepository;
import com.example.cocomarket.repository.ShopRepository;
import com.example.cocomarket.services.ContratPDFExport;
import com.example.cocomarket.services.ContratService;
import com.example.cocomarket.services.StorageService;
import com.example.cocomarket.util.Imageutils;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
public class SnoussiController {
    @Autowired

    IShop shopinterface;

    @Autowired
    ICategorie categorieinterface;

    @Autowired
    IProduit produitinterface;

    @Autowired
    IRaintingProduct raitingprointerface;

    @Autowired
    IContrat contratinterface ;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ImageRepository imagerepo ;

    @Autowired
    ProduitRepository produitRepository ;

    @GetMapping("/shop")
    public List<Shop> afficherLesShop() {

        return shopinterface.afficherLesShop();
    }

    @GetMapping("/shop/{id}")
    public Shop getShopById(@PathVariable int id) {
        return shopRepository.findById(id).orElse(null);
    }

    @PostMapping("/add-shop")
    public Shop addNewshop(@RequestBody Shop shp) throws WriterException, IOException {
        String shopUrl = "https://votre-site-web.com/shop/" + shp.getId();
        // Générer le QR code correspondant à l'URL du shop
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(shopUrl, BarcodeFormat.QR_CODE, 250, 250);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        // Convertir le QR code en tableau de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byte[] qrCode = byteArrayOutputStream.toByteArray();
        // Ajouter le QR code au shop et enregistrer le shop dans la base de données
        shp.setQrCodeShop(qrCode);
        return shopinterface.addNewshop(shp);
    }



    @PostMapping("/add-Categorie")
    public Categorie addnewCategorie(@RequestBody Categorie cat) {
        categorieinterface.addSubnewCategorie(cat);
        return categorieinterface.addnewCategorie(cat);
    }

    @GetMapping("/allcategorie")
    public List<Categorie> getAllcategorie () {
        return categorieinterface.getAllcategories() ;
    }



    @DeleteMapping("/supprimershop/{idShop}")
    void supprimerShop(@PathVariable("idShop") int id) {
        shopinterface.supprimerShop(id);

    }


    @PostMapping("/ajouterProduit/{idShop}/{idCateg}")
    public void addProduitAffeASHopAndAffeAcategorie(@RequestBody Produit produit, @PathVariable Integer idShop, @PathVariable Integer idCateg) {
        produitinterface.addnewProduit(produit);
        produitinterface.addProduitAffeASHopAndAffeAcategorie(produit.getId(), idShop, idCateg);
    }

    @Autowired
    private ProduitRepository produitrepo;
    @GetMapping(path = "/generateQrCode/{shopId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(@PathVariable Integer shopId) throws WriterException, IOException {
        String shopUrl = "https://votre-site-web.com/shop/" + shopId;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(shopUrl, BarcodeFormat.QR_CODE, 250, 250);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return ResponseEntity.ok(byteArrayOutputStream.toByteArray());
    }

    @GetMapping("/SumRaint")
    public String sumProduit (Integer idproduit,  Integer idporduit2 )
    {
        return produitinterface.sumRatting(idproduit,idporduit2) ;
    }

    @PostMapping("/Ajoutercontrat/{idshop}")
    public void addContratToShop(@RequestBody Contrat contrat, @PathVariable Integer idshop) {
        contratinterface.addContratEtAffecter(contrat);
        shopinterface.addContratToShop(contrat.getId(),idshop);

    }
    @GetMapping("produits/cherif")
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @GetMapping("/AffichageListeProduit")
    public List<Produit> recomendation(Integer idproduit , Integer idCateg){
        return produitinterface.recomendation(idproduit , idCateg);

    }
    @Autowired
    private ContratService service;

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Contrat> listUsers = service.listAll();

        ContratPDFExport exporter = new ContratPDFExport(listUsers);
        exporter.export(response);
    }
    @Autowired
    StorageService servicee ;
    @PutMapping(value = "/UploadeImage/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file , @PathVariable Integer id ) throws IOException {
        String uploadImage = servicee.uploadImage(file,id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping(value = "/produits/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImageForProduit(@PathVariable Integer id) {
        ImageData imageData = imagerepo.findByProduitId(id);
        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] imageBytes = Imageutils.decompressImage(imageData.getImagdata());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}








