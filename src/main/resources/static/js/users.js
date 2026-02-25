
$(document).ready(function() {
    loadUsers();
    setupUI();
});

function setupUI() {

    document.getElementById('display-email').innerText = localStorage.email;
    const role = localStorage.role;
    document.getElementById('display-role').innerText = role;

    if (role === 'ADMIN') {
        document.getElementById('display-role').className = 'badge badge-danger';
        document.getElementById('table-title').innerText = "Panel de Administrador";
    } else {
        document.getElementById('display-role').className = 'badge badge-success';
        document.getElementById('table-title').innerText = "Mi Perfil";
        document.getElementById('table-desc').innerText = "Aquí puedes gestionar tus datos personales.";
    }
}

async function loadUsers() {
    const role = localStorage.role;

    const url = (role === 'ADMIN') ? 'api/users' : 'api/users/me';

    const request = await fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Bearer ' + localStorage.token
        }
    });

        if (request.status === 401 || request.status === 403) {
            alert("Tu sesión ha expirado o no tienes permisos.");
            logout();
            return;
        }

    let users = await request.json();

    if (!Array.isArray(users)) {
        users = [users];
    }

    let listadoHtml = '';
    for (let user of users) {
//       if (localStorage.role !== 'ADMIN') {
//            localStorage.currentUserId = user.id;
//           }

        let btnDelete = `<a href="#" onclick="deleteUser(${user.id})" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>`;
        let btnEdit = `<a href="#" onclick="editUser(${user.id})" class="btn btn-info btn-circle btn-sm ml-2"><i class="fas fa-edit"></i></a>`;

    let userHtml = `<tr id="row-${user.id}">
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.phoneNumber ? user.phoneNumber : '-'}</td>
        <td>${btnDelete}${btnEdit}</td>
    </tr>`;
    listadoHtml += userHtml;
    }

    document.querySelector('#usersTable tbody').innerHTML = listadoHtml;
    $('#usersTable').DataTable();
}

function logout() {
    localStorage.clear();
    window.location.replace('login.html');
}

async function deleteUser(id) {

  const token = localStorage.token;

      if(!confirm('Do you want to delete this user?')) {
        return;
      }

    try {
          const request = await fetch('api/users/' + id, {
            method: 'DELETE',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + token
            },
          })

          if (request.ok) {
            if (id.toString() === localStorage.currentUserId) {
                alert("Your account has been deleted. You will be redirected to the beginning.");
                localStorage.clear();
                window.location.href = 'index.html';
                return;
            }

            const row = document.getElementById(`row-${id}`);
            if (row) {
                $('#usersTable').DataTable().row($(row)).remove().draw();
            }

            console.log(`Usuario ${id} eliminado con éxito.`);
              } else if (request.status === 401 || request.status === 403) {
                  alert("Your session has expired. Please log in again");
                  logout();
              } else {
                  alert("User can´t delete. Server Error.");
              }
     }
    catch (error)  {
    console.error("Error en la conexión:", error);
    alert("Hubo un error de conexión con el servidor.");

   }

  }

  // 1. Esta función se dispara al pulsar el botón azul de la tabla
  async function editUser(id) {
      // Abrimos el modal primero
      $('#editModal').modal('show');

      // Buscamos los datos actuales del usuario para que el admin/user vea qué va a cambiar
      const request = await fetch('api/users/' + id, {
          method: 'GET',
          headers: {
              'Accept': 'application/json',
              'Authorization': 'Bearer ' + localStorage.token
          }
      });
      const user = await request.json();

      // Rellenamos los campos del modal con los datos que nos dio el servidor
      document.getElementById('editId').value = user.id;
      document.getElementById('editName').value = user.name;
      document.getElementById('editEmail').value = user.email;
      document.getElementById('editPhone').value = user.phoneNumber ? user.phoneNumber : '';
  }

  // 2. Esta función se dispara al pulsar "Guardar Cambios" dentro del modal
  async function saveUserEdit() {
      let id = document.getElementById('editId').value;
      let datos = {
          id: id,
          name: document.getElementById('editName').value,
          email: document.getElementById('editEmail').value,
          phoneNumber: document.getElementById('editPhone').value
      };

      const request = await fetch('api/users/update/' + id, {
          method: 'PUT',
          headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.token
          },
          body: JSON.stringify(datos)
      });

      if (request.ok) {
          $('#editModal').modal('hide'); // Cerramos el modal

          // Actualizamos la fila de la tabla visualmente sin refrescar
          const row = document.getElementById(`row-${id}`);
          if (row) {
              row.cells[1].innerText = datos.name;
              row.cells[2].innerText = datos.email;
              row.cells[3].innerText = datos.phoneNumber;
          }

          alert("Usuario actualizado correctamente");
      } else {
          alert("Error al actualizar el usuario. Verifica tus permisos.");
      }
  }


