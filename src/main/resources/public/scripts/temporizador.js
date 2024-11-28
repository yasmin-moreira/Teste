let timerInterval;
let timeRemaining = 0; // Armazena o tempo restante em segundos
let isRunning = false;
let isPaused = false;

// Função que inicia o temporizador
function startTimer() {
    const hours = parseInt(document.getElementById('hoursInput').value, 10) || 0;
    const minutes = parseInt(document.getElementById('minutesInput').value, 10) || 0;

    timeRemaining = (hours * 3600);
    if (timeRemaining > 0) {
        isRunning = true;
        isPaused = false;
        updateDisplay();
        timerInterval = setInterval(countdown, 1000);
        document.getElementById('pauseTimer').disabled = false;
        document.getElementById('cancelTimer').disabled = false;
    }
}

// Função que atualiza o display do temporizador
function updateDisplay() {
    const hours = Math.floor(timeRemaining / 3600);
    const minutes = Math.floor((timeRemaining % 3600) / 60);
    const seconds = timeRemaining % 60;
    document.getElementById('timerDisplay').textContent = 
        `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
}

// Função que executa a contagem regressiva
function countdown() {
    if (timeRemaining > 0 && !isPaused) {
        timeRemaining--;
        updateDisplay();
    } else if (timeRemaining === 0) {
        clearInterval(timerInterval);
        isRunning = false;
        alert('O tempo acabou!');
    }
}

// Função para pausar ou retomar o temporizador
function pauseTimer() {
    if (isPaused) {
        isPaused = false;
        document.getElementById('pauseTimer').textContent = 'Pausar';
    } else {
        isPaused = true;
        document.getElementById('pauseTimer').textContent = 'Retomar';
    }
}

// Função para cancelar o temporizador
function cancelTimer() {
    clearInterval(timerInterval);
    timeRemaining = 0;
    isRunning = false;
    isPaused = false;
    updateDisplay();
    document.getElementById('pauseTimer').disabled = true;
    document.getElementById('cancelTimer').disabled = true;
}

// Evento para iniciar o temporizador quando o botão for clicado
document.getElementById('startTimer').addEventListener('click', function() {
    if (!isRunning) {
        startTimer();
    }
});

// Evento para pausar ou retomar o temporizador
document.getElementById('pauseTimer').addEventListener('click', pauseTimer);

// Evento para cancelar o temporizador
document.getElementById('cancelTimer').addEventListener('click', cancelTimer);

// Continua o temporizador mesmo se o modal for fechado
document.getElementById('timerModal').addEventListener('hidden.bs.modal', function () {
    if (timeRemaining > 0 && isRunning) {
        alert('Temporizador ainda está em andamento!');
    }
});

// Função para tornar o modal arrastável
function makeModalDraggable(modal) {
    const modalHeader = modal.querySelector('.modal-header');
    const modalDialog = modal.querySelector('.modal-dialog');

    let isDragging = false;
    let offsetX, offsetY;

    modalHeader.addEventListener('mousedown', function (e) {
        isDragging = true;
        // Calcula a posição inicial do clique
        offsetX = e.clientX - modalDialog.getBoundingClientRect().left;
        offsetY = e.clientY - modalDialog.getBoundingClientRect().top;
        document.addEventListener('mousemove', onMouseMove);
        document.addEventListener('mouseup', onMouseUp);
    });

    function onMouseMove(e) {
        if (isDragging) {
            // Define a nova posição do modal
            modalDialog.style.position = 'absolute';
            modalDialog.style.left = `${e.clientX - offsetX}px`;
            modalDialog.style.top = `${e.clientY - offsetY}px`;
            modalDialog.style.margin = '0'; // Remove o margin para melhor controle da posição
        }
    }

    function onMouseUp() {
        isDragging = false;
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
    }
}

// Chame a função para tornar o modal arrastável
const modalElement = document.getElementById('timerModal');
makeModalDraggable(modalElement);
