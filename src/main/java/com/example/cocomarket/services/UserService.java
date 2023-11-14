package com.example.cocomarket.services;

import com.example.cocomarket.entity.*;
import com.example.cocomarket.interfaces.IUser;
import com.example.cocomarket.repository.*;
import com.example.cocomarket.config.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUser {
    @Autowired
    UserRepository ur;
    @Autowired
    VehiculeRepository vr;

    @Autowired
    Commande_Repository comr ;

    @Autowired
    EmailSenderService mailserv ;

    @Autowired
    AuthorityRepository ar;

    @Override
    public void assignusertoCar(Integer lid, Integer uId) {
        User u=ur.findById(uId).orElse(null);
        Vehicule v=vr.findById(lid).orElse(null);
        u.setCar(v);
        ur.save(u);

    }

    @Override
    public Vehicule assignusertoCarCreate(Integer uId, Vehicule v) {
        User u=ur.findById(uId).orElse(null);
        u.setCar(v);
        ur.save(u);
     return v;
    }

    @Override
    public void accepterCommande(Integer idcommande){
        Commande commande = comr.traiterCommand();
        //accepter
        commande.setEtat(Etat.VALIDATED);
        //send mail avec les details
        mailserv.sendSimpleEmail(commande.getBuyeremail(),"your order has been validated","order validation");
    }

    @Override
    public void refuserCommande(Integer idcommande) {
        Commande commande = comr.traiterCommand();
        //refuser
        commande.setEtat(Etat.REFUSED);
        //send mail avec motif de refus
        mailserv.sendSimpleEmail(commande.getBuyeremail(),"your order has been Rejected","order rejection");
    }

    public List<User> GetUserByRole(String role) {
        List<Autority> auths= ar.findAll();
        List<User> users=new ArrayList<>();
        for (Autority a :auths){
            if (a.getName().equals(role))
                users.add(a.getUserAuth());

        }
        return users;
    }

}
