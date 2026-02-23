
async function loginUser() {

    const emailInput = document.getElementById('email').value;

    let datos = {
        email: emailInput,
        password: document.getElementById('password').value
    };

    const request = await fetch('api/auth/login', {
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