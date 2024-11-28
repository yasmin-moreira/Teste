package maven.ti2cc.services;

import java.util.List;

import com.google.gson.Gson;

import maven.ti2cc.DAO.UsuarioDAO;
import maven.ti2cc.models.Usuario;
import spark.Request;
import spark.Response;


public class UsuarioService {

    UsuarioDAO dao;
    Gson gson;

    public UsuarioService() {

        this.dao = new UsuarioDAO();
        this.gson = new Gson();
    }

    /*public List<Usuario> buscarUsuarios() {

        return dao.listUsers();
    } */

    public Usuario buscarUsuarioPorId(int id) throws Exception {

        Usuario usuario = dao.findById(id); // Obtenha o usuário
        System.out.println(usuario);

        if (usuario == null)
            throw new Exception("Usuario no encontrado");

        return usuario;
    }


    public Object getUserbyNameandPassword(Request request, Response response) throws Exception {
        String nomeUsuario = request.params("username");
        String password = request.params("password");
    
        Usuario u = null;
        try {
            u = dao.getUser(nomeUsuario, password);
            if (u != null) {
                response.status(200); // Usuário encontrado
                return gson.toJson(u);
            } else {
                response.status(404); // Usuário não encontrado
                return "Usuário não encontrado.";
            }
        } catch (Exception e) {
            response.status(500); // Erro no servidor
            System.out.println(e.getMessage());
            return "Erro ao buscar o usuário: " + e.getMessage();
        }
    }

    public Object insertUsuario(Request request, Response response) throws Exception {
        Gson gson = new Gson();

        Usuario usuario = gson.fromJson(request.body(), Usuario.class);

        try {
            dao.insert(usuario);
            response.status(201);
        }

        catch (Exception e) {
            response.status(400);
            throw new RuntimeException("Erro ao inserir usuario no sistema" + e.getMessage());
        }
        return gson.toJson(usuario);
    }

    public Object atualizarCadastro(Request req, Response res, int id) {

        res.type("application/json"); // Define o tipo de resposta como JSON

        String json = req.body(); // Captura o corpo da solicitação
        Usuario usuarioAtualizado = gson.fromJson(json, Usuario.class); // Converte JSON para objeto Usuario

        if (dao.updateUser(usuarioAtualizado, id)) {
            res.status(200); // Status 200 (OK)
            return "{\"status\":\"Cadastro atualizado com sucesso.\"}"; // Mensagem de sucesso
        }

        else {
            res.status(500); // Status 500 (Internal Server Error)
            return "{\"erro\":\"Falha ao atualizar o cadastro.\"}"; // Mensagem de erro
        }
    }

    public void deletarUsuario(int id) {

        try {
            dao.deleteUser(id);
        }

        catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuario no sistema" + e.getMessage());
        }

    }

    public String teste(Request req, Response res) throws Exception {
        return "TESTE";
    }
}
