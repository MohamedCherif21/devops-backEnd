package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.Koffa;
import com.example.cocomarket.Interfaces.IKoffa;
import com.example.cocomarket.Repository.Koffa_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Koffa_Service implements IKoffa {

    @Autowired
    private Koffa_Repository koffaRepository;

    @Override
    public List<Koffa> getAllKoffas() {
        return koffaRepository.findAll();
    }

    @Override
    public Koffa getKoffaById(Integer id) {
        Optional<Koffa> koffa = koffaRepository.findById(id);
        return koffa.orElse(null);
    }

    @Override
    public Koffa saveKoffa(Koffa koffa) {
        return koffaRepository.save(koffa);
    }

    @Override
    public void deleteKoffaById(Integer id) {
        koffaRepository.deleteById(id);
    }
}
