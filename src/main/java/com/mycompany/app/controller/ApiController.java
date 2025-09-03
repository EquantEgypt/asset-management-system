package com.mycompany.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/profile")
    public String profile(Principal principal) { return "{\"message\": \"Welcome, " + principal.getName() + "!\"}"; }
    @GetMapping("/admin")
    public String adminPanel() { return "{\"data\": \"Admin-only content\"}"; }
    @GetMapping("/manager")
    public String managerPanel() { return "{\"data\": \"Manager-only content\"}"; }
    @GetMapping("/it")
    public String itPanel() { return "{\"data\": \"IT-only content\"}"; }
    @GetMapping("/employee")
    public String employeePanel(Principal principal) { return "{\"data\": \"General employee content for " + principal.getName() + "\"}"; }
}

