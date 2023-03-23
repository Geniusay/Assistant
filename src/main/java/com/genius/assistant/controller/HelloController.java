package com.genius.assistant.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Genius
 *
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/helloGet")
    public void hello(){
        System.out.println("hello");
    }

    @PostMapping("/helloPost")
    public void helloPost(@RequestBody String body){
        System.out.println("helloPost");
    }

    @GetMapping("/helloGet/{id}/{name}")
    public void helloGetById(@PathVariable("id") String id,@PathVariable("name") String name){
        System.out.println("helloGet");
    }

    @GetMapping("/helloNotRestful")
    public void helloNotRestful(@RequestParam("id") String id,@RequestParam("name") String name){
        System.out.println("helloNotRestful");
    }
}
