import RotasTarefa from "./RotasTarefas.js"
import { ListarTarefas } from "./app.js"

export const base_url = `https://c66378d6-23b9-4a44-87e2-44bfce35e6b4-00-2axwcp1najbzx.worf.replit.dev`

export const addForm = document.querySelector('#addForm') //Formulário de criar/editar tarefas
export const tableBody = document.querySelector('#Tarefas_vertical') //Corpo da tabela de tarefas
export const addBtn = document.querySelector('#addButton') //Botao de criar/editar tarefa
export const addModalBtn = document.querySelector('.add-modal-btn') //Botao de abrir modal para criar tarefas
export const recurrentCheckbox = document.querySelector('input[name="recurrent"]') //Botão que define se é recorrente
export const prioritySelect = document.querySelector('select[name="priority"]') //Botão que define se é recorrente
export const addModal = document.querySelector('#addModal')

export const dateInput = document.querySelector('input[type="datetime-local"]')

export const checkboxDays = document.querySelectorAll('.days-checkbox')

export const dataAtual = new Date()

export const showDaysWeek = () => {
    if (recurrentCheckbox.checked == true) {
        document.querySelector('#week-days-select-container').classList.add('show')
    } else document.querySelector('#week-days-select-container').classList.remove('show')
}

export const refreshData = async () => {

    const rotasTarefas = await RotasTarefa.get()

    tableBody.innerHTML = ''

    // Lista as tarefas
    if (rotasTarefas.data.length == 0) {
        tableBody.innerHTML = `
        <div class="tarefas-item border-bottom row">
            <div class="col-12">
                Nenhuma tarefa por enquanto, que tal <a class="" href="javascript:void(0)" data-bs-toggle="modal" data-bs-target="#addModal">criar uma</a>?
            </div>
        </div>
        `
    } else {
        
        ListarTarefas();
        /*rotasTarefas.data.forEach(el => {
            let date = new Date(el.date)
            tableBody.innerHTML += `
            <div class="tarefas-item border-bottom row">
                <div class="col-8 d-flex flex-column textzinho">
                    <div class="text-truncate">${el.name}</div>
                    <div>${date.getDate().toLocaleString(undefined, { minimumIntegerDigits: 2 })}/${(date.getMonth() + 1).toLocaleString(undefined, { minimumIntegerDigits: 2 })}/${date.getFullYear().toLocaleString(undefined, { minimumIntegerDigits: 4, useGrouping: false })} - ${date.getHours().toLocaleString(undefined, { minimumIntegerDigits: 2 })}:${date.getMinutes().toLocaleString(undefined, { minimumIntegerDigits: 2 })}</div>
                </div>
                <div class="col-4 d-flex align-items-center">
                    <div class="btn btnActions btn-sm edit-btn" >
                        <img class="iconeSvg" id="editzinho" data-type-btn="exclude" data-id-tarefa="${el.id}" data-bs-toggle="modal" data-bs-target="#addModal" src="../assets/edit-fotinha.svg" >
                    </div>
                    <div class="btn btnActions btn-sm exclude-btn" >
                        <img class="iconeSvg" data-type-btn="edit" data-id-tarefa="${el.id}" src="../assets/trash-fotinha.svg">
                    </div>
                </div>
            </div>
            `
        });*/
    }

    // Cria evento para botao de excluir tarefa
    document.querySelectorAll('.exclude-btn').forEach(el => {
        el.addEventListener('click', async (ev) => {
            await RotasTarefa.delete(ev.target.dataset.idTarefa)
            refreshData()
        })
    })

    // Cria evento para abrir modal de editar tarefa
    document.querySelectorAll('.edit-btn').forEach(el => {
        el.addEventListener('click', async (ev) => {
            console.log("printou");
            const data = (await RotasTarefa.getById(ev.target.dataset.idTarefa)).data //axios precisa de .data

            console.log(data);

            addBtn.dataset.action = "edit"
            addBtn.textContent = "Editar"
            addBtn.dataset.idTarefa = data.id
            addBtn.classList.remove('btn-success')
            addBtn.classList.add('btn-warning')
            addForm.name.value = data.name
            addForm.date.value = data.date
            addForm.comment.value = data.comment
            addForm.weekDays.dataset.value = data.daysWeek != null ? data.daysWeek : "[]"
            document.querySelectorAll('.priorityOption').forEach(el => {
                if (el.value == data.priority) {
                    el.selected = true
                } else {
                    el.selected = false
                }
            })
            document.querySelectorAll('.weekDayLine').forEach(el => {
                el.classList.remove('bg-dark')
                el.classList.remove('text-white')
                el.children[0].checked = false
            })
            addForm.recurrent.checked = false
            if (data.recurrent == "true") {
                addForm.recurrent.checked = true
                showDaysWeek()

                document.querySelectorAll('.weekDayLine').forEach(el => {
                    if(data.daysWeek != null) {
                        JSON.parse(data.daysWeek).forEach(ele => {
                            if (el.children[0].dataset.value == ele) {
                                el.classList.add('bg-dark')
                                el.classList.add('text-white')
                                el.children[0].checked = true
                            }
                        })
                    }
                })
            } else addForm.recurrent.checked = false

            showDaysWeek()
        })
    })

}

