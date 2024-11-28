
import { addBtn, dateInput, dataAtual, refreshData, editarCriarTarefa} from "./global.js";

dateInput.min = `${dataAtual.getFullYear()}-${(dataAtual.getMonth() + 1).toLocaleString(undefined, { minimumIntegerDigits: 2 })}-${dataAtual.getDate().toLocaleString(undefined, { minimumIntegerDigits: 2 })}T${dataAtual.getHours().toLocaleString(undefined, { minimumIntegerDigits: 2 })}:${dataAtual.getMinutes().toLocaleString(undefined, { minimumIntegerDigits: 2 })}`
dateInput.max = `${dataAtual.getFullYear() + 100}-${(dataAtual.getMonth() + 1).toLocaleString(undefined, { minimumIntegerDigits: 2 })}-${dataAtual.getDate().toLocaleString(undefined, { minimumIntegerDigits: 2 })}T${dataAtual.getHours().toLocaleString(undefined, { minimumIntegerDigits: 2 })}:${dataAtual.getMinutes().toLocaleString(undefined, { minimumIntegerDigits: 2 })}`


// Cria/Edita tarefa
addBtn.addEventListener('click', async (ev) => {

    editarCriarTarefa(ev);
    
})

refreshData()