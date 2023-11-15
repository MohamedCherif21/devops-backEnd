    package com.example.cocomarket.Services;

    import com.example.cocomarket.Entity.Contrat;
    import com.example.cocomarket.Entity.Shop;
    import com.example.cocomarket.Interfaces.IShop;
    import com.example.cocomarket.Repository.Contrat_Repository;
    import com.example.cocomarket.Repository.Shop_Repository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class Shop_Service implements IShop {
        @Autowired
        Shop_Repository Shoprepo ;

        @Autowired
        Contrat_Repository contratrepo ;


        @Override
        public List<Shop> AfficherLesShop() {
            return
                    Shoprepo.findAll();
        }

        @Override
        public void supprimerShop(int id) {
            Shoprepo.deleteById(id);
        }

        @Override
        public Shop RetriveByid(int id) {
            return Shoprepo.findById(id).orElse(null);
        }

        @Override
        public Shop AddNewshop(Shop shp ) {
    /*
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250);
            MatrixToImageWriter.toBufferedImage(bitMatrix);
            shp.setUrl(url);

    int width ;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter(url, BarcodeFormat.QR_CODE, 250, 250);
            ByteArrayOutputStream pngOutputStream =
                    new ByteArrayOutputStream();
            MatrixToImageConfig con =
                    new MatrixToImageConfig(0xFF000002, 0xFF04B4AE);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
            byte[] pngData = pngOutputStream.toByteArray();

            return pngData;
            */
            return Shoprepo.save(shp);
        }

        @Override
        public void AddContratToShop(Integer idcontrat ,Integer idshop) {


            Shop shop = Shoprepo.findById(idshop).orElse(null) ;
            Contrat c = contratrepo.findById(idcontrat).orElse(null) ;
            //produit.getRaiting_products().add(R) ;
            System.out.println("♦♦♦♦♦♦♦♦♦♦"+c);
            System.out.println("♦♦♦♦♦♦♦♦♦♦"+shop);
            shop.setContrat_shop(c);  ;
            Shoprepo.save(shop) ;

        }

    }
