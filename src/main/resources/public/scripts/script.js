import {id} from './app.js'
let year = new Date().getFullYear();
let month = new Date().getMonth();

var usuario = localStorage.getItem("usuario");

if(usuario){
  usuario = JSON.parse(usuario);
}

addEventListener('DOMContentLoaded', async function(e){
  await generateCalendar();
})

/*document.querySelector('.add-task-button').addEventListener('click', () => {
  const today = new Date();
  const currentYear = today.getFullYear();
  const currentMonth = today.getMonth() + 1;
  const formattedMonth = currentMonth < 10 ? `0${currentMonth}` : `${currentMonth}`;
  const formattedDay = today.getDate() < 10 ? `0${today.getDate()}` : `${today.getDate()}`;
  const todayDate = `${currentYear}-${formattedMonth}-${formattedDay}`;

  document.getElementById('taskStartDate').value = todayDate;
  document.getElementById('taskEndDate').value = todayDate;
  document.querySelector('.task-modal').style.display = 'block';
});*/

document.querySelector('.close').addEventListener('click', () => {
  document.querySelector('.task-modal').style.display = 'none';
});

document.querySelector('.close-details').addEventListener('click', () => {
  document.querySelector('.task-details-modal').style.display = 'none';
});

document.getElementById('prev').addEventListener('click', () => {
  month--;
  if (month < 0) {
    month = 11;
    year--;
  }
  generateCalendar();
});

document.getElementById('next').addEventListener('click', () => {
  month++;
  if (month > 11) {
    month = 0;
    year++;
  }
  generateCalendar();
});

async function loadTasksFromJSON() {
  try {
    const response = await fetch(`/findTasks/notation/list/` + usuario.id_usuario);
    const data = await response.json();
    const openTasks = data.filter(task => task.status === 'aberto');
    return openTasks;
  } catch (error) {
    console.error('Erro ao carregar tarefas:', error);
    return [];
  }
}

 async function generateCalendar() {
  const tasks = await loadTasksFromJSON();

  const weekdaysElement = document.querySelector('.weekdays');
  weekdaysElement.innerHTML = '';

  const weekdaysAbbreviations = ['DOM', 'SEG', 'TER', 'QUA', 'QUI', 'SEX', 'SAB'];

  for (let i = 0; i < weekdaysAbbreviations.length; i++) {
    const weekdayElement = document.createElement('div');
    weekdayElement.classList.add('weekday');
    weekdayElement.textContent = weekdaysAbbreviations[i];
    weekdaysElement.appendChild(weekdayElement);
  }

  const daysElement = document.querySelector('.days');
  daysElement.innerHTML = '';

  const today = new Date();
  const currentYear = today.getFullYear();
  const currentMonth = today.getMonth();
  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);

  document.getElementById('month').textContent = `${firstDay.toLocaleString('pt-BR', { month: 'long' })} ${year}`;

  for (let day = 1; day <= lastDay.getDate(); day++) {
    const date = new Date(year, month, day);
    const dayElement = document.createElement('div');
    dayElement.classList.add('day');
    dayElement.textContent = day;

    const tasksForDate = tasks.filter(task => task.date.split('T')[0] === date.toISOString().split('T')[0]);
    tasksForDate.forEach(task => {
      const taskElement = document.createElement('div');
      taskElement.classList.add('task');
      taskElement.style.backgroundColor = getPriorityColor(task.priority);
      dayElement.appendChild(taskElement);
      taskElement.addEventListener('click', () => {
        showTaskDetails(task);
      });
    });

    daysElement.appendChild(dayElement);
  }
}

function getTasksForDate(date) {
  const tasks = JSON.parse(localStorage.getItem('tasks')) || [];
  return tasks.filter(task => task.date === date);
}

function getPriorityColor(priority) {
  switch (priority) {
    case '1':
      return '#E0473F';
    case '2':
      return '#F2A516';
    case '3':
    default:
      return '#83f04b';
  }
}

/*function addTask() {
  const taskName = document.getElementById('taskName').value;
  const priority = document.getElementById('priority').value || 'Baixo';
  const taskStartDate = document.getElementById('taskStartDate').value;
  const taskEndDate = document.getElementById('taskEndDate').value;

  if (taskName && taskStartDate && taskEndDate) {
    const task = {
      nome: taskName,
      Prioridade: priority,
      Data: taskStartDate,
      Status: 'Aberta',
    };
    let tasks = JSON.parse(localStorage.getItem('tasks')) || [];
    tasks.push(task);
    localStorage.setItem('tasks', JSON.stringify(tasks));
    document.getElementById('taskName').value = '';
    document.querySelector('.task-modal').style.display = 'none';
    generateCalendar();
  } else {
    alert('Por favor, preencha todos os campos.');
  }
}*/

function showTaskDetails(task) {
  const taskDetailsTitle = document.getElementById('taskDetailsTitle');
  const taskDetailsStartDate = document.getElementById('taskDetailsStartDate');
  const taskDetailsEndDate = document.getElementById('taskDetailsEndDate');

  taskDetailsTitle.textContent = task.nome;
  taskDetailsStartDate.textContent = task.date;
  taskDetailsEndDate.textContent = task.date; 

  document.querySelector('.task-details-modal').style.display = 'block';
}
