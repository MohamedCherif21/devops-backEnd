package com.example.cocomarket.services;

import com.example.cocomarket.entity.MSG;
import com.example.cocomarket.interfaces.IMsg;
import com.example.cocomarket.repository.MsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MsgService implements IMsg {
    @Autowired
    MsgRepository msgRepository;




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
