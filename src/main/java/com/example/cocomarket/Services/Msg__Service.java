package com.example.cocomarket.Services;

import com.example.cocomarket.Entity.MSG;
import com.example.cocomarket.Interfaces.IMsg;
import com.example.cocomarket.Repository.Msg__Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Msg__Service implements IMsg {
    @Autowired
    Msg__Repository msgRepository;




    @Override
    public List<MSG> getAllMsgs() {
        return msgRepository.findAll();
    }

    @Override
    public Optional<MSG> getMsgById(Integer id) {
        return msgRepository.findById(id);
    }

    @Override
    public MSG addMsg(MSG msg) {
        return msgRepository.save(msg);
    }

    @Override
    public void deleteMsg(Integer id) {
        msgRepository.deleteById(id);
    }




}
