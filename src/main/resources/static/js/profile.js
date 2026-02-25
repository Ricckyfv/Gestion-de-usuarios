document.addEventListener('DOMContentLoaded', function() {
    loadProfile();
});

async function loadProfile() {
    const token = localStorage.token;
    if (!token) {
        window.location.href = 'login.html';
        return;
    }

    // Mostrar botón de Admin si el rol es ADMIN
    if (localStorage.role === 'ADMIN') {
        document.getElementById('btnAdmin').style.display = 'inline-block';
    }

    // Petición a un nuevo endpoint que crearemos en Java: /api/users/me
    const request = await fetch('api/users/me', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Accept': 'application/json'
        }
    });

    if (request.ok) {
        const user = await request.json();

        // Rellenar los campos
        document.getElementById('txtNameDisplay').innerText = user.name;
        document.getElementById('txtName').value = user.name;
        document.getElementById('txtEmail').value = user.email;
        document.getElementById('txtPhone').value = user.phoneNumber || 'No indicado';
        document.getElementById('badgeRole').innerText = user.role;
    } else {
        alert("Error al cargar el perfil");
        logout();
    }
}

function logout() {
// 1. Borramos el token y datos del usuario
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');

    // 2. Redirigimos eliminando la página actual del historial
    window.location.replace('login.html');
}

// Función para habilitar la edición
function enableEditing() {
    document.getElementById('txtName').readOnly = false;
    document.getElementById('txtEmail').readOnly = false;
    document.getElementById('txtPhone').readOnly = false;

    document.getElementById('btnEdit').style.display = 'none';
    document.getElementById('btnSave').style.display = 'block';

    document.getElementById('txtName').focus();
}

// Función para enviar los cambios al servidor
async function saveProfile() {
    let datos = {
        name: document.getElementById('txtName').value,
        email: document.getElementById('txtEmail').value,
        phoneNumber: document.getElementById('txtPhone').value
    };

    const request = await fetch('api/users/update', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.token
        },
        body: JSON.stringify(datos)
    });

    if (request.ok) {
        alert("¡Perfil actualizado con éxito!");
        // Volvemos al estado de solo lectura
        location.reload();
    } else {
        alert("Hubo un error al actualizar los datos.");
    }
}