package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Koffa;

import java.util.List;

public interface IKoffa {

    List<Koffa> getAllKoffas();

    Koffa getKoffaById(Integer id);

    Koffa saveKoffa(Koffa koffa);

    void deleteKoffaById(Integer id);
}
