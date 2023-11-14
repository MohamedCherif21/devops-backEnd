package com.example.cocomarket.interfaces;

import com.example.cocomarket.entity.MSG;

import java.util.List;
import java.util.Optional;

public interface IMsg {
    List<MSG> getAllMsgs();

    Optional<MSG> getMsgById(Integer id);

    MSG addMsg(MSG msg);

    void deleteMsg(Integer id);
}
