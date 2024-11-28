// Variável json server
// Página inicial de Login
document.getElementById("registerForm").addEventListener('submit', function(e){
    e.preventDefault();

    const formData ={
        username: document.querySelector("#username_cad").value,
        nome: document.querySelector("#nome_cad").value,
        email: document.querySelector("#email_cad").value,
        senha: document.querySelector("#senha_cad").value,
    }

    console.log(formData);

    fetch("/inserir", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.json())
    .then(data => {
        console.log("Sucesso:", data);
        window.location.href = '../pages/login.html';
    })
    .catch(error => {
        console.error("Erro:", error);
    });
});

document.getElementById("login-form").addEventListener('submit', function(e) {
    e.preventDefault();

    var username = document.querySelector("#username").value;
    var senha = document.querySelector("#password").value;

    fetch("/usuario/" + username + "/" + senha, {
        method: "GET",
    })
    .then(response => {
        if (response.status === 404) {
            throw new Error("Usuário não encontrado.");
        } else if (!response.ok) {
            throw new Error("Erro na requisição");
        }
        return response.json(); // Converte a resposta para JSON
    })
    .then(data => {
        // Salva os dados do usuário no localStorage
        localStorage.setItem("usuario", JSON.stringify(data));
        console.log("Dados do usuário salvos no localStorage:", data);
        window.location.href = '../index.html'; // Redireciona para a página inicial
    })
    .catch(error => {
        console.error("Erro ao buscar o usuário:", error);
        alert(error.message); // Alerta ao usuário sobre o erro
    });
});
