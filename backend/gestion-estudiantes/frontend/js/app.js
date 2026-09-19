const API_BASE = 'http://localhost:8080/api/estudiantes';

const form = document.getElementById('estudiante-form');
const formTitle = document.getElementById('form-title');
const btnGuardar = document.getElementById('btn-guardar');
const btnCancelar = document.getElementById('btn-cancelar');
const btnRefrescar = document.getElementById('btn-refrescar');
const tablaBody = document.getElementById('tabla-body');
const mensajeDiv = document.getElementById('mensaje');

let modoEdicion = false;

document.addEventListener('DOMContentLoaded', () => {
    cargarEstudiantes();
    form.addEventListener('submit', manejarSubmit);
    btnCancelar.addEventListener('click', cancelarEdicion);
    btnRefrescar.addEventListener('click', cargarEstudiantes);
});

function mostrarMensaje(texto, tipo) {
    mensajeDiv.textContent = texto;
    mensajeDiv.className = 'mensaje ' + tipo;
    mensajeDiv.classList.remove('hidden');
    setTimeout(() => {
        mensajeDiv.classList.add('hidden');
    }, 4000);
}

function limpiarFormulario() {
    form.reset();
    document.getElementById('estudiante-id').value = '';
    modoEdicion = false;
    formTitle.textContent = 'Registrar nuevo estudiante';
    btnGuardar.textContent = 'Guardar';
    btnCancelar.classList.add('hidden');
}

function cancelarEdicion() {
    limpiarFormulario();
}

async function cargarEstudiantes() {
    try {
        const respuesta = await fetch(API_BASE);
        if (!respuesta.ok) {
            throw new Error('Error al obtener el listado de estudiantes');
        }
        const estudiantes = await respuesta.json();
        renderizarTabla(estudiantes);
    } catch (error) {
        mostrarMensaje('No se pudo conectar con el servidor. Verifique que el backend este en ejecucion.', 'error');
        tablaBody.innerHTML = '<tr><td colspan="9" style="text-align:center;">Sin datos disponibles</td></tr>';
    }
}

function renderizarTabla(estudiantes) {
    tablaBody.innerHTML = '';

    if (!estudiantes || estudiantes.length === 0) {
        tablaBody.innerHTML = '<tr><td colspan="9" style="text-align:center;">No hay estudiantes registrados</td></tr>';
        return;
    }

    estudiantes.forEach(est => {
        const fila = document.createElement('tr');
        const estadoClase = est.activo ? 'estado-activo' : 'estado-inactivo';
        const estadoTexto = est.activo ? 'Activo' : 'Inactivo';

        fila.innerHTML = `
            <td>${est.id}</td>
            <td>${est.nombre}</td>
            <td>${est.apellido}</td>
            <td>${est.email}</td>
            <td>${est.carrera}</td>
            <td>${est.semestre}</td>
            <td>${est.telefono || '-'}</td>
            <td class="${estadoClase}">${estadoTexto}</td>
            <td class="acciones">
                <button class="btn btn-edit" onclick="editarEstudiante(${est.id})">Editar</button>
                <button class="btn btn-delete" onclick="eliminarEstudiante(${est.id})">Eliminar</button>
            </td>
        `;
        tablaBody.appendChild(fila);
    });
}

async function manejarSubmit(evento) {
    evento.preventDefault();

    const id = document.getElementById('estudiante-id').value;
    const datos = {
        nombre: document.getElementById('nombre').value.trim(),
        apellido: document.getElementById('apellido').value.trim(),
        email: document.getElementById('email').value.trim(),
        carrera: document.getElementById('carrera').value.trim(),
        semestre: parseInt(document.getElementById('semestre').value, 10),
        telefono: document.getElementById('telefono').value.trim() || null,
        activo: true
    };

    try {
        let respuesta;
        if (modoEdicion && id) {
            respuesta = await fetch(`${API_BASE}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datos)
            });
        } else {
            respuesta = await fetch(API_BASE, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datos)
            });
        }

        const resultado = await respuesta.json();

        if (!respuesta.ok) {
            const mensajeError = resultado.mensaje || 'Error en la operacion';
            mostrarMensaje(mensajeError, 'error');
            return;
        }

        const mensajeExito = modoEdicion
            ? 'Estudiante actualizado correctamente'
            : 'Estudiante registrado correctamente';
        mostrarMensaje(mensajeExito, 'exito');
        limpiarFormulario();
        cargarEstudiantes();
    } catch (error) {
        mostrarMensaje('Error de comunicacion con el servidor', 'error');
    }
}

async function editarEstudiante(id) {
    try {
        const respuesta = await fetch(`${API_BASE}/${id}`);
        if (!respuesta.ok) {
            throw new Error('No se pudo obtener el estudiante');
        }
        const estudiante = await respuesta.json();

        document.getElementById('estudiante-id').value = estudiante.id;
        document.getElementById('nombre').value = estudiante.nombre;
        document.getElementById('apellido').value = estudiante.apellido;
        document.getElementById('email').value = estudiante.email;
        document.getElementById('carrera').value = estudiante.carrera;
        document.getElementById('semestre').value = estudiante.semestre;
        document.getElementById('telefono').value = estudiante.telefono || '';

        modoEdicion = true;
        formTitle.textContent = 'Editar estudiante';
        btnGuardar.textContent = 'Actualizar';
        btnCancelar.classList.remove('hidden');

        window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (error) {
        mostrarMensaje('Error al cargar los datos del estudiante', 'error');
    }
}

async function eliminarEstudiante(id) {
    const confirmacion = confirm('¿Esta seguro de eliminar este estudiante? Esta accion no se puede deshacer.');
    if (!confirmacion) {
        return;
    }

    try {
        const respuesta = await fetch(`${API_BASE}/${id}`, {
            method: 'DELETE'
        });

        const resultado = await respuesta.json();

        if (!respuesta.ok) {
            mostrarMensaje(resultado.mensaje || 'Error al eliminar', 'error');
            return;
        }

        mostrarMensaje('Estudiante eliminado correctamente', 'exito');
        cargarEstudiantes();
    } catch (error) {
        mostrarMensaje('Error de comunicacion con el servidor', 'error');
    }
}
