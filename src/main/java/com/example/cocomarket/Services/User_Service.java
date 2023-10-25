package com.example.cocomarket.Services;

import com.example.cocomarket.Controller.User_Controller;
import com.example.cocomarket.Entity.*;
import com.example.cocomarket.Interfaces.IUser;
import com.example.cocomarket.Repository.*;
import com.example.cocomarket.config.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class User_Service implements IUser {
    @Autowired
    User_Repository ur;
    @Autowired
    Vehicule_Repository vr;

    @Autowired
    Commande_Repository comr ;

    @Autowired
    EmailSenderService mailserv ;

    @Autowired
    AuthorityRepository Ar;

    @Override
    public void assignusertoCar(Integer LId, Integer UId) {
        User u=ur.findById(UId).orElse(null);
        Vehicule v=vr.findById(LId).orElse(null);
        u.setCar(v);
        ur.save(u);

    }

    @Override
    public Vehicule assignusertoCarCreate(Integer UId, Vehicule v) {
        User u=ur.findById(UId).orElse(null);
        u.setCar(v);
        ur.save(u);
     return v;
    }

    @Override
    public void Accepter_commande(Integer idcommande){
        Commande commande = comr.traiterCommand();
        //accepter
        commande.setEtat(Etat.VALIDATED);
        //send mail avec les details
        mailserv.sendSimpleEmail(commande.getBuyer_email(),"your order has been validated","order validation");
    }

    @Override
    public void Refuser_commande(Integer idcommande) {
        Commande commande = comr.traiterCommand();
        //refuser
        commande.setEtat(Etat.REFUSED);
        //send mail avec motif de refus
        mailserv.sendSimpleEmail(commande.getBuyer_email(),"your order has been Rejected","order rejection");
    }

    public List<User> GetUserByRole(String role) {
        List<Autority> auths=Ar.findAll();
        List<User> users=new ArrayList<>();
        for (Autority a :auths){
            if (a.getName().equals(role))
                users.add(a.getUserAuth());

        }
        return users;
    }

}
