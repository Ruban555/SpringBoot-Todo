package com.example.HelloWorld.Common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class summing {
    @GetMapping("/sum")
    int sum(){
        int a=10;
        int b=20;
        return a+b;
    }
}
