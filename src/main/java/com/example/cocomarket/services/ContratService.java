package com.example.cocomarket.services;

import com.example.cocomarket.entity.Contrat;
import com.example.cocomarket.interfaces.IContrat;
import com.example.cocomarket.repository.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import java.util.List;

@Service
public class ContratService implements IContrat {
    @Autowired
    ContratRepository contratrepo ;
    @Override
    public Contrat addContratEtAffecter(Contrat contr) {
        return contratrepo.save(contr)
                ;
    }


    @Autowired
    private ContratRepository repo;

    public List<Contrat> listAll() {
        return repo.findAll(Sort.by("dateDebutContrat").ascending());
    }
}
