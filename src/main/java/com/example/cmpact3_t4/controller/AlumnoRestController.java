package com.example.cmpact3_t4.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmpact3_t4.dto.AlumnoRequest;
import com.example.cmpact3_t4.entity.Alumno;
import com.example.cmpact3_t4.service.AlumnoService;

// CRUD REST (JSON) para probar con Postman
@RestController
@RequestMapping("/api/alumnos")
public class AlumnoRestController {

    private final AlumnoService alumnoService;

    public AlumnoRestController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // READ (todos o por carrera): GET /api/alumnos?carreraId=1
    @GetMapping
    public List<Alumno> listar(@RequestParam(required = false) Long carreraId) {
        return alumnoService.listar(carreraId);
    }

    // READ (uno): GET /api/alumnos/1
    @GetMapping("/{id}")
    public Alumno buscar(@PathVariable Long id) {
        return alumnoService.buscar(id);
    }

    // CREATE: POST /api/alumnos
    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody AlumnoRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.crear(datos));
    }

    // UPDATE: PUT /api/alumnos/1
    @PutMapping("/{id}")
    public Alumno actualizar(@PathVariable Long id, @RequestBody AlumnoRequest datos) {
        return alumnoService.actualizar(id, datos);
    }

    // DELETE: DELETE /api/alumnos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> noEncontrado(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> datosInvalidos(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
