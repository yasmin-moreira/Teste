<#list tarefas as tarefa>
<div class="modal fade" id="exampleModal-${tarefa.idTarefa}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">${tarefa.nomeTarefa}</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="Modal_conteudo">
                    <p><b>Data:</b> ${tarefa.dataprazo}</p>
                </div>
                <div class="Modal_conteudo">
                    <p><b>Recorrência:</b> 
                        <#list tarefa.recorrencia as dia>
                            <#if dia>Sim<#else>Não</#if>
                        </#list>
                    </p>
                    <p><b>Prioridade:</b> 
                        <#if tarefa.prioridade == 1>Baixa</#if>
                        <#if tarefa.prioridade == 2>Média</#if>
                        <#if tarefa.prioridade == 3>Alta</#if>
                    </p>
                </div>
                <div class="Modal-Comentario">
                    <p><b>Descrição:</b> ${tarefa.descricao}</p>
                </div>
                <div class="modal-footer">
                    <button type="button" id="btn-fechar-modal" class="btn btn-fechar-modal" data-bs-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="tarefas" id="${tarefa.idTarefa}">
    <div class="conteudo">
        <h3>${tarefa.nomeTarefa}</h3>
        <p>${tarefa.dataprazo}</p>
        <div class="conclude-div">
            <button class="btn_concluir" data-bs-toggle="modal" data-bs-target="#exampleModal-${tarefa.idTarefa}" id="botao_Modal_${tarefa.idTarefa}">Concluir</button>
        </div>
    </div>
    <div class="icone">
        <div class="d-flex">
            <div class="btn btnActions btn-sm edit-btn" id="btn-edit" data-type-btn="edit" data-id-tarefa="${tarefa.idTarefa}" data-bs-toggle="modal" data-bs-target="#addModal">Editar</div>
            <div class="btn btnActions btn-sm exclude-btn" id="btn-exclude" data-nome="${tarefa.nomeTarefa}" onclick="deletarTarefa(this)" data-type-btn="exclude" data-id-tarefa="${tarefa.idTarefa}">Excluir</div>
        </div>
        <div>
            <button type="button" class="btn btn-see-more" data-bs-toggle="modal" data-bs-target="#exampleModal-${tarefa.idTarefa}" id="botao_Modal">Veja Mais</button>
        </div>
    </div>
</div>
</#list>
