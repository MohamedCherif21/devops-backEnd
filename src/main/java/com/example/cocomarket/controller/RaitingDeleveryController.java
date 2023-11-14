package com.example.cocomarket.controller;

import com.example.cocomarket.entity.RaitingDelevryMan;
import com.example.cocomarket.interfaces.IRaitingDelevryMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Ratting_Delivery")
public class RaitingDeleveryController {
    @Autowired
    IRaitingDelevryMan ir;
    @PostMapping("/add-Raiting")
    public RaitingDelevryMan addRatting(@RequestBody RaitingDelevryMan l) {
        return ir.addRaitingL(l);
    }
    @PutMapping("/update-Raiting")
    public RaitingDelevryMan updateRaiting(@RequestBody RaitingDelevryMan l) {
        return ir.updateRaitingL(l);
    }
    @DeleteMapping("/remove-Raiting/{Rait-id}")
    public void removeRaiting(@PathVariable("Rait-id") Integer integer) {

        ir.deleteRaitingL(integer);
    }
    @GetMapping("/retrieve-all-Liv")
    public List<RaitingDelevryMan> getRaits() {
        return ir.retrieveAllRaitingL();
    }
    @GetMapping("/retrieve-Liv/{Rait-id}")
    public RaitingDelevryMan getliv(@PathVariable("Rait-id") Integer idR) {
        return ir.findbyidRaitingL(idR);
    }
}
