package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.*;
import com.example.cocomarket.Interfaces.ICommande;
import com.example.cocomarket.Repository.*;
import com.example.cocomarket.config.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Commande_Service implements ICommande {

    @Autowired
    Commande_Repository cr;
    @Autowired
    Livraison_Repository lr;
    @Autowired
    User_Repository ur;
    @Autowired
    Cart_Repository car;

    @Autowired
    Coupon_Repository couponRepository ;

    @Autowired
    Coupon_Service coupon_service ;

    @Autowired
    EmailSenderService mailserv ;



    @Override
    public Livraison affectercamandtolivaison(String Region,Livraison l) {
        List<Commande> c=cr.getnotaffectedCommand(Region);
        LocalDate d;
        float x=0,y=0;
        for (Commande i:c)
        {
            x+=i.getSomme_volume();
            y+=i.getTotal_weight();
            i.setEtat(Etat.VALIDATED);
          i.setLivraison_commande(l);
        }
        l.setWeightL(y);
       l.setVolumeL(x);
       l.setRegion(Region);
        d=LocalDate.now();
        l.setDate_Sortie(d);
        lr.save(l);
        return l;

        }

    @Override
    public Commande Confirmer_Commande(Commande commande, Integer idCart,String couponCode)  {
        CART cart = car.findById(idCart).orElseThrow(() -> new NotFoundException("Cart not found"));
        Set<Produit_Cart> cartItemsSet = cart.getItems();
        List<Produit_Cart> cartItemsList = new ArrayList<>();
        cartItemsList.addAll(cartItemsSet);
        Produit_Cart cartItem = cartItemsList.get(0);
        Produit produit = cartItem.getProduit();
        double x = 0.2;
        commande.setCommande_cart(cart);
        commande.setTax((long) (cart.getTotal_price() * x));
        commande.setCurrency("USD");
        commande.setLes_produits(produit.getNom());
        commande.setMethode("en ligne (par carte)");
        commande.setTotal_price(cart.getTotal_price()+commande.getTax()*((100-produit.getPourcentagePromotion())/100));
        commande.setTotal_weight(cart.getTotal_weight());
        commande.setBuyer_email(cart.getUser().getEmail());
       // commande.setShop_address(produit.getShopes().getAdresse());
        commande.setNbProd(cart.getNbProd());
        commande.setEtat(Etat.EDITABLE);
        commande.setPayment_mode(Payment_Mode.TRANSFER);
        commande.setDateCmd(LocalDateTime.now());
        commande.setBuyer_address(cart.getUser().getAdresseuser());
        commande.setSomme_volume(cart.getTotal_volume());
        produit.setQuantiteVendue(produit.getQuantiteVendue()+1);

        List<Produit_Cart> cartItems = new ArrayList<>(cart.getItems());
        List<String> shopNames = new ArrayList<>();
        List<String> productNames = new ArrayList<>();
        for (Produit_Cart cartIte : cartItems) {
            Produit produit2 = cartIte.getProduit();
            String shopName = produit2.getShopes().getNom();
            String produitname = produit2.getNom();
            shopNames.add(shopName);
            productNames.add(produitname);
        }
        commande.setShop_name(shopNames.toString());
        commande.setLes_produits(productNames.toString());

        if (couponCode != null && !couponCode.isEmpty()) {
            Coupon coupon = couponRepository.findByCode(couponCode);
            if (coupon != null && !coupon.isUsed()) {
                double discount = coupon.getDiscount();
                double total = cart.getTotal_price() + commande.getTax();
                double discountedTotal = total - (total * discount);
                commande.setTotal_price((long) discountedTotal);
                if (commande.getEtat()==Etat.VALIDATED){
                    coupon.setUsed(true);}
            }

        } else {
            // Pas de coupon appliqué
            commande.setTotal_price(cart.getTotal_price() + commande.getTax());
        }

        return cr.save(commande);
    }

    @Scheduled(fixedDelay =  18000000 ) // 3 minutes en millisecondes
    public void changeOrderStatus() {
        List<Commande> commandes = cr.findAll();
        for(Commande commande :commandes) {
            if (commande.getEtat() == Etat.EDITABLE) {
                System.out.println("change");
                commande.setEtat(Etat.WAITING);
            }
            cr.save(commande);
        }

    }

    @Override
    public Set<Commande> Afficher_AllCommandes(Integer idcart) {
        CART cart = car.findById(idcart).get();
        Set<Commande> commande = cart.getCommande_cart();
        return  commande;

    }

    @Override
    public List<Commande> Afficher_AllCommandesadmin() {
        return cr.findAll();
    }

    @Override
    public void Accepter_commande(Integer idcommande){
        // Commande commande = cr.traiterCommand();
        Commande cmd = cr.findById(idcommande).orElse(null);
        cmd.setEtat(Etat.VALIDATED);
        cr.save(cmd);
    }

    @Override
    public void Refuser_commande(Integer idcommande) {
        //Commande commande = cr.traiterCommand();
        Commande cmd = cr.findById(idcommande).orElse(null);
        //refuser
        //send mail avec motif de refus
        cmd.setEtat(Etat.REFUSED);
        cr.save(cmd);
    }


    @Override
    public Commande Afficher_Commandes(Integer idcart, Integer idcommande) {
        CART cart = car.findById(idcart).orElse(null);
        if (cart != null) {
            Set<Commande> commandeCart = cart.getCommande_cart();
            for (Commande commande : commandeCart) {
                if (commande.getId() == idcommande) {
                    return commande;
                }
            }
        }
        return null;
    }

    public List<Commande> rechercher(String parametre) {
        return cr.rechercher(parametre);
    }


    public String findCartIdWith3Orders(){
        Integer idcart = cr.thewinneroftheyear();
        CART cart = car.findById(idcart).orElse(null);
        System.out.println("Result SQl :"+idcart);
        return cart.getUser().getEmail() ;
    }

    // @Scheduled(cron = "0 0 0 * * ?")
    public void envoyerCouponPanier3Commandes() {
        // Récupérer l'email du client qui a effectué 3 commandes
        String email = findCartIdWith3Orders();

        // Générer un code aléatoire
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            code.append(characters.charAt(index));
        }
        // Créer un nouvel objet Coupon avec le code généré
        Coupon coupon = new Coupon();
        coupon.setCode(code.toString());
        coupon.setUsed(false);
        coupon.setDiscount(0.1);
        // Enregistrer le coupon dans la base de données
        couponRepository.save(coupon);

        // Envoyer un email contenant le code généré
        mailserv.sendSimpleEmail(email,"Hello,Congratulations! You have made a lot of orders. Here is your discount code"  +code,"discount coupon");

    }


    @Override
    public void Annuler_Commande(Integer idCommande) {
        Commande commande = cr.findById(idCommande).orElse(null);
        LocalDateTime currentTimeNow = LocalDateTime.now();
        LocalDateTime Limite = commande.getDateCmd().plusMinutes(300);
        if (currentTimeNow.isBefore(Limite) && commande.getEtat()!=Etat.VALIDATED) {
            cr.delete(commande);
        } else {
            throw new RuntimeException("La commande ne peut plus être annulée.");
        }
    }


}

