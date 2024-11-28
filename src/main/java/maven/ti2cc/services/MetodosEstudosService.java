package maven.ti2cc.services;

import com.google.gson.Gson;
import maven.ti2cc.models.MetodosEstudos;
import maven.ti2cc.DAO.MetodosEstudosDAO;
import spark.Request;
import spark.Response;

import java.util.List;

public class MetodosEstudosService {
    
    MetodosEstudosDAO dao;
    Gson gson;

    public MetodosEstudosService() {
        this.dao = new MetodosEstudosDAO();
        this.gson = new Gson();
    }

    // Busca todos os métodos de estudos
    public List<MetodosEstudos> buscarMetodos() {
        return dao.listMetodos();
    }

    // Busca um método de estudo por ID
    public MetodosEstudos buscarMetodoPorId(int id) throws Exception {
        MetodosEstudos metodo = dao.findById(id);
        System.out.println(metodo);
        if (metodo == null)
            throw new Exception("Método de estudo não encontrado");

        return metodo;
    }

    // Faz a rota /inserir funcionar
    public void insertMetodo(MetodosEstudos metodo) throws Exception {
        try {
            dao.insert(metodo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir método de estudo no sistema: " + e.getMessage());
        }

        System.out.println("Método de estudo inserido com sucesso");
    }

    // Atualiza um método de estudo
    public Object atualizarMetodo(Request req, Response res, int id) {
        res.type("application/json");
        String json = req.body();
        MetodosEstudos metodoAtualizado = gson.fromJson(json, MetodosEstudos.class);
        if (dao.updateMetodo(metodoAtualizado, id)) {
            res.status(200);
            return "{\"status\":\"Método de estudo atualizado com sucesso.\"}";
        } else {
            res.status(500);
            return "{\"erro\":\"Falha ao atualizar o método de estudo.\"}";
        }
    }

    // Deleta um método de estudo
    public void deletarMetodo(int id) {
        try {
            dao.deleteMetodo(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar método de estudo no sistema: " + e.getMessage());
        }
    }

    // Método de teste para rota
    public String teste(Request req, Response res) throws Exception {
        return "Testando MetodosEstudosService!";
    }
}
