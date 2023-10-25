package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Livraison;
import com.example.cocomarket.Entity.Raiting_DelevryMan;

import java.util.List;

public interface ILivraison {
    public Livraison addLivraison (Livraison l);
    public Livraison updateLivraison (Livraison l);
    public Livraison findbyidLivraison (Integer idliv);
    void deleteLiv(Integer idLiv);
    public List<Livraison> retrieveAllLiv();
    public Raiting_DelevryMan affecterrattingtolivaison(Integer id_l, Raiting_DelevryMan l);
     public List<Livraison> notaffected(String region);

}
