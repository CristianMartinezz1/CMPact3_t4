package com.example.cmpact3_t4.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.cmpact3_t4.dto.AlumnoRequest;
import com.example.cmpact3_t4.entity.Carrera;
import com.example.cmpact3_t4.repository.AlumnoRepository;
import com.example.cmpact3_t4.repository.CarreraRepository;
import com.example.cmpact3_t4.service.AlumnoService;

// Si la base de datos está vacía, inserta carreras y unos alumnos de ejemplo
@Component
public class DatosIniciales implements CommandLineRunner {

    private final CarreraRepository carreraRepository;
    private final AlumnoRepository alumnoRepository;
    private final AlumnoService alumnoService;

    public DatosIniciales(CarreraRepository carreraRepository, AlumnoRepository alumnoRepository,
                          AlumnoService alumnoService) {
        this.carreraRepository = carreraRepository;
        this.alumnoRepository = alumnoRepository;
        this.alumnoService = alumnoService;
    }

    @Override
    public void run(String... args) {
        if (carreraRepository.count() == 0) {
            carreraRepository.save(new Carrera("ISC", "Ingeniería en Sistemas Computacionales"));
            carreraRepository.save(new Carrera("II", "Ingeniería Industrial"));
            carreraRepository.save(new Carrera("IGE", "Ingeniería en Gestión Empresarial"));
            carreraRepository.save(new Carrera("IE", "Ingeniería Electrónica"));
        }
        if (alumnoRepository.count() == 0) {
            Long isc = idDe("ISC");
            Long ii = idDe("II");
            Long ige = idDe("IGE");
            alumnoService.crear(alumno("23161016", "Cristian Martínez Pacheco", "cristian@ejemplo.com", 7, isc));
            alumnoService.crear(alumno("23161020", "Ana López Ruiz", "ana@ejemplo.com", 7, isc));
            alumnoService.crear(alumno("23162031", "Luis Hernández Soto", "luis@ejemplo.com", 5, ii));
            alumnoService.crear(alumno("23163045", "María García Torres", "maria@ejemplo.com", 3, ige));
        }
    }

    private Long idDe(String clave) {
        return carreraRepository.findAll().stream()
                .filter(c -> c.getClave().equals(clave))
                .findFirst().orElseThrow().getId();
    }

    private AlumnoRequest alumno(String matricula, String nombre, String email, int semestre, Long carreraId) {
        AlumnoRequest datos = new AlumnoRequest();
        datos.setMatricula(matricula);
        datos.setNombre(nombre);
        datos.setEmail(email);
        datos.setSemestre(semestre);
        datos.setCarreraId(carreraId);
        return datos;
    }
}
