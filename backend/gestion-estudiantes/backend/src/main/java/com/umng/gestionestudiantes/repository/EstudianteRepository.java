package com.umng.gestionestudiantes.repository;

import com.umng.gestionestudiantes.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByEmail(String email);

    List<Estudiante> findByActivoTrue();

    List<Estudiante> findByCarreraContainingIgnoreCase(String carrera);
}
