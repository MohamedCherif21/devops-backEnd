package com.example.cocomarket.Controller;


import com.example.cocomarket.Entity.ReactionType;
import com.example.cocomarket.Services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Reaction")
public class ReactionController {   

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/{userId}/publications/{publicationId}/reactions")
    public ResponseEntity<String> ajouterReaction(@PathVariable Integer userId, @PathVariable Integer publicationId, @RequestParam ReactionType reactionType) {
        reactionService.ajouterReaction(userId, publicationId, reactionType);
        return new ResponseEntity<>("La réaction a été ajoutée avec succès.", HttpStatus.CREATED);
    }
}
