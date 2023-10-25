package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Contrat;
import com.example.cocomarket.Interfaces.IContrat;
import com.example.cocomarket.Repository.Contrat_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import java.util.List;

@Service
public class Contrat_Service implements IContrat {
    @Autowired
    Contrat_Repository contratrepo ;
    @Override
    public Contrat AddContratEtAffecter(Contrat contr) {
        return contratrepo.save(contr)
                ;
    }


    @Autowired
    private Contrat_Repository repo;

    public List<Contrat> listAll() {
        return repo.findAll(Sort.by("dateDebutContrat").ascending());
    }
}
