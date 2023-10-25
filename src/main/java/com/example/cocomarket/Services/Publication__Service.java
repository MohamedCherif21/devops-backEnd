package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Comentaire;
import com.example.cocomarket.Entity.Publication;
import com.example.cocomarket.Entity.User;
import com.example.cocomarket.Interfaces.IPublication;
import com.example.cocomarket.Repository.Comentaire_Repository;
import com.example.cocomarket.Repository.Publication__Repository;
import com.example.cocomarket.Repository.User_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class Publication__Service implements IPublication {
    @Autowired
    Publication__Repository questionRepository;
    @Autowired
    private Comentaire_Repository commentaireRepository;

    @Autowired
    private User_Repository userRepository;

    @Override
    public List<Publication> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Publication> getQuestionById(Integer id) {
        return questionRepository.findById(id);
    }


@Override
    public void ajouterPublication(Integer userId, Publication publication) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            publication.setDatePub(LocalDate.now());
            publication.setUserPublication(user);
            questionRepository.save(publication);
        }
    }

    @Override
    public void deleteQuestion(Integer id) {
        questionRepository.deleteById(id);
    }




    @Override
    public void ajouterCommentaire(Integer publicationId, Comentaire commentaire) {
        Publication publication = questionRepository.findById(publicationId).orElse(null);
        if (publication != null) {
            commentaire.setDateCmnt(LocalDate.now());
            publication.getComentaire_Du_Publication().add(commentaire);
            commentaire.setPublication(publication);
            commentaireRepository.save(commentaire);
            questionRepository.save(publication);
        }

        }

    public Publication findMostCommentedPublication() {
        List<Publication> publications = questionRepository.findAll();
        Publication mostCommentedPublication = publications.get(0);
        for (int i = 1; i < publications.size(); i++) {
            Publication publication = publications.get(i);
            if (publication.getComentaire_Du_Publication().size() > mostCommentedPublication.getComentaire_Du_Publication().size()) {
                mostCommentedPublication = publication;
            }
        }
        return mostCommentedPublication;
    }
}
