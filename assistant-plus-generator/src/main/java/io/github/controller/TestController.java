package io.github.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author Genius
 *
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/testGet")
    public void test(){
        System.out.println("test");
    }

    @PostMapping("/testPost")
    public void testPost(@RequestBody String body){
        System.out.println("testPost");
    }

    @GetMapping("/testGet/{id}")
    public void testGetById(@PathVariable("id") String id){
        System.out.println("testGet");
    }

    @GetMapping("/testNotRestful")
    public void testNotRestful(@RequestParam("id") String id,@RequestParam("name") String name){
        System.out.println("testNotRestful");
    }
}
