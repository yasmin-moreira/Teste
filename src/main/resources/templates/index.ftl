<#list tarefas as tarefa>
    <#assign cor = "">
    <#if tarefa.prioridade == 3>
        <#assign cor = "green">
    </#if>
    <#if tarefa.prioridade == 2>
        <#assign cor = "yellow">
    </#if>
    <#if tarefa.prioridade == 1>
        <#assign cor = "red">
    </#if>

    <div class="card-tarefa" style="border: 4px solid ${cor}; box-shadow: 0 0 5px ${cor};">
        <div class="card-corpo">
            <div class="card-header">
                <h5 class="card-titulo">${tarefa.nomeTarefa}</h5>
                <button class="btn_concluir" data-bs-toggle="modal" 
                        data-bs-target="#exampleModal-${tarefa.idTarefa}-concluir" 
                        id="botao_Modal_${tarefa.idTarefa}">
                </button>
            </div>
            <p class="card-texto">
                <#-- Formatar a data para exibição -->
                ${tarefa.dataprazo}<br>
            </p>
        </div>
    </div>
</#list>

<#list tarefas as tarefa>
    <div class="modal fade" id="exampleModal-${tarefa.idTarefa}-concluir" tabindex="1000" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">${tarefa.nomeTarefa}</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="Modal_conteudo">
                        <p><b>Deseja mesmo concluir esta tarefa?</b></p>
                    </div>
                    <div class="modal-footer" style="background-color: rgb(33, 115, 238);">
                        <button type="button" id="btn-fechar-modal" class="btn btn-fechar-modal" data-bs-dismiss="modal">Fechar</button>
                        <button type="button" class="btn_update_status" id="btn_${tarefa.idTarefa}">Concluir</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#list>

