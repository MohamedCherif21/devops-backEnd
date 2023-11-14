package com.example.cocomarket.services;

import com.example.cocomarket.entity.Comentaire;
import com.example.cocomarket.entity.Publication;
import com.example.cocomarket.entity.User;
import com.example.cocomarket.interfaces.IPublication;
import com.example.cocomarket.repository.ComentaireRepository;
import com.example.cocomarket.repository.PublicationRepository;
import com.example.cocomarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class Publication__Service implements IPublication {
    @Autowired
    PublicationRepository questionRepository;
    @Autowired
    private ComentaireRepository commentaireRepository;

    @Autowired
    private UserRepository userRepository;

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
            publication.getComentaireDuPublication().add(commentaire);
            commentaireRepository.save(commentaire);
            questionRepository.save(publication);
        }

        }

    public Publication findMostCommentedPublication() {
        List<Publication> publications = questionRepository.findAll();
        Publication mostCommentedPublication = publications.get(0);
        for (int i = 1; i < publications.size(); i++) {
            Publication publication = publications.get(i);
            if (publication.getComentaireDuPublication().size() > mostCommentedPublication.getComentaireDuPublication().size()) {
                mostCommentedPublication = publication;
            }
        }
        return mostCommentedPublication;
    }
}
