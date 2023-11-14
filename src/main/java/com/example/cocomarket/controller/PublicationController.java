package com.example.cocomarket.controller;

import com.example.cocomarket.entity.Comentaire;
import com.example.cocomarket.entity.Publication;
import com.example.cocomarket.interfaces.IPublication;
import com.example.cocomarket.services.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")

public class PublicationController {
    @Autowired
    IPublication questionService;

    @Autowired
    private PublicationService publicationService;

    @GetMapping("/all")
    public ResponseEntity<List<Publication>> getAllQuestions() {
        List<Publication> publications = questionService.getAllQuestions();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> getQuestionById(@PathVariable Integer id) {
        Optional<Publication> question = questionService.getQuestionById(id);
        return new ResponseEntity<>(question.orElse(null), HttpStatus.OK);
    }


    @PostMapping("/publications/{userId}")
    public ResponseEntity<String> ajouterPublication(@PathVariable Integer userId, @RequestBody Publication publication) {
        publicationService.ajouterPublication(userId, publication);
        return new ResponseEntity<>("La publication a été ajoutée avec succès.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
    }




    @PostMapping("/{publicationId}/commentaires")
    public ResponseEntity<String> ajouterCommentaire(@PathVariable Integer publicationId, @RequestBody Comentaire commentaire) {
        publicationService.ajouterCommentaire(publicationId, commentaire);
        return new ResponseEntity<>("Le commentaire a été ajouté avec succès à la publication.", HttpStatus.CREATED);
    }

    @GetMapping("/most-commented")
    public ResponseEntity<Publication> getMostCommentedPublication() {
        Publication mostCommentedPublication = publicationService.findMostCommentedPublication();
        return ResponseEntity.ok(mostCommentedPublication);
    }


 }
