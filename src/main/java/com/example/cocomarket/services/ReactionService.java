package com.example.cocomarket.services;

import com.example.cocomarket.entity.Publication;
import com.example.cocomarket.entity.Reaction;
import com.example.cocomarket.entity.ReactionType;
import com.example.cocomarket.entity.User;
import com.example.cocomarket.interfaces.IReaction;
import com.example.cocomarket.repository.PublicationRepository;
import com.example.cocomarket.repository.ReactionRepository;
import com.example.cocomarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionService implements IReaction {


    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;


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
