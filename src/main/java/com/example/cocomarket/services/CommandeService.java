package com.example.cocomarket.services;

import com.example.cocomarket.entity.*;
import com.example.cocomarket.interfaces.ICommande;
import com.example.cocomarket.repository.*;
import com.example.cocomarket.config.EmailSenderService;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

@Service
public class CommandeService implements ICommande {


    @Mock
    private Commande_Repository commandeRepository;

    @Mock
    private LivraisonRepository livraisonRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private CommandeService service;
    @Autowired
    Commande_Repository cr;
    @Autowired
    LivraisonRepository lr;
    @Autowired
    UserRepository ur;
    @Autowired
    CartRepository car;

    @Autowired
    CouponRepository couponRepository ;

    @Autowired
    CouponService couponService;

    @Autowired
    EmailSenderService mailserv ;



    @Override
    public Livraison affectercamandtolivaison(String region, Livraison l) {
        List<Commande> c=cr.getnotaffectedCommand(region);
        LocalDate d;
        float x=0;
        float y=0;
        for (Commande i:c)
        {
            x+=i.getSommevolume();
            y+=i.getTotalweight();
            i.setEtat(Etat.VALIDATED);
          i.setLivraisonCommande(l);
        }
        l.setWeightL(y);
       l.setVolumeL(x);
       l.setRegion(region);
        d=LocalDate.now();
        l.setDateSortie(d);
        lr.save(l);
        return l;

        }

    @Override
    public Commande confirmerCommande(Commande commande, Integer idCart, String couponCode)  {
        CART cart = car.findById(idCart).orElseThrow(() -> new NotFoundException("Cart not found"));
        Set<ProduitCart> cartItemsSet = cart.getItems();
        List<ProduitCart> cartItemsList = new ArrayList<>();
        cartItemsList.addAll(cartItemsSet);
        ProduitCart cartItem = cartItemsList.get(0);
        Produit produit = cartItem.getProduit();
        double x = 0.2;
        commande.setCommandeCart(cart);
        commande.setTax((long) (cart.getTotal_price() * x));
        commande.setCurrency("USD");
        commande.setLesproduits(produit.getNom());
        commande.setMethode("en ligne (par carte)");
        commande.setTotalprice(cart.getTotal_price()+commande.getTax()*((100-produit.getPourcentagePromotion())/100));
        commande.setTotalweight(cart.getTotal_weight());
        commande.setBuyeremail(cart.getUser().getEmail());
        commande.setNbProd(cart.getNbProd());
        commande.setEtat(Etat.EDITABLE);
        commande.setPaymentmode(Payment_Mode.TRANSFER);
        commande.setDateCmd(LocalDateTime.now());
        commande.setBuyeraddress(cart.getUser().getAdresseuser());
        commande.setSommevolume(cart.getTotal_volume());
        produit.setQuantiteVendue(produit.getQuantiteVendue()+1);

        List<ProduitCart> cartItems = new ArrayList<>(cart.getItems());
        List<String> shopNames = new ArrayList<>();
        List<String> productNames = new ArrayList<>();
        for (ProduitCart cartIte : cartItems) {
            Produit produit2 = cartIte.getProduit();
            String shopName = produit2.getShopes().getNom();
            String produitname = produit2.getNom();
            shopNames.add(shopName);
            productNames.add(produitname);
        }
        commande.setShopname(shopNames.toString());
        commande.setLesproduits(productNames.toString());

        if (couponCode != null && !couponCode.isEmpty()) {
            Coupon coupon = couponRepository.findByCode(couponCode);
            if (coupon != null && !coupon.isUsed()) {
                double discount = coupon.getDiscount();
                double total = (cart.getTotal_price()) + (commande.getTax());
                double discountedTotal = total - (total * discount);
                commande.setTotalprice((long) discountedTotal);
                if (commande.getEtat()==Etat.VALIDATED){
                    coupon.setUsed(true);}
            }

        } else {
            // Pas de coupon appliqué
            commande.setTotalprice(cart.getTotal_price() + commande.getTax());
        }

        return cr.save(commande);
    }

    @Scheduled(fixedDelay =  18000000 ) // 3 minutes en millisecondes
    public void changeOrderStatus() {
        List<Commande> commandes = cr.findAll();
        for(Commande commande :commandes) {
            if (commande.getEtat() == Etat.EDITABLE) {
                commande.setEtat(Etat.WAITING);
            }
            cr.save(commande);
        }

    }

    @Override
    public Set<Commande> afficherAllCommandes(Integer idcart) {
        Optional<CART> optionalCart = car.findById(idcart);

        if (optionalCart.isPresent()) {
            CART cart = optionalCart.get();
            return cart.getCommande_cart();
        } else {
            // Handle the case where the Optional is empty, e.g., return an empty set or throw an exception.
            // For now, I'm returning an empty set as an example.
            return Collections.emptySet();
        }
    }


    @Override
    public List<Commande> afficherAllCommandesadmin() {
        return cr.findAll();
    }

    @Override
    public void accepterCommande(Integer idcommande) {
        Commande cmd = cr.findById(idcommande).orElse(null);
        if (cmd != null) {
            cmd.setEtat(Etat.VALIDATED);
            cr.save(cmd);
        }
    }

    @Test
    void testAccepterCommande() {
        Mockito.when(commandeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(new Commande()));

        service.accepterCommande(1);

        // Add assertions based on your business logic
        // For example, verify that the save method is called
        Mockito.verify(commandeRepository).save(ArgumentMatchers.any());
    }


    @Override
    public void refuserCommande(Integer idcommande) {
        Commande cmd = cr.findById(idcommande).orElse(null);
        if (cmd != null) {
        cmd.setEtat(Etat.REFUSED);
        cr.save(cmd);
        }
    }


    @Override
    public Commande afficherCommandes(Integer idcart, Integer idcommande) {
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


    public String findCartIdWith3Orders() {
        Integer idcart = cr.thewinneroftheyear();
        CART cart = car.findById(idcart).orElse(null);

        if (cart != null) {
            return cart.getUser().getEmail();
        } else {
            return null; // Or handle this case according to your requirements
        }
    }


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
        Coupon coupon = new Coupon();
        coupon.setCode(code.toString());
        coupon.setUsed(false);
        coupon.setDiscount(0.1);
        couponRepository.save(coupon);

        // Envoyer un email contenant le code généré
        mailserv.sendSimpleEmail(email,"Hello,Congratulations! You have made a lot of orders. Here is your discount code"  +code,"discount coupon");

    }


    @Override
    public void annulerCommande(Integer idCommande) {
        Commande commande = cr.findById(idCommande).orElse(null);

        if (commande != null) {
            LocalDateTime currentTimeNow = LocalDateTime.now();
            LocalDateTime limite = commande.getDateCmd().plusMinutes(300);

            if (currentTimeNow.isBefore(limite) && commande.getEtat() != Etat.VALIDATED) {
                cr.delete(commande);
            } else {
                throw new NullPointerException("La commande ne peut plus être annulée.");
            }
        }
    }

    @Test
    void testAnnulerCommande() {
        Mockito.when(commandeRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(new Commande()));

        service.annulerCommande(1);

        // Add assertions based on your business logic
        // For example, verify that the delete method is called
        Mockito.verify(commandeRepository).delete(ArgumentMatchers.any());
    }



}

