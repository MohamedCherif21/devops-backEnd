package com.example.cocomarket.controller;

import com.example.cocomarket.entity.Comentaire;
import com.example.cocomarket.interfaces.IComentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/response")

public class ComentaireController {
    @Autowired
    IComentaire responseService;

    @GetMapping("/all")
    public ResponseEntity<List<Comentaire>> getAllResponses() {
        List<Comentaire> response = responseService.getAllResponses();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentaire> getResponseById(@PathVariable Integer id) {
        Optional<Comentaire> response = responseService.getResponseById(id);
        return new ResponseEntity<>(response.orElse(null), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Comentaire> addResponse(@RequestBody Comentaire comentaire) {
        return new ResponseEntity<>(responseService.addResponse(comentaire), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteResponse(@PathVariable Integer id) {
        responseService.deleteResponse(id);
    }
}
