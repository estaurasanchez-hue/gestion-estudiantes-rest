package com.umng.gestionestudiantes.service;

import com.umng.gestionestudiantes.model.Estudiante;
import com.umng.gestionestudiantes.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    @Transactional
    public Estudiante crear(Estudiante estudiante) {
        estudiante.setId(null);
        if (estudiante.getActivo() == null) {
            estudiante.setActivo(true);
        }
        return estudianteRepository.save(estudiante);
    }

    @Transactional
    public Optional<Estudiante> actualizar(Long id, Estudiante datosActualizados) {
        return estudianteRepository.findById(id).map(estudianteExistente -> {
            estudianteExistente.setNombre(datosActualizados.getNombre());
            estudianteExistente.setApellido(datosActualizados.getApellido());
            estudianteExistente.setEmail(datosActualizados.getEmail());
            estudianteExistente.setCarrera(datosActualizados.getCarrera());
            estudianteExistente.setSemestre(datosActualizados.getSemestre());
            estudianteExistente.setTelefono(datosActualizados.getTelefono());
            if (datosActualizados.getActivo() != null) {
                estudianteExistente.setActivo(datosActualizados.getActivo());
            }
            return estudianteRepository.save(estudianteExistente);
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean existeEmail(String email) {
        return estudianteRepository.findByEmail(email).isPresent();
    }
}
