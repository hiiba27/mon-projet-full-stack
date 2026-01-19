package com.fst.rendement.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/") public String home() { return "Bienvenue dans Projet Rendement 🚀"; } 
	@GetMapping("/hello") public String hello() { return "Hello Spring Boot!"; }
    @GetMapping("/test")
    public String test() {
        return "SPRING OK";
    }
}
