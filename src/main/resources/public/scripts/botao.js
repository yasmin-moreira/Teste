document.addEventListener("DOMContentLoaded", function ScrollCards () {
    const scrollContainer = document.getElementById('cards-tarefas')
    const scrollContent = document.querySelector(".card-tarefa");
    const scrollStep = 180; // Quantidade de pixels a serem rolados

    document.getElementById("forward").addEventListener("click", function ScrollCards () {
        scrollContainer.scrollLeft += scrollStep;
    });

    document.getElementById("backward").addEventListener("click", function ScrollCards () {
        scrollContainer.scrollLeft -= scrollStep;
    });
});


const usuario = JSON.parse(localStorage.getItem("usuario"));
if(usuario){
    console.log(usuario.id_usuario);
}

document.getElementById("addButton").addEventListener("click", function () {
    console.log("clicou")
    // Obter os valores dos campos do formulário
    const name = document.querySelector("input[name='name']").value;
    const date = document.querySelector("input[name='date']").value;
    const prioritySelect = document.querySelector("select[name='priority']").value;
    const comment = document.querySelector("textarea[name='comment']").value;
    const isRecurrent = document.querySelector("input[name='recurrent']").checked;
    
    // Formatar a data para "dd/mm/YYYY"
    const formattedDate = new Date(date);
    const day = String(formattedDate.getDate()).padStart(2, '0');
    const month = String(formattedDate.getMonth() + 1).padStart(2, '0'); // Mês começa do zero
    const year = formattedDate.getFullYear();
    const formattedDateStr = `${day}/${month}/${year}`;

    // Converter a prioridade
    const priority = parseInt(prioritySelect);

    // Array de recorrência
    let recurrenceArray = [false, false, false, false, false, false, false]; // Array de booleanos (7 dias da semana)

    if (isRecurrent) {
        document.querySelectorAll(".days-checkbox").forEach((checkbox, index) => {
            if (checkbox.checked) {
                recurrenceArray[index] = true; // 1 para dia selecionado
            }
        });
    }

    // Criar objeto com todos os dados do formulário
    const formData = {
        nomeTarefa: name,
        dataprazo: formattedDateStr,
        prioridade: priority,
        descricao: comment,
        status: -1,
        recorrencia: recurrenceArray,
        usuarioIdUsuario1: usuario.id_usuario
    };

    console.log(formData);

    fetch("/tarefa/inserir", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData) // formData é o objeto que criamos com os dados do formulário
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erro na requisição: " + response.statusText);
        }
        return response.json(); // ou response.text() dependendo do que o backend retorna
    })
    .then(data => {
        console.log("Tarefa inserida com sucesso:", data);
    })
    .catch(error => {
        console.error("Erro ao inserir tarefa:", error);
    });
    

});

function deletarTarefa(botao){
    const usuario = localStorage.getItem("usuario");
    var user;
    if(usuario){
        user = JSON.parse(usuario);
    }

    var nome = botao.getAttribute("data-nome")
    console.log(nome)

    fetch("/tarefa/delete/" + nome + "/" + user.id_usuario, {
        method: "DELETE"
    })

    .then(response => {
        if(!response.ok){
            console.log(response)
            console.log("nao foi deletado")
        }

        return response.json();
    })

    .then(data => {
        alert("Tarefa deletada com sucesso " + data);
        location.reload();
    })  
}   


/*document.addEventListener("DOMContentLoaded", function () {
    const scrollContainer = document.getElementById('cards-tarefas')
    const scrollContent = document.querySelector(".card-tarefa");
    const scrollStep = 1; // Você pode ajustar isso conforme necessário

    document.getElementById("forward").addEventListener("click", function () {
        const visibleElements = document.querySelectorAll(".card-tarefa");
        const firstVisibleElement = Array.from(visibleElements).find((element) =>
            element.getBoundingClientRect().right >= 0
        );

        if (firstVisibleElement) {
            firstVisibleElement.scrollIntoView({ behavior: "smooth" });
        }
    });

    document.getElementById("backward").addEventListener("click", function () {
        const visibleElements = document.querySelectorAll(".card-tarefa");
        const lastVisibleElement = Array.from(visibleElements)
            .reverse()
            .find((element) => element.getBoundingClientRect().left <= scrollContainer.clientWidth);

        if (lastVisibleElement) {
            lastVisibleElement.scrollIntoView({ behavior: "smooth" });
        }
    });
});*/

