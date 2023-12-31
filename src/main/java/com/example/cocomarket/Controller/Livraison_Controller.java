package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Raiting_DelevryMan;
import com.example.cocomarket.Interfaces.ILivraison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Livraison")
public class Livraison_Controller {
    @Autowired
    ILivraison il;
    @PostMapping("/add-Livraison")
    public Livraison addLivraison(@RequestBody Livraison l) {
        Livraison li = il.addLivraison(l);
        return li;
    }
    @PutMapping("/update-Livraison")
    public Livraison updatelivration(@RequestBody Livraison l) {
        Livraison li = il.updateLivraison(l);
        return li;
    }
    @DeleteMapping("/remove-livraison/{liv-id}")
    public void removeLiv(@PathVariable("liv-id") Integer livId) {

        il.deleteLiv(livId);
    }
    @GetMapping("/retrieve-all-Liv")
    public List<Livraison> getlivs() {
        List<Livraison> listLiv = il.retrieveAllLiv();
        return listLiv;
    }
    @GetMapping("/retrieve-Liv/{liv-id}")
    public Livraison getliv(@PathVariable("liv-id") Integer idliv) {
       Livraison Liv = il.findbyidLivraison(idliv);
        return Liv;
    }
    @PostMapping("/add-assign-Ratting/{idL}")
    @ResponseBody
    public Raiting_DelevryMan addLivwithcommand(@RequestBody Raiting_DelevryMan l,
                                       @PathVariable("idL") Integer idL)
    {
        Raiting_DelevryMan ratting = il.affecterrattingtolivaison(idL,l);
        return ratting;
    }
    @GetMapping("/retrieve-Liv-notaffected/{region}")
    public  List<Livraison> getlivnotaffected(@PathVariable("region") String region) {
       List <Livraison> Liv = il.notaffected(region);
        return Liv;
    }
}
