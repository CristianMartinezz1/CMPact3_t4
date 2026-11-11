package com.example.cmpact3_t4.dto;

// Datos que llegan del formulario (@ModelAttribute) o de Postman (@RequestBody).
// La carrera se indica solo con su id; el Service la busca y arma la relación.
public class AlumnoRequest {

    private String matricula;
    private String nombre;
    private String email;
    private Integer semestre;
    private Long carreraId;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }
}
