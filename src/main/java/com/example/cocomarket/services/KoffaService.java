package com.example.cocomarket.services;

import com.example.cocomarket.entity.Koffa;
import com.example.cocomarket.interfaces.IKoffa;
import com.example.cocomarket.repository.KoffaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KoffaService implements IKoffa {

    @Autowired
    private KoffaRepository koffaRepository;

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
