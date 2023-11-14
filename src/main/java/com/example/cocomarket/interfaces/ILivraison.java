package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.Livraison;
import com.example.cocomarket.entity.RaitingDelevryMan;

import java.util.List;

public interface ILivraison {
    public Livraison addLivraison (Livraison l);
    public Livraison updateLivraison (Livraison l);
    public Livraison findbyidLivraison (Integer idliv);
    void deleteLiv(Integer idLiv);
    public List<Livraison> retrieveAllLiv();
    public RaitingDelevryMan affecterrattingtolivaison(Integer idL, RaitingDelevryMan l);
     public List<Livraison> notaffected(String region);

}
