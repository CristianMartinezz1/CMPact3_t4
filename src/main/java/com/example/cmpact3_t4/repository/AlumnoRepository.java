package com.example.cmpact3_t4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cmpact3_t4.entity.Alumno;

// JpaRepository ya trae findAll, findById, save, deleteById, count...
// Los métodos de abajo los genera Spring Data a partir de su nombre (sin escribir SQL)
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByCarreraIdOrderByNombre(Long carreraId);

    List<Alumno> findAllByOrderByNombre();

    boolean existsByMatricula(String matricula);

    boolean existsByMatriculaAndIdNot(String matricula, Long id);
}
