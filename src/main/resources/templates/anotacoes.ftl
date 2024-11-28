<div id="formulario">
  <div class="container-notation">
    <header>
      <h1>Anotações</h1>
      <button id="createNoteBtn">+ Cadastrar Anotação</button>
    </header>

    <div class="notes-container" id="notesContainer">
      <!-- anotações aqui -->
    </div>
  </div>

  <!-- Modal anotação -->
  <div id="noteModal-notation" class="modal">
    <div class="modal-content-notation">
      <span id="closeModal" class="close">&times;</span>
      <h2 id="modalTitle-notation">Cadastrar Anotação</h2>
      <form id="noteForm-notation">
        <div class="input-part-notation">
          <label for="noteTitle">Título da Anotação:</label>
          <input type="text" id="noteTitle" name="noteTitle" required>
        </div>
        <div class="input-part-notation">
          <label for="noteTask">Tarefa Associada:</label>

          <select name="noteTask" id="noteTask">
            <#list tarefas as tarefa>
              <option value="${tarefa.idTarefa}">${tarefa.nomeTarefa}</option>
            </#list>
          </select>
        </div>
        <div class="input-part-notation">
          <label for="notePriority">Prioridade:</label>
          <select id="notePriority" name="notePriority" required>
            <option value="Baixa">Baixa</option>
            <option value="Média">Média</option>
            <option value="Alta">Alta</option>
          </select>
        </div>
        <div class="input-part-notation">
          <label>Deseja resumir o texto?</label>
          <div>
            <label>
              <input type="radio" name="summarizeText" value="Sim" required> Sim
            </label>
            <label>
              <input type="radio" name="summarizeText" value="Não"> Não
            </label>
          </div>
        </div>
        <div class="input-part-notation">
          <label for="noteImage">Carregar Imagem:</label>
          <input type="file" id="noteImage" name="noteImage" accept="image/*">
        </div>
        <div class="input-part-notation">
          <label for="noteComment">Comentário:</label>
          <textarea id="noteComment" name="noteComment" rows="4" cols="50"></textarea>
        </div>
        <button type="submit" id="saveNoteBtn">Salvar</button>
        <button type="button" id="deleteNoteBtn" style="display: none;">Excluir</button>
      </form>
    </div>
  </div>

</div>