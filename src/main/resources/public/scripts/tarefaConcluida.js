
var usuario = localStorage.getItem("usuario")

if(usuario){
    usuario = JSON.parse(usuario)
}


console.log(usuario)

document.addEventListener('DOMContentLoaded', function () {
    fetch(`/findTasks/notation/filter/${usuario.id_usuario}`)
        .then(response => response.text())
        .then(data => {
            console.log(data)
            var tarefas = document.getElementById("tarefasConcluidas")
            tarefas.innerHTML = data;
        })
        .catch(error => console.error('Erro ao carregar dados:', error));

    function formatarData(date) {
        let data = new Date(date);
        let dia = (data.getDate()).toString().padStart(2, '0'); // Adicionar zero à esquerda, se necessário
        let mes = (data.getMonth() + 1).toString().padStart(2, '0'); // Lembre-se que os meses em JavaScript são baseados em zero
        let ano = data.getFullYear();
        // Formatar a data no formato "DD/MM/AAAA"
        const dataFormatada = `${dia}/${mes}/${ano}`;
        return dataFormatada;
    }

    function formatarHora(time) {
        let hora = time.split('T')[1];
        return hora;
    }

    function exibirTarefasConcluidas(tarefas) {
        const containerTarefas = document.getElementById('tarefasConcluidas');

        let recorrencia;
        //Verifica recorrência
        if(tarefas.recurrent == true){
            recorrencia = 'Sim';
        }else{
            recorrencia = 'Não'
        }

        tarefas
            .filter(tarefa => tarefa.status === 'Concluida')
            .forEach(tarefa => {
                const dataFormatada = formatarData(tarefa.date);
                const time = formatarHora(tarefa.date)
                const divTarefa = document.createElement('div');
                divTarefa.classList.add('tarefaConclud');
                divTarefa.innerHTML = `
                    <h3 class="h3">${tarefa.name}</h3>
                    <p class="p">Data: ${dataFormatada}</p>
                    <p class="p">Recorrência: ${recorrencia}</p>
                    <p class="p">Horário: ${time}</p>
                    <p class="p">Status: ${tarefa.status}</p>
                `;
                const prioridadeCircle = document.createElement('div');
                prioridadeCircle.classList.add('prioridade-circle');
                prioridadeCircle.style.backgroundColor = getColorByPriority(tarefa.priority);

                divTarefa.addEventListener('click', () => openModal(tarefa));

                divTarefa.appendChild(prioridadeCircle);
                containerTarefas.appendChild(divTarefa);
            });
    }

    function getColorByPriority(priority) {
        switch (priority.toLowerCase()) {
            case '1':
                return 'red';
            case '2':
                return 'yellow';
            case '3':
                return 'green';
            default:
                return 'gray';
        }
    }

    function openModal(tarefa) {
        const modal = document.getElementById('modal-tarefaConcluida');
        const modalTitle = document.getElementById('modal-title-concluida');
        const modalDescription = document.getElementById('modal-description-concluida');
        const modalClose = document.getElementById('modal-close-concluida');

        modalTitle.textContent = tarefa.name;
        modalDescription.textContent = tarefa.comment;

        modal.style.display = 'block';

        modalClose.addEventListener('click', closeModal);

        function closeModal() {
            modal.style.display = 'none';
            modalClose.removeEventListener('click', closeModal);
        }

        window.onclick = function (event) {
            if (event.target === modal) {
                closeModal();
            }
        };
    }
});
