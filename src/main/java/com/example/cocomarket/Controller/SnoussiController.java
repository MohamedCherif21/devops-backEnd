package com.example.cocomarket.Controller;


import com.example.cocomarket.Entity.*;
import com.example.cocomarket.Interfaces.*;
import com.example.cocomarket.Repository.ImageRepository;
import com.example.cocomarket.Repository.Produit__Repository;
import com.example.cocomarket.Repository.Shop_Repository;
import com.example.cocomarket.Services.ContratPDFExport;
import com.example.cocomarket.Services.Contrat_Service;
import com.example.cocomarket.Services.StorageService;
import com.google.zxing.BarcodeFormat;
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
    IRainting_Product raitingprointerface;

    @Autowired
    IContrat contratinterface ;

    @Autowired
    Shop_Repository shopRepository;

    @Autowired
    ImageRepository imagerepo ;

    @Autowired
    Produit__Repository produitRepository ;

    @GetMapping("/shop")
    public List<Shop> AfficherLesShop() {

        return shopinterface.AfficherLesShop();
    }

    @GetMapping("/shop/{id}")
    public Shop getShopById(@PathVariable int id) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop != null) {
            return shop;
        } else {
            return shop;
        }
    }

    @PostMapping("/add-shop")
    public Shop AddNewshop(@RequestBody Shop shp) throws Exception {
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
        Shop shop = shopinterface.AddNewshop(shp);
        return shop;
    }



    @PostMapping("/add-Categorie")
    public Categorie AddnewCategorie(@RequestBody Categorie cat) {
        categorieinterface.AddSubnewCategorie(cat);
        Categorie categorie = categorieinterface.AddnewCategorie(cat);
        return categorie;
    }
    public SnoussiController(Produit__Repository produit__repository) {
        this.produit__repository = produit__repository;
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
    public void AddProduitAffeASHopAndAffeAcategorie(@RequestBody Produit produit, @PathVariable Integer idShop, @PathVariable Integer idCateg) {
        produitinterface.AddnewProduit(produit);
        produitinterface.AddProduitAffeASHopAndAffeAcategorie(produit.getId(), idShop, idCateg);
    }


    @PostMapping("/ajouterRainting/{idProduit}")
    public void AddRaitingtoProduit(@RequestBody Raiting_Product raintingP, @PathVariable Integer idProduit) {
        System.out.println("--------I'm IN Function--------");
        raitingprointerface.AddNEwRaitingProduit(raintingP);
        produitinterface.AddRaitingtoProduit(raintingP.getId(),idProduit);


    }


    private final Produit__Repository produit__repository;



    /*   @GetMapping("/recherche")
       public ResponseEntity<List<Produit>> searchForProduit(@SearchSpec Specification<Produit> specs) {
           return new ResponseEntity<>(produit__repository.findAll(Specification.where(specs)), HttpStatus.OK);
       }
   */
   /* @GetMapping(path = "/getqrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage generateQRCodeImage(@RequestParam String url) throws Exception {
//QRcode generator logic
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    */
    @GetMapping(path = "/generateQrCode/{shopId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(@PathVariable Integer shopId) throws Exception {
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
        return produitinterface.SumRatting(idproduit,idporduit2) ;
    }

    @PostMapping("/Ajoutercontrat/{idshop}")
    public void AddContratToShop(@RequestBody Contrat contrat, @PathVariable Integer idshop) {
        System.out.println("--------I'm IN Function--------");
        contratinterface.AddContratEtAffecter(contrat);
        shopinterface.AddContratToShop(contrat.getId(),idshop);

    }
    @GetMapping("produits/cherif")
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @GetMapping("/AffichageListeProduit")
    public List<Produit> Recomendation(Integer idproduit , Integer idCateg){
        return produitinterface.Recomendation(idproduit , idCateg);

    }
    @Autowired
    private Contrat_Service service;

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
    /*
        @GetMapping(value = "/produits/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
        @Produces(MediaType.IMAGE_PNG_VALUE)

        public ResponseEntity<ByteArrayResource> getImageForProduit(@PathVariable Integer id) {
            ImageData imageData = imagerepo.findByProduitId(id);
            if (imageData == null) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayResource resource = new ByteArrayResource(imageData.getImageData());
            return ResponseEntity.ok()
                    .contentLength(imageData.getImageData().length)
                    .header("Content-type", imageData.getType())
                    .body(resource);
        }
    */
    @GetMapping(value = "/produits/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImageForProduit(@PathVariable Integer id) {
        ImageData imageData = imagerepo.findByProduitId(id);
        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] imageBytes = com.example.cocomarket.util.ImageUtils.decompressImage(imageData.getImageData());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}