// Abre o modal para criar tarefa
addModalBtn.addEventListener('click', () => {
    addBtn.dataset.action = "add"
    addBtn.textContent = "Adicionar"
    addBtn.idTarefa = undefined
    addBtn.classList.remove('btn-warning')
    addBtn.classList.add('btn-success')
    addForm.name.value = ''
    addForm.date.value = ''
    addForm.comment.value = ''
    recurrentCheckbox.checked = false
    addForm.weekDays.dataset.value = "[]"
    document.querySelectorAll('.priorityOption').forEach(el => {
        el.selected = false
    })
    document.querySelectorAll('.priorityOption')[0].selected = true

    document.querySelectorAll('.weekDayLine').forEach(el => {
        el.classList.remove('bg-dark')
        el.classList.remove('text-white')
    })

    showDaysWeek()
})

showDaysWeek()

recurrentCheckbox.addEventListener('change', (ev) => {
    showDaysWeek()
})

checkboxDays.forEach(el => {
    el.addEventListener('change', (ev) => {
        const values = JSON.parse(addForm.weekDays.dataset.value)
        ev.target.parentNode.classList.toggle('bg-dark')
        ev.target.parentNode.classList.toggle('text-white')
        if (ev.target.checked) {
            values.push(ev.target.dataset.value)
            values.sort()
            addForm.weekDays.dataset.value = JSON.stringify(values)
        } else {
            const indexOfItem = values.indexOf(ev.target.dataset.value)
            if(indexOfItem != -1) {
                values.splice(indexOfItem, 1)
                addForm.weekDays.dataset.value = JSON.stringify(values)
            }
        }
        console.log(values);
    })
})

console.log(addForm.name);

[addForm.date, addForm.name, addForm.priority].forEach((e) => {
    e.addEventListener("focus", () => {
        e.classList.remove("wrong")
    })
})

export const editarCriarTarefa = async (ev) => {

    if (addForm.name.value != '' && addForm.date.value != '' && addForm.date.value != undefined && addForm.priority.value != 0 ) {
        if (ev.target.dataset.action == "edit") {
            await RotasTarefa.change(ev.target.dataset.idTarefa, {
                name: addForm.name.value,
                date: addForm.date.value,
                comment: addForm.comment.value,
                recurrent: addForm.recurrent.checked ? (JSON.parse(addForm.weekDays.dataset.value).length > 0 ? "true" : "false") : "false",
                priority: addForm.priority.value,
                daysWeek : addForm.recurrent.checked ? JSON.parse(addForm.weekDays.dataset.value).length > 0 ? addForm.weekDays.dataset.value : null : null,
            })
        } else {
            await RotasTarefa.set({
                name: addForm.name.value,
                date: addForm.date.value,
                comment: addForm.comment.value,
                recurrent: addForm.recurrent.checked ? (JSON.parse(addForm.weekDays.dataset.value).length > 0 ? "true" : "false") : "false",
                priority: addForm.priority.value,
                daysWeek : addForm.recurrent.checked ? (JSON.parse(addForm.weekDays.dataset.value).length > 0 ? addForm.weekDays.dataset.value : null) : null,
                created_at: new Date()
            })
        }
        document.querySelector('body').removeChild(document.querySelector(".modal-backdrop"))
        addModal.style.display = "none"
        addModal.classList.remove('show')
        addModal.parentNode.classList.remove('modal-open')

    } else {
        if(addForm.name.value == "") {
            addForm.name.classList.add("wrong")
        }
        if(addForm.date.value == '') {
            addForm.date.classList.add("wrong")
        }
        if(addForm.date.value == undefined) {
            addForm.date.classList.add("wrong")
        }
        if(addForm.priority.value == 0) {
            addForm.priority.classList.add("wrong")
        }
    }

    addForm.name.value = ''
    addForm.date.value = ''
    addForm.comment.value = ''
    refreshData()
}