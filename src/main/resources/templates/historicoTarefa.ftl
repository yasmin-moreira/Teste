<#list tarefas as tarefa>
    <div class="tarefaConclud" onclick="openModal(${tarefa.idTarefa})">
        <h3 class="h3">${tarefa.nomeTarefa}</h3>
        <p class="p">Data: ${tarefa.dataprazo}</p> <!-- Usando dataprazo diretamente -->

        <p class="p">Horário: ${tarefa.dataprazo!}</p> <!-- Aqui você pode usar a lógica que precisar para formatar o horário -->
        
        <p class="p">Status: 
            <#if tarefa.status == 0>
                Pendente
            <#elseif tarefa.status == 1>
                Em Progresso
            <#elseif tarefa.status == 2>
                Concluída
            </#if>
        </p>
        
        <div class="prioridade-circle" style="background-color: ${tarefa.prioridade!}">
            <!-- A cor de prioridade é baseada na prioridade do objeto -->
        </div>
    </div>
</#list>
