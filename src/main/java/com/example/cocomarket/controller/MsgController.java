package com.example.cocomarket.controller;

import com.example.cocomarket.entity.MSG;
import com.example.cocomarket.interfaces.IMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    IMsg msgService;

    @GetMapping("/all")
    public ResponseEntity<List<MSG>> getAllMsgs() {
        List<MSG> msgs = msgService.getAllMsgs();
        return new ResponseEntity<List<MSG>>(msgs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MSG> getMsgById(@PathVariable Integer id) {
        Optional<MSG> msg = msgService.getMsgById(id);
        return new ResponseEntity<>(msg.orElse(null), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<MSG> addMsg(@RequestBody MSG msg) {
        return new ResponseEntity<>(msgService.addMsg(msg), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteMsg(@PathVariable Integer id) {
        msgService.deleteMsg(id);
    }



}
