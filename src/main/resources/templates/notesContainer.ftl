    <div class="notes-container" id="notesContainer">
        <#list anotacoes as anotacao>

      <div class="note" data-id="${anotacao.idAnotacao}">
                <img src="../images/threeDots.png" class="img-dots" />
                <div class="name-part-notation">
                <div class="note-title">${anotacao.titulo}</div>
                <div class="outline-css"></div>
                </div>
                <div class="info-part-notation">
                <p class="note-task">${anotacao.tarefaIdTarefa}</p>
                <p class="note-date">${anotacao.dataCriacao}</p>
                <p class="note-comment">${anotacao.texto}</p>
                <p data-value="${anotacao.conteudoTexto}" class="note-resum"></p>
                <p data-value="${anotacao.conteudoResumo}" class="note-summarize"></p>
                </div> 
      </div>
         </#list>  
    </div>