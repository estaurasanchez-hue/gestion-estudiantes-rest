package com.umng.gestionestudiantes.controller;

import com.umng.gestionestudiantes.model.Estudiante;
import com.umng.gestionestudiantes.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.listarTodos();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEstudiante(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteService.buscarPorId(id);
        if (estudiante.isPresent()) {
            return ResponseEntity.ok(estudiante.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Estudiante no encontrado con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearEstudiante(@Valid @RequestBody Estudiante estudiante) {
        if (estudianteService.existeEmail(estudiante.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Ya existe un estudiante registrado con el correo: " + estudiante.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        Estudiante nuevo = estudianteService.crear(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEstudiante(@PathVariable Long id,
                                                  @Valid @RequestBody Estudiante estudiante) {
        Optional<Estudiante> actualizado = estudianteService.actualizar(id, estudiante);
        if (actualizado.isPresent()) {
            return ResponseEntity.ok(actualizado.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Estudiante no encontrado con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEstudiante(@PathVariable Long id) {
        boolean eliminado = estudianteService.eliminar(id);
        if (eliminado) {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Estudiante eliminado correctamente");
            return ResponseEntity.ok(respuesta);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Estudiante no encontrado con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
