package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Koffa;

import java.util.List;
import java.util.Optional;

public interface IKoffa {

    List<Koffa> getAllKoffas();

    Koffa getKoffaById(Integer id);

    Koffa saveKoffa(Koffa koffa);

    void deleteKoffaById(Integer id);
}
