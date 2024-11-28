user = document.getElementById("content-my-profile");

const nome = usuarioCorrente.nome;
const login = usuarioCorrente.login;
const email = usuarioCorrente.email;

document.addEventListener('DOMContentLoaded',function(){
    
    user.innerHTML = `
    <div class= "itens-perfil">
        <span id="nome-perfil" class="item-perfil">${nome}</span>
        <span id="login-perfil" class="item-perfil">${login}</span>
        <span id="email-perfil" class="item-perfil">${email}</span>
    </div>
    `

})