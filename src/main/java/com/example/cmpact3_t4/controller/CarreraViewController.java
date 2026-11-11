package com.example.cmpact3_t4.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.cmpact3_t4.service.CarreraService;

// Muestra la relación desde el otro lado: cada Carrera con sus Alumnos (@OneToMany)
@Controller
@RequestMapping("/carreras")
public class CarreraViewController {

    private final CarreraService carreraService;

    @Value("${app.nombre-proyecto}")
    private String nombreProyecto;

    public CarreraViewController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("nombreProyecto", nombreProyecto);
        model.addAttribute("carreras", carreraService.listar());
        return "carreras/lista";
    }
}
