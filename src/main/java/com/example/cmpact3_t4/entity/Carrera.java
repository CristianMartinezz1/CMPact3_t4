package com.example.cmpact3_t4.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Entidad "uno" de la relación: una Carrera tiene muchos Alumnos
@JsonPropertyOrder({"id", "clave", "nombre"})
@Entity
@Table(name = "carreras")
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String clave;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Lado inverso de la relación: "mappedBy" apunta al atributo carrera de Alumno,
    // así que la llave foránea vive en la tabla alumnos (carrera_id)
    @JsonIgnore
    @OneToMany(mappedBy = "carrera")
    private List<Alumno> alumnos = new ArrayList<>();

    public Carrera() {
    }

    public Carrera(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
