package com.example.HelloWorld.Common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping("/hello")
    String helloworld(){
        return "Hello World!";
    }

    @GetMapping("/world")
    String world(){
        return "jshdgfjksajksdjksd";
    }
}
