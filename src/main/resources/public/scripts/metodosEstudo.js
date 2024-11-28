const carregarJSON = async (filePath = "") => {
  const data = await fetch(filePath)
    .then(async (res) => {
      return await res.json();
    })
    .then((data) => {
      return data;
    });
  return data;
};

const cards = document.getElementById("parte-metodos");

const init = async () => {
  const data = await carregarJSON("assets/scripts/data/data.json")
  data.metodosEstudo.forEach(element => {
    cards.innerHTML += `
  
      <div class="modal fade" id="exampleModalCenter-${element.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLongTitle">${element.nome}</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body-${element.id} modal-text">
            ${element.descricao}
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-dismiss="modal" >Fechar</button>
            </div>
          </div>
        </div>
      </div>
  
      <div class="div-item" data-toggle="modal" data-target="#exampleModalCenter-${element.id}">
        <div class="title-item">
            ${element.nome}
        </div>
      </div>
      `;

  });

  try {
    const dataCustom = await carregarJSON("http://localhost:8080/metodos")
    dataCustom.data.forEach((el) => {
      cards.innerHTML += `
      <div class="modal fade" id="CustomMethodModal-${el.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
          <div class="modal-content">
            <form id="edit-form-${el.id}">
              <div class="modal-header">
                  <input type="text" value="${el.nome}" class="w-100 fs-4">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                  </button>
              </div>
              <textarea name="" id="" class="w-100 fs-5" style="min-height: 400px;">
                  ${el.descricao}
              </textarea>
              <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-dismiss="modal" >Fechar</button>
              <button type="button" class="btn btn-warning">Editar</button>
              </div>
            </form>
          </div>
        </div>
      </div>
  
      <div class="div-item" data-toggle="modal" data-target="#CustomMethodModal-${el.id}">
        <div class="title-item">
            ${el.nome}
        </div>
        <div class="btn-danger delete-btn" data-method-id="${el.id}">
          Excluir
        </div>
      </div>
    `
      document.querySelector(`#CustomMethodModal-${el.id}`).addEventListener("submit", async (ev) => {
        ev.preventDefault()
        body = JSON.stringify({
          nomemetodo: ev.target.titleMethod.value,
          descricaometodo: ev.target.descriptionMethod.value,
        })
        try {
          const data = await fetch("http://localhost:8080/atualizarMetodo/id", {
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: body,
            method: "PUT"
          })
            .then(async (res) => {
              return await res.json();
            })
            .then((data) => {
              return data;
            });
          window.location.reload()

        } catch (err) {
          document.querySelector(`#CustomMethodModal-${el.id} .modal-footer`).innerHTML = `
          <button type="button" class="btn btn-primary" data-dismiss="modal" >Fechar</button>
            <button type="button" class="btn btn-warning">Editar</button>
            <div class="alert alert-danger w-100" role="alert">
              Não foi possível salvar, tente novamente.
            </div>
          `
        }
      })
    })
  } catch (err) {

  }

  cards.innerHTML += `

  <div class="modal fade" id="createMethod" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLongTitle">Criar Método</h5>
          
        </div>
        <form id="adicionar-tarefa">
          <div class="modal-body-adicionar modal-text">
              <div>
                  <label for="titleMethod">Título</label><br/>
                  <input type="text" id="titleMethod" class="w-100" placeholder="Adicione um título">
              </div>
              <div class="mt-4">
                  <label for="descriptionMethod">Descrição</label><br/>
                  <input type="text" id="descriptionMethod" class="w-100" placeholder="Adicione uma descrição">
              </div>
            
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-dismiss="modal" >Cancelar</button>
            <button type="submit" class="btn btn-success" >Adicionar</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <div class="btn-adicionar div-item w-100" data-toggle="modal" data-target="#createMethod">
      <div class="title-item">
          Adicionar método
      </div>
  </div>

  `

  document.querySelector('#adicionar-tarefa').addEventListener('submit', async (ev) => {
    ev.preventDefault()
    body = JSON.stringify({
      nomemetodo: ev.target.titleMethod.value,
      descricaometodo: ev.target.descriptionMethod.value,
    })
    try {
      const data = await fetch("http://localhost:8080/inserirMetodo", {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: body,
        method: "POST"
      })
        .then(async (res) => {
          return await res.json();
        })
        .then((data) => {
          return data;
        });
      window.location.reload()

    } catch (err) {
      document.querySelector("#adicionar-tarefa .modal-footer").innerHTML = `
      <button type="button" class="btn btn-primary" data-dismiss="modal" >Cancelar</button>
      <button type="submit" class="btn btn-success" >Adicionar</button>
      <div class="alert alert-danger w-100" role="alert">
        Não foi possível salvar, tente novamente.
      </div>
      `
    }

    // console.log(body);

  })

  console.log(document.querySelectorAll(".div-item .delete-btn"));
  document.querySelectorAll(".div-item>.delete-btn").forEach(el => {
    el.addEventListener("click", async (ev) => {
      const id = ev.target.dataset.methodId
      try {
        const data = await fetch(`http://localhost:8080/deleteMetodo/${id}`)
          .then(window.location.reload())
      } catch (err) {
        alert("Ocorreu um erro, tente novamente!")
      }
    })
  })
}

window.addEventListener('load', () => init())