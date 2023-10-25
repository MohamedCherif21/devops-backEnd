package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.ReactionType;

public interface IReaction {


    void ajouterReaction(Integer userId, Integer publicationId, ReactionType reactionType);

}
