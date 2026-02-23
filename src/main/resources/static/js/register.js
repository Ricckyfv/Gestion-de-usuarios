async function register() {
    let datos = {
        name: document.getElementById("txtName").value,
        email: document.getElementById("txtEmail").value,
        password: document.getElementById("txtPassword").value,
        phoneNumber: document.getElementById("txtPhoneNumber").value
    };


    let repeatPassword = document.getElementById("txtRepeatPassword").value;

    if (datos["password"] !== repeatPassword) {
        alert("Passwords do not match!");
        return;
    }

//    const request = await fetch('api/auth/register'
    const request = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
      });

       if (request.status === 409) {
        alert("Email already exists!");
        return;
       }

      if(!request.ok){
        alert("Error creating user!");
        return;
      }

      alert("User created successfully!");
      window.location.href = "login.html";
}
