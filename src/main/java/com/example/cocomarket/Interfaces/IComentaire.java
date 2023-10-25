package com.example.cocomarket.Interfaces;

import com.example.cocomarket.Entity.Comentaire;

import java.util.List;
import java.util.Optional;

public interface IComentaire {
    public List<Comentaire> getAllResponses();
    public Optional<Comentaire> getResponseById(Integer id);
    public Comentaire addResponse(Comentaire comentaire);
    public void deleteResponse(Integer id);

}
