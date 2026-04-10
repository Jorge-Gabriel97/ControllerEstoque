package br.com.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AplicacaoController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Adicione este bloco:
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}