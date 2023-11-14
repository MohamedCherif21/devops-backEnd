package com.example.cocomarket.controller;

import com.example.cocomarket.interfaces.IServiceMapbox;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MapBoxController {
    private IServiceMapbox iServiceMapBox;

    @GetMapping("/MapBox/{x1}/{y1}/{x2}/{y2}")
    public Map<String , String> getInfo(@PathVariable String x1 ,@PathVariable String y1 ,@PathVariable String x2 , @PathVariable String y2) throws IOException{
        return iServiceMapBox.getInfo(x1,y1,x2,y2);
    }

}
