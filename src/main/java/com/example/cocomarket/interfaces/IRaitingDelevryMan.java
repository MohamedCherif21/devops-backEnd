package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.RaitingDelevryMan;

import java.util.List;

public interface IRaitingDelevryMan {
    public RaitingDelevryMan addRaitingL (RaitingDelevryMan l);
    public RaitingDelevryMan updateRaitingL (RaitingDelevryMan l);
    public RaitingDelevryMan findbyidRaitingL (Integer idliv);
    void deleteRaitingL(Integer idLiv);
    public List<RaitingDelevryMan> retrieveAllRaitingL();
}
