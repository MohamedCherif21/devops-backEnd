package com.example.cocomarket.Controller;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Raiting_DelevryMan;
import com.example.cocomarket.Interfaces.IRaiting_DelevryMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Ratting_Delivery")
public class Raiting_Delevery_Controller {
    @Autowired
    IRaiting_DelevryMan ir;
    @PostMapping("/add-Raiting")
    public Raiting_DelevryMan addRatting(@RequestBody Raiting_DelevryMan l) {
        Raiting_DelevryMan li = ir.addRaitingL(l);
        return li;
    }
    @PutMapping("/update-Raiting")
    public Raiting_DelevryMan updateRaiting(@RequestBody Raiting_DelevryMan l) {
        Raiting_DelevryMan li = ir.updateRaitingL(l);
        return li;
    }
    @DeleteMapping("/remove-Raiting/{Rait-id}")
    public void removeRaiting(@PathVariable("Rait-id") Integer RId) {

        ir.deleteRaitingL(RId);
    }
    @GetMapping("/retrieve-all-Liv")
    public List<Raiting_DelevryMan> getRaits() {
        List<Raiting_DelevryMan> listR = ir.retrieveAllRaitingL();
        return listR;
    }
    @GetMapping("/retrieve-Liv/{Rait-id}")
    public Raiting_DelevryMan getliv(@PathVariable("Rait-id") Integer idR) {
        Raiting_DelevryMan Raitting = ir.findbyidRaitingL(idR);
        return Raitting;
    }
}
