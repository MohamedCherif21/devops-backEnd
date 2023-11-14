package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Comentaire;
import com.example.cocomarket.entity.Publication;

import java.util.List;
import java.util.Optional;

public interface IPublication {
    public List<Publication> getAllQuestions();
    public Optional<Publication> getQuestionById(Integer id);


    void ajouterPublication(Integer userId, Publication publication);

    public void deleteQuestion(Integer id);

    void ajouterCommentaire(Integer publicationId, Comentaire commentaire);
}
