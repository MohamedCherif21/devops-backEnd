package com.example.cocomarket.services;
import com.example.cocomarket.Entity.MSG;
import com.example.cocomarket.Repository.Msg__Repository;
import com.example.cocomarket.Services.Msg__Service;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class Msg__ServiceMock {
    @Mock
    private Msg__Repository msgRepository;

    @InjectMocks
    private Msg__Service msgService;


    @Test
    void getAllMsgs() {

        List<MSG> msgs = Arrays.asList(new MSG(), new MSG());
        when(msgRepository.findAll()).thenReturn(msgs);
        List<MSG> result = msgService.getAllMsgs();
        assertEquals(2, result.size());
    }

    @Test
    void getMsgById() {

        int msgId = 1;
        MSG msg = new MSG();
        when(msgRepository.findById(msgId)).thenReturn(Optional.of(msg));
        Optional<MSG> result = msgService.getMsgById(msgId);
        assertEquals(msg, result.orElse(null));
    }

    @Test
    void addMsg() {

        MSG msg = new MSG();
        when(msgRepository.save(msg)).thenReturn(msg);
        MSG result = msgService.addMsg(msg);
        assertEquals(msg, result);
    }

    @Test
    void deleteMsg() {

        int msgId = 1;
        msgService.deleteMsg(msgId);
        verify(msgRepository, times(1)).deleteById(msgId);
    }
}
