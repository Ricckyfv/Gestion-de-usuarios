const BASE_URL = window.location.hostname === 'localhost'
    ? 'http://localhost:8080'
    : 'https://gestion-de-usuarios-production-d7d9.up.railway.app';

async function loginUser() {

    const emailInput = document.getElementById('email').value;

    let datos = {
        email: emailInput,
        password: document.getElementById('password').value
    };

    const request = await fetch(`${BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
      });

if (request.ok) {
        const response = await request.json();

        localStorage.token = response.token;
        localStorage.role = response.role;
        localStorage.email = response.email ? response.email : emailInput;

        window.location.href = 'users.html';

        } else {
        alert("Email o contraseña incorrectos");
        }
}