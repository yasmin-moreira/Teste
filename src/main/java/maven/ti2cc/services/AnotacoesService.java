package maven.ti2cc.services;

import com.google.gson.Gson;
import maven.ti2cc.models.Anotacoes;
import maven.ti2cc.DAO.AnotacoesDAO;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class AnotacoesService {

    private AnotacoesDAO dao;
    private Gson gson;

    public AnotacoesService() {

        this.dao = new AnotacoesDAO();
        this.gson = new Gson();
    }


    public ArrayList<Anotacoes> listAll(Request request, Response response){
        int id = Integer.parseInt(request.params("id"));
        ArrayList<Anotacoes> A = new ArrayList<Anotacoes>();
        try{
            A = dao.listAnotacoesPorUsuario(id);
            return A;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Método para buscar uma anotação por ID
    public Anotacoes buscarAnotacaoPorId(int id) throws Exception {
        Anotacoes anotacao = dao.findById(id);
        if (anotacao == null) {
            throw new Exception("Anotação não encontrada");
        }
        return anotacao;
    }

    // Método para inserir uma nova anotação
    public Object inserirAnotacao(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        Anotacoes anotacao;
        try {
            
            anotacao = gson.fromJson(request.body(), Anotacoes.class);
            anotacao.setDataCriacao();
            
            System.out.println(anotacao + "\n\n\n\n\n");

            dao.insert(anotacao);
            
            response.status(201);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir anotação no sistema: " + e.getMessage());
        }
        System.out.println("Anotação inserida com sucesso");

        return true;
    }

    // Método para atualizar uma anotação existente
    public Object atualizarAnotacao(Request req, Response res, int id) {
        res.type("application/json");
        String json = req.body();
        Anotacoes anotacaoAtualizada = gson.fromJson(json, Anotacoes.class);

        if (dao.updateAnotacao(anotacaoAtualizada, id)) {
            res.status(200);
            return "{\"status\":\"Anotação atualizada com sucesso.\"}";
        } else {
            res.status(500);
            return "{\"erro\":\"Falha ao atualizar a anotação.\"}";
        }
    }

    // Método para deletar uma anotação
    public void deletarAnotacao(int id) {
        try {
            dao.deleteAnotacao(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar anotação do sistema: " + e.getMessage());
        }
    }

    // Método de teste para rota
    public String teste(Request req, Response res) {
        return "Testando AnotacoesService!";
    }
}
