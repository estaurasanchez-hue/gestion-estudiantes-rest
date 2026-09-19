# Gestion de Estudiantes - Servicio RESTful

Proyecto de implementacion completa de un servicio RESTful con base de datos y aplicacion cliente web.
Cumple los requisitos de la Actividad No. 3 - Unidad 4: Implementacion de Componentes de Desarrollo de Software.

## Descripcion

Sistema de gestion de estudiantes que permite realizar las operaciones basicas de un servicio RESTful:

- GET: consultar listado y consulta individual
- POST: registrar nuevo estudiante
- PUT: actualizar datos de un estudiante
- DELETE: eliminar un estudiante

La solucion aplica principios de orientacion a objetos, reutilizacion de componentes (modelo, repositorio, servicio y controlador) y separacion entre backend y frontend.

## Estructura del proyecto

```
gestion-estudiantes/
├── backend/                          # API RESTful (Spring Boot)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/umng/gestionestudiantes/
│       │   ├── GestionEstudiantesApplication.java
│       │   ├── model/Estudiante.java
│       │   ├── repository/EstudianteRepository.java
│       │   ├── service/EstudianteService.java
│       │   └── controller/EstudianteController.java
│       └── resources/application.properties
├── frontend/                         # Cliente web
│   ├── index.html
│   ├── css/styles.css
│   └── js/app.js
└── README.md
```

## Requisitos previos

- JDK 17 o superior
- Apache Maven 3.8 o superior
- Navegador web moderno (Chrome, Firefox, Edge)

## Instrucciones de ejecucion

### 1. Iniciar el backend

Abrir una terminal en la carpeta `backend` y ejecutar:

```bash
mvn spring-boot:run
```

La API quedara disponible en:

```
http://localhost:8080/api/estudiantes
```

La consola de la base de datos H2 (opcional) se encuentra en:

```
http://localhost:8080/h2-console
```

Datos de conexion H2:
- JDBC URL: jdbc:h2:mem:estudiantesdb
- User Name: sa
- Password: (vacio)

### 2. Abrir el frontend

Abrir el archivo `frontend/index.html` directamente en el navegador, o servir la carpeta frontend con cualquier servidor estatico simple.

Ejemplo con Python:

```bash
cd frontend
python -m http.server 5500
```

Luego acceder a: http://localhost:5500

### 3. Uso de la aplicacion

1. Completar el formulario y presionar "Guardar" para crear un estudiante (POST).
2. El listado se actualiza automaticamente (GET).
3. Usar el boton "Editar" para cargar los datos y modificarlos (PUT).
4. Usar el boton "Eliminar" para borrar un registro (DELETE).
5. El boton "Actualizar listado" vuelve a consultar todos los registros.

## Endpoints de la API

| Metodo | URL                              | Descripcion                        |
|--------|----------------------------------|------------------------------------|
| GET    | /api/estudiantes                 | Lista todos los estudiantes        |
| GET    | /api/estudiantes/{id}            | Obtiene un estudiante por ID       |
| POST   | /api/estudiantes                 | Crea un nuevo estudiante           |
| PUT    | /api/estudiantes/{id}            | Actualiza un estudiante existente  |
| DELETE | /api/estudiantes/{id}            | Elimina un estudiante              |

## Modelo de datos (Estudiante)

| Campo     | Tipo    | Restricciones                  |
|-----------|---------|--------------------------------|
| id        | Long    | Autogenerado, clave primaria   |
| nombre    | String  | Obligatorio, max 80            |
| apellido  | String  | Obligatorio, max 80            |
| email     | String  | Obligatorio, unico, formato email |
| carrera   | String  | Obligatorio, max 100           |
| semestre  | Integer | Obligatorio, valor positivo    |
| telefono  | String  | Opcional, max 20               |
| activo    | Boolean | Por defecto true               |

## Componentes reutilizados

- **Modelo (Entity)**: representa la entidad de negocio.
- **Repositorio**: acceso a datos mediante Spring Data JPA.
- **Servicio**: encapsula la logica de negocio y transacciones.
- **Controlador**: expone los endpoints REST y maneja las respuestas HTTP.
- **Cliente web**: consume la API mediante la funcion fetch del navegador.

## Notas tecnicas

- Se utiliza base de datos H2 en memoria para facilitar la ejecucion sin instalaciones adicionales.
- La base de datos se reinicia cada vez que se detiene el backend.
- Se habilito CORS para permitir el consumo desde el frontend.
- Validaciones basicas de campos obligatorios y formato de correo.
- El proyecto aplica separacion de capas y reutilizacion de metodos.

## Autor

Proyecto desarrollado como parte de la actividad de implementacion de servicios RESTful.
