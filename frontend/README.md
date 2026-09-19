# Gestion de Estudiantes - Frontend Angular

Frontend Angular (standalone components) que consume la API RESTful de Gestion de Estudiantes.

Sigue la misma estructura y buenas practicas del material de clase "CRUD de Usuarios con Angular y una API REST".

## Requisitos

- Node.js 18 o superior
- Angular CLI 17
- Backend Spring Boot corriendo en http://localhost:8080

## Instalacion y ejecucion

```bash
cd gestion-estudiantes-angular
npm install
ng serve
```

Abrir en el navegador: http://localhost:4200

## Estructura del proyecto

```
src/app/
  models/
    estudiante.model.ts     -> Interfaz Estudiante
  services/
    estudiante.service.ts   -> Llamadas HTTP (GET, POST, PUT, DELETE)
  app.component.ts          -> Estado y logica del CRUD + filtros
  app.component.html        -> Formulario + tabla + filtros
  app.component.css         -> Estilos del componente
  app.config.ts             -> Registro de HttpClient
```

## Endpoints consumidos

| Metodo | URL                                      | Descripcion              |
|--------|------------------------------------------|--------------------------|
| GET    | /api/estudiantes                         | Listar (con filtros)     |
| GET    | /api/estudiantes/{id}                    | Obtener uno              |
| POST   | /api/estudiantes                         | Crear                    |
| PUT    | /api/estudiantes/{id}                    | Actualizar               |
| DELETE | /api/estudiantes/{id}                    | Eliminar                 |

Filtros disponibles (query params):
- `?carrera=Sistemas`
- `?semestre=5`
- `?carrera=Sistemas&semestre=5`

## Notas

- CORS ya esta habilitado en el backend Spring Boot (@CrossOrigin).
- La base de datos del backend es H2 en archivo (persistente).
- Se usan signals de Angular 17 para el estado reactivo.
