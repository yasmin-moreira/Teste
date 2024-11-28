package maven.ti2cc.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import maven.ti2cc.models.Tarefa;
import maven.ti2cc.DAO.TarefaDAO;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class TarefaService {
    private TarefaDAO dao;
    private Gson gson;

    public TarefaService() {
        this.dao = new TarefaDAO();
        this.gson = new Gson();
    }

    // Método para listar todas as tarefas
   /* public List<Tarefa> listarTarefas() {
        return dao.listarTarefas();
    } */


    // Método para buscar tarefa por ID
    /*public Tarefa buscarTarefaPorId(int id) throws Exception {
        Tarefa tarefa = dao.findById(id);
        if (tarefa == null) {
            throw new Exception("Tarefa não encontrada");
        }
        return tarefa;
    } */

    public Object deleteTarefa(Request request, Response response) throws Exception{
        String nome = request.params("nome");
        int userid = Integer.parseInt(request.params("userid"));
        System.out.println(nome + userid + "\n\n\n\n");
        try{
            dao.deleteTarefa(nome, userid);
            response.status(200);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return true;
    }

    public Object insertTarefa(Request request, Response response) throws Exception {
        Gson gson = new Gson();

        String json = request.body();

        int startIndex = json.indexOf("\"recorrencia\":") + "\"recorrencia\":".length();
        int endIndex = json.indexOf("]", startIndex) + 1;
        
        String recorrenciaJson = json.substring(startIndex, endIndex);

        Tarefa newTarefa = new Tarefa();

        newTarefa = gson.fromJson(request.body(), Tarefa.class);

        newTarefa.setRecorrencia(recorrenciaJson);
        
        try {
          /* dao.insertTarefa(newTarefa); */
            dao.insertTarefa(newTarefa);
            response.status(201);

        } catch (Exception e) {

            throw new RuntimeException("Erro ao inserir tarefa no sistema: " + e.getMessage());
        }

        System.out.println("Tarefa criada com sucesso");
        return true;
    }

    public ArrayList<Tarefa> listAllTasksbyId(Request request, Response response){
        int id = Integer.parseInt(request.params("userId"));
        ArrayList<Tarefa> t = new ArrayList<Tarefa>();
        try{    
            t = dao.findAllTasks(id);
        }catch(Exception e){
            response.status(404);
            System.out.println(e.getMessage());
        }
        return t;
    }


    // Método para atualizar tarefa
    /*  public Object atualizarTarefa(Request req, Response res, int id) {

        res.type("application/json");
        String json = req.body();
        Tarefa tarefaAtualizada = gson.fromJson(json, Tarefa.class);

        try {
            if (dao.updateTarefa(tarefaAtualizada, id)) {
                res.status(200); // Status 200 (OK)
                return "{\"status\":\"Tarefa atualizada com sucesso.\"}";
            } else {
                res.status(500); // Status 500 (Internal Server Error)
                return "{\"erro\":\"Falha ao atualizar a tarefa.\"}";
            }
        } catch (Exception e) {
            res.status(500); // Status 500 (Internal Server Error)
            return "{\"erro\":\"Erro ao atualizar a tarefa: " + e.getMessage() + "\"}";
        }
    } */

    // Método de teste
    public String teste(Request req, Response res) {
        return "Teste de serviço de tarefa.";
    }
}
