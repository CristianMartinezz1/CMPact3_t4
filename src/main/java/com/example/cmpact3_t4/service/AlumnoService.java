package com.example.cmpact3_t4.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cmpact3_t4.dto.AlumnoRequest;
import com.example.cmpact3_t4.entity.Alumno;
import com.example.cmpact3_t4.entity.Carrera;
import com.example.cmpact3_t4.repository.AlumnoRepository;
import com.example.cmpact3_t4.repository.CarreraRepository;

// Lógica de negocio del CRUD. Los controladores (vistas y REST) solo llaman a estos métodos.
@Service
@Transactional
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final CarreraRepository carreraRepository;

    public AlumnoService(AlumnoRepository alumnoRepository, CarreraRepository carreraRepository) {
        this.alumnoRepository = alumnoRepository;
        this.carreraRepository = carreraRepository;
    }

    // READ: todos, o filtrados por carrera
    @Transactional(readOnly = true)
    public List<Alumno> listar(Long carreraId) {
        if (carreraId == null) {
            return alumnoRepository.findAllByOrderByNombre();
        }
        return alumnoRepository.findByCarreraIdOrderByNombre(carreraId);
    }

    // READ: uno por id
    @Transactional(readOnly = true)
    public Alumno buscar(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe el alumno con id " + id));
    }

    // CREATE
    public Alumno crear(AlumnoRequest datos) {
        validar(datos);
        if (alumnoRepository.existsByMatricula(datos.getMatricula().trim())) {
            throw new IllegalArgumentException("Ya existe un alumno con la matrícula " + datos.getMatricula());
        }
        Alumno alumno = new Alumno();
        copiarDatos(datos, alumno);
        return alumnoRepository.save(alumno);
    }

    // UPDATE
    public Alumno actualizar(Long id, AlumnoRequest datos) {
        Alumno alumno = buscar(id);
        validar(datos);
        if (alumnoRepository.existsByMatriculaAndIdNot(datos.getMatricula().trim(), id)) {
            throw new IllegalArgumentException("Ya existe otro alumno con la matrícula " + datos.getMatricula());
        }
        copiarDatos(datos, alumno);
        return alumnoRepository.save(alumno);
    }

    // DELETE
    public void eliminar(Long id) {
        Alumno alumno = buscar(id);
        alumnoRepository.delete(alumno);
    }

    // Convierte el alumno guardado a los datos del formulario (para editar)
    @Transactional(readOnly = true)
    public AlumnoRequest aFormulario(Long id) {
        Alumno alumno = buscar(id);
        AlumnoRequest datos = new AlumnoRequest();
        datos.setMatricula(alumno.getMatricula());
        datos.setNombre(alumno.getNombre());
        datos.setEmail(alumno.getEmail());
        datos.setSemestre(alumno.getSemestre());
        datos.setCarreraId(alumno.getCarrera().getId());
        return datos;
    }

    private void validar(AlumnoRequest datos) {
        if (vacio(datos.getMatricula()) || vacio(datos.getNombre())) {
            throw new IllegalArgumentException("La matrícula y el nombre son obligatorios");
        }
        if (datos.getCarreraId() == null) {
            throw new IllegalArgumentException("Selecciona una carrera");
        }
        if (datos.getSemestre() != null && (datos.getSemestre() < 1 || datos.getSemestre() > 12)) {
            throw new IllegalArgumentException("El semestre debe estar entre 1 y 12");
        }
    }

    // Aquí se arma la relación: se busca la Carrera por id y se asigna al Alumno
    private void copiarDatos(AlumnoRequest datos, Alumno alumno) {
        Carrera carrera = carreraRepository.findById(datos.getCarreraId())
                .orElseThrow(() -> new IllegalArgumentException("No existe la carrera con id " + datos.getCarreraId()));
        alumno.setMatricula(datos.getMatricula().trim());
        alumno.setNombre(datos.getNombre().trim());
        alumno.setEmail(vacio(datos.getEmail()) ? null : datos.getEmail().trim());
        alumno.setSemestre(datos.getSemestre());
        alumno.setCarrera(carrera);
    }

    private boolean vacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
