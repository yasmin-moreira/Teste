var usuario = localStorage.getItem("usuario");

if (usuario) {
    usuario = JSON.parse(usuario);
}

console.log(usuario);

const apiUrl = `/desempenho/` + usuario.id_usuario;

// Tornar a função assíncrona
async function getJson() {
    try {
        const response = await fetch(apiUrl, {
            method: "GET"
        });
        const data = await response.json();
        console.log(data);
        return data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return []; // Retorna uma lista vazia em caso de erro
    }
}

document.addEventListener('DOMContentLoaded', async function () {
    try {
        // Esperar que os dados sejam carregados
        const jsonData = await getJson();

        if (!jsonData || jsonData.length === 0) {
            console.warn('Nenhum dado encontrado.');
            return;
        }
        

        // Filtrar tarefas concluídas
        const tarefasConcluidas = jsonData.filter(function (tarefa) {
            return tarefa.status == 2;
        });

        // Filtrar tarefas em aberto
        const tarefasAbertas = jsonData.filter(function (tarefa) {
            return tarefa.status == -1;
        });

        // Obter porcentagens
        const percentConcluidas = (tarefasConcluidas.length / jsonData.length) * 100;
        const percentAbertas = (tarefasAbertas.length / jsonData.length) * 100;

        // Criar gráfico de pizza
        const ctx = document.getElementById('pieChart').getContext('2d');
        const pieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: ['Concluídas', 'Em Aberto'],
                datasets: [{
                    data: [percentConcluidas, percentAbertas],
                    backgroundColor: ['#c5e898', '#3288c8']
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Porcentagem de Tarefas Concluídas e em Aberto',
                    fontColor: 'White'
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'bottom',
                        labels: {
                            font: {
                                size: 15,
                                weight: 'bold',
                            },
                            color: 'White',
                            padding: 40,
                        }
                    }
                }
            }
        });

    } catch (error) {
        console.error('Error in DOMContentLoaded event:', error);
    }
});
