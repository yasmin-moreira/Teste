const usuario = this.localStorage.getItem("usuario");
let usuarioJson;
if (usuario) {
  usuarioJson = JSON.parse(usuario);
  console.log(usuarioJson);
} else {
  console.log("nenhum usuario encontrado");
}

this.fetch("/findTasks/notation/" + usuarioJson.id_usuario, {
  method: "GET"
})
  .then(response => response.text())
  .then(data => {
    // Atualizando o conteúdo do formulário
    const parser = new DOMParser();
    const htmlDoc = parser.parseFromString(data, 'text/html');
    const content = htmlDoc.querySelector('#formulario');

    // Substitui o conteúdo do #formulario no DOM
    const formulario = document.getElementById("formulario");
    formulario.innerHTML = content.innerHTML;

    this.fetch("/annotation/listAll/" + usuarioJson.id_usuario, {
      method: "GET"
    })

      .then(response => response.text())
      .then(data => {
        const parser = new DOMParser();
        const htmlDoc = parser.parseFromString(data, 'text/html');
        const content = htmlDoc.querySelector('#notesContainer');
        const formulario = document.getElementById("notesContainer");
        console.log(data)
        formulario.innerHTML = content.innerHTML;
      })
    // Reatribui eventos após a atualização do HTML
    attachEventListeners();
  });




// Função para reatribuir os eventos
function attachEventListeners() {

  const notesContainer = document.getElementById("notesContainer");
  const modal = document.getElementById("noteModal");
  const closeModalBtn = modal.querySelector(".close-btn");

  // Elementos do modal
  const modalTitle = document.getElementById("modalTitle");
  const modalTaskId = document.getElementById("modalTaskId");
  const modalDate = document.getElementById("modalDate");
  const modalText = document.getElementById("modalText");
  const modalSummary = document.getElementById("modalSummary");
  const modalSummaryT = document.getElementById("modalSumaryT");

  // Adiciona o evento de clique para cada anotação
  notesContainer.addEventListener("click", (event) => {
    const noteElement = event.target.closest(".note");
    if (noteElement) {
      // Obter os dados da anotação
      const id = noteElement.dataset.id;
      const title = noteElement.querySelector(".note-title").innerText;
      const taskId = noteElement.querySelector(".note-task").innerText;
      const date = noteElement.querySelector(".note-date").innerText;
      const text = noteElement.querySelector(".note-comment").innerText;
      const summary = noteElement.querySelector(".note-summarize").dataset.value;
      const resum = noteElement.querySelector(".note-resum").dataset.value;
      // Preenche os dados no modal
      modalTitle.innerText = title;
      modalTaskId.innerText = taskId;
      modalDate.innerText = date;
      modalText.innerText = text;
      modalSummary.innerText = summary;
      modalSummaryT.innerText = resum;

      // Exibe o modal
      modal.style.display = "block";
    }
  });

  // Fecha o modal ao clicar no botão de fechar
  closeModalBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });

  // Fecha o modal ao clicar fora do conteúdo
  window.addEventListener("click", (event) => {
    if (event.target === modal) {
      modal.style.display = "none";
    }
  });


  // Elementos do DOM
  const createNoteBtn = document.getElementById("createNoteBtn");
  const noteModal = document.getElementById("noteModal-notation");
  const closeModal = document.getElementById("closeModal");
  const noteForm = document.getElementById("noteForm-notation");
  const deleteNoteBtn = document.getElementById("deleteNoteBtn");


  let editingNote = null; // Para verificar se estamos editando uma anotação

  // Abrir o modal
  createNoteBtn.addEventListener("click", () => {
    editingNote = null;
    document.getElementById("modalTitle-notation").textContent = "Cadastrar Anotação";
    noteForm.reset();
    noteModal.style.display = "flex";
    deleteNoteBtn.style.display = "none"; // Oculta o botão de exclusão
  });

  // Fechar o modal
  closeModal.addEventListener("click", () => {
    noteModal.style.display = "none";
  });

  function applyOutlineColor(outlineElement, priority) {
    if (priority === "Baixa") {
      outlineElement.style.backgroundColor = "rgba(100, 194, 28, 0.978)";
    } else if (priority === "Média") {
      outlineElement.style.backgroundColor = "rgb(255, 217, 0)";
    } else if (priority === "Alta") {
      outlineElement.style.backgroundColor = "rgba(209, 30, 17, 0.816)";
    }
  }


  // Adicionar/Editar uma anotação
  noteForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const noteTitle = document.getElementById("noteTitle").value;
    const noteTask = document.getElementById("noteTask").value;
    const notePriority = document.getElementById("notePriority").value;
    const noteComment = document.getElementById("noteComment").value;
    const summarizeText = document.querySelector('input[name="summarizeText"]:checked').value;
    const noteImage = document.getElementById("noteImage").files[0]; // Obtem o arquivo de imagem

    // Seleciona o input do radio que está marcado
    const selectedOption = document.querySelector('input[name="summarizeText"]:checked');

    // Retorna o valor, ou null se nada estiver selecionado

    let desc = " ";
    let descResum = " ";
    const formData = new FormData();
    formData.append("image", noteImage);
    console.log(selectedOption.value)

    if(selectedOption.value == "Sim"){
      try {
        const response = await fetch("http://localhost:5000/upload/resum", {
          method: "POST",
          body: formData,
        });
  
        const data = await response.json();
        console.log(data); // Output do Python
  
        descResum = data
      } catch (error) {
        console.error("Error uploading image:", error);
      }
    }
      try {
        const response = await fetch("http://localhost:5000/upload", {
          method: "POST",
          body: formData,
        });
  
        const data = await response.json();
        console.log(data); // Output do Python
  
        desc = data
      } catch (error) {
        console.error("Error uploading image:", error);
      }

    console.log(desc)

    const data = {
      titulo: noteTitle,
      texto: noteComment,
      conteudoResumo: descResum.text,
      conteudoTexto: desc.text,
      tarefaIdTarefa: noteTask
    }

    console.log(data)

    fetch("/annotation/add", {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
    })
      .then(response => {
        if (!response.ok) {
          console.log("deu ruim")
        }

        return response.json()
      })

      .then(data => {
        alert("Inserida com sucesso");
        console.log(data);
      })

    let imageURL = ""; // Inicializa como vazio
    if (noteImage) {
      imageURL = URL.createObjectURL(noteImage); // Cria um URL temporário para a imagem
    }

    if (editingNote) {
      // Atualizar a anotação
      editingNote.querySelector(".note-title").textContent = noteTitle;
      editingNote.querySelector(".note-date").textContent = noteDate;
      editingNote.querySelector(".note-task").textContent = noteTask;
      editingNote.querySelector(".note-priority").textContent = notePriority;
      editingNote.querySelector(".note-comment").textContent = noteComment;
      editingNote.dataset.summarize = summarizeText;

      const imageElement = editingNote.querySelector(".note-image");
      if (noteImage) {
        imageElement.src = imageURL; // pega a imagem aqui
      } else {
        imageElement.style.display = "none";
      }

      const outlineElement = editingNote.querySelector(".outline-css");
      applyOutlineColor(outlineElement, notePriority);
    }

    noteModal.style.display = "none"; // Fechar modal
  });

  // Lógica de exclusão de anotações
  deleteNoteBtn.addEventListener("click", () => {
    if (editingNote) {
      notesContainer.removeChild(editingNote); // Remove a anotação do container
      editingNote = null; // Reseta a variável de edição
      noteModal.style.display = "none"; // Fecha o modal
      deleteNoteBtn.style.display = "none"; // Oculta o botão "Excluir"
    }
  });


}
