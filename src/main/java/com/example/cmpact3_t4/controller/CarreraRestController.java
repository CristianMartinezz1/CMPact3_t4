package com.example.cmpact3_t4.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmpact3_t4.entity.Alumno;
import com.example.cmpact3_t4.entity.Carrera;
import com.example.cmpact3_t4.service.AlumnoService;
import com.example.cmpact3_t4.service.CarreraService;

@RestController
@RequestMapping("/api/carreras")
public class CarreraRestController {

    private final CarreraService carreraService;
    private final AlumnoService alumnoService;

    public CarreraRestController(CarreraService carreraService, AlumnoService alumnoService) {
        this.carreraService = carreraService;
        this.alumnoService = alumnoService;
    }

    // Lista de carreras (para saber qué carreraId usar al crear un alumno)
    @GetMapping
    public List<Carrera> listar() {
        return carreraService.listar();
    }

    // Alumnos de una carrera: GET /api/carreras/1/alumnos
    @GetMapping("/{id}/alumnos")
    public List<Alumno> alumnos(@PathVariable Long id) {
        carreraService.buscar(id);
        return alumnoService.listar(id);
    }
}
