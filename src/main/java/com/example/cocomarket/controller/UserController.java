package com.example.cocomarket.controller;

import com.example.cocomarket.entity.Vehicule;
import com.example.cocomarket.interfaces.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
public class UserController {
    @Autowired
    IUser iu;

    @PutMapping(value="/affecter-Car-user/{LId}/{UId}")
    public void affectertUserToCar(@PathVariable("LId") Integer lId,
                                                 @PathVariable("UId")Integer uId){

        iu.assignusertoCar(lId,uId);
    }
    @PutMapping(value="/affecter-Car-user-create/{UId}")
    public Vehicule affectertUserToCarCreate(@RequestBody Vehicule v,
                                   @PathVariable("UId")Integer uId) {

        return iu.assignusertoCarCreate(uId, v);
    }

        @PostMapping("/Accepter_commande/{idCommande}")
        public void accepter (@PathVariable("idCommande") Integer idcommande){
            iu.accepterCommande(idcommande);
        }

        @PostMapping("/Refuser_commande/{idCommande}")
        public void refuser (@PathVariable("idCommande") Integer idcommande){
            iu.refuserCommande(idcommande);
        }

}
