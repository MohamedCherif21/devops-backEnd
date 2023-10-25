package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Publication;
import com.example.cocomarket.Entity.Reaction;
import com.example.cocomarket.Entity.ReactionType;
import com.example.cocomarket.Entity.User;
import com.example.cocomarket.Interfaces.IReaction;
import com.example.cocomarket.Repository.Publication__Repository;
import com.example.cocomarket.Repository.ReactionRepository;
import com.example.cocomarket.Repository.User_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionService implements IReaction {


    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private Publication__Repository publicationRepository;

    @Autowired
    private User_Repository userRepository;


@Override
    public void ajouterReaction(Integer userId, Integer publicationId, ReactionType reactionType) {
    User user = userRepository.findById(userId).orElse(null);
    Publication publication = publicationRepository.findById(publicationId).orElse(null);

    if (user != null && publication != null) {
        Reaction reaction = new Reaction();
        reaction.setReactionType(reactionType);
        reaction.setUser(user);
        reaction.setPublication(publication);

        reactionRepository.save(reaction);
    }
}
}
