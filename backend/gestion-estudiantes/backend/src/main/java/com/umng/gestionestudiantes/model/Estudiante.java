package com.umng.gestionestudiantes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false, length = 80)
    private String apellido;

    @NotBlank(message = "El correo electronico es obligatorio")
    @Email(message = "El formato del correo no es valido")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "La carrera es obligatoria")
    @Column(nullable = false, length = 100)
    private String carrera;

    @NotNull(message = "El semestre es obligatorio")
    @Positive(message = "El semestre debe ser un numero positivo")
    @Column(nullable = false)
    private Integer semestre;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;

    public Estudiante() {
    }

    public Estudiante(String nombre, String apellido, String email, String carrera, Integer semestre, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.carrera = carrera;
        this.semestre = semestre;
        this.telefono = telefono;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
