package com.example.cmpact3_t4.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cmpact3_t4.entity.Carrera;
import com.example.cmpact3_t4.repository.CarreraRepository;

@Service
@Transactional(readOnly = true)
public class CarreraService {

    private final CarreraRepository carreraRepository;

    public CarreraService(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    public List<Carrera> listar() {
        return carreraRepository.findAll(Sort.by("nombre"));
    }

    public Carrera buscar(Long id) {
        return carreraRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe la carrera con id " + id));
    }
}
