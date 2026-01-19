package com.fst.rendement.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chef")
public class ChefController {

    @GetMapping("/dashboard")
    public String chefDashboard() {
        return "Bienvenue Chef d'équipe - accès limité";
    }
}
