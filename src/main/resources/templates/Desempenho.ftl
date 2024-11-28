<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gráfico - Tarefas Concluídas</title>
    <link rel="stylesheet" href="./assets/Styles/style.css">
    <link rel="stylesheet" href="./assets/Styles/style_des.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inclusive+Sans&family=Poppins&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
    crossorigin="anonymous"></script>

    <!-- Versão do jQuery compatível com Bootstrap 5 -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
    integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="assets/Scripts/login.js"></script>
    <script>
        // Verifica se o usuário já esta logado e se negativo, redireciona para tela de login        
        if (!usuarioCorrente.login) {
            window.location.href = LOGIN_URL;
            console.log('não armazwenou')
        }

        function initPage() {
            document.getElementById('btn_logout').addEventListener('click', logoutUser);
        }

        function returnHomePage() {
            window.location.href = 'index.html?id=' + usuarioCorrente.id;
        }

        function IdUser(page) {
            window.location.href = `${page}?id=` + usuarioCorrente.id;
        }
        // Associa ao evento de carga da página a função para verificar se o usuário está logado
        window.addEventListener('load', initPage);
    </script>
</head>

<body>
    <header class="header">
        <!-- <a onclick="returnHomePage()"><img src="assets/imgs/CLOCK - PNG.png" id="logo"></a>
        <ul>
            <li><a href="./metodosEstudo.html">Metodos de Estudos</a></li>
            <li><a href="#">Minha Conta</a></li>
            <li><a href="#" id="btn_logout">Sair</a></li>
        </ul> -->
        <nav class="nav">
            <div class="logo">
                <a href="#" onclick="returnHomePage()">
                    <img src="assets//imgs/CLOCK - PNG.png" class="logo-icon" />
                </a>
            </div>
            <input type="checkbox" id="menu-toggle">
            <label for="menu-toggle" class="menu-icon">&#9776;</label>
            <ul class="menu">
                <li><a href="./metodosEstudo.html">Metodos de Estudos</a></li>
                <li><a href="#" onclick="IdUser('Desempenho.html')">Desempenho</a></li>
                <li><a href="#" class="btn btn-primary" id="btn-timer-header" data-bs-toggle="modal" data-bs-target="#timerModal">Temporizador</a></li>
                <li class="li-dropdown"><span class="arrow">></span> Minha conta
                    <div class="dropdown-overlay">
                        <div class="dropdown-container">
                            <div class="content-all-my-profile" id="content-my-profile"></div>
                            <a href="#" id="btn_logout">Sair</a>
                        </div>
                    </div>
                </li>
                
            </ul>
        </nav>
    </header>

 <!-- Modal Temporizador -->
 <div class="modal fade" id="timerModal" tabindex="-1" aria-labelledby="timerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" id="header-time">
                <h5 class="modal-title" id="timerModalLabel">Temporizador</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label for="hoursInput" class="form-label">Horas</label>
                        <input type="number" class="form-control" id="hoursInput" min="0" value="0">
                    </div>
                    <div class="mb-3">
                        <label for="minutesInput" class="form-label">Pausa (minutos)</label>
                        <input type="number" class="form-control" id="minutesInput" min="0" value="0">
                    </div>
                </form>
                <div id="timerDisplay">00:00:00</div>
            </div>
            <div class="modal-footer">
                <div class="div-pause-or-cancel">
                    <button type="button" class="btn btn-warning" id="pauseTimer" disabled>Pausar</button>
                    <button type="button" class="btn btn-danger" id="cancelTimer" disabled>Cancelar</button>
                </div>
                <button type="button" class="btn btn-secondary btn-default" data-bs-dismiss="modal">Fechar</button>
                <button type="button" class="btn btn-primary btn-success" id="startTimer">Iniciar</button>
                
            </div>
        </div>
    </div>
</div>

  <div class="central-container">
   <div class="container_graph">
        <h1>Visão Geral de Desempenho</h1>
        <div class="piechart">
            <canvas id="pieChart"></canvas>
        </div>
   </div>
  </div>
</body>
<script src="./assets/Scripts/desempenho.js" type="module"></script>
<script src="./assets/Scripts/minhaConta.js"></script>
<script src="./assets/Scripts/temporizador.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</html>