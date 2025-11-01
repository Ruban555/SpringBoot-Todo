package com.example.HelloWorld.Common;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Requesting")
public class requestes {
    @GetMapping("/{id}")
    String id(@PathVariable String id){
        return "hellow"+ " "+id;
    }

    @GetMapping
    String name(@RequestParam String names){
        return names;
    }

    @PostMapping("/create")
    String create(@RequestBody String name){
        return name;
    }
}
