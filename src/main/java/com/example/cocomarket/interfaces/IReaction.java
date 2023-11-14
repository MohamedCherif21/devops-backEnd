package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.ReactionType;

public interface IReaction {


    void ajouterReaction(Integer userId, Integer publicationId, ReactionType reactionType);

}
