package maven.ti2cc.app;

import static spark.Spark.*;
import com.google.gson.Gson;
import maven.ti2cc.services.UsuarioService;
import maven.ti2cc.services.TarefaService;
import maven.ti2cc.services.MetodosEstudosService;
import maven.ti2cc.services.AnotacoesService;
import maven.ti2cc.utils.GsonUtil;
import maven.ti2cc.models.*;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;


import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        port(8080);
        
        staticFiles.location("/public");        

        UsuarioService usuarioService = new UsuarioService();
        TarefaService tarefaService = new TarefaService();
        MetodosEstudosService metodosEstudosService = new MetodosEstudosService();
        AnotacoesService anotacaoService = new AnotacoesService();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

        cfg.setClassLoaderForTemplateLoading(App.class.getClassLoader(), "templates"); // Onde 'templates' é a pasta dos templates
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Gson gson = GsonUtil.createGson();

        get("/desempenho/:userId", (request, response) -> {
            ArrayList<Tarefa> T = tarefaService.listAllTasksbyId(request, response);

            return gson.toJson(T);
        });
        
        get("/findTasks/notation/filter/:userId", (request, response) -> {
            ArrayList<Tarefa> T = tarefaService.listAllTasksbyId(request, response);
            ArrayList<Tarefa> tarefa = new ArrayList<Tarefa>();
            for(int i = 0; i<T.size(); i++){
                if(T.get(i).getStatus() == 2){
                    tarefa.add(T.get(i));
                }
            }
            System.out.println(tarefa + "\n\n\n\n");

            Map<String, Object> data = new HashMap<>(); 
            data.put("tarefas", tarefa);

            Template template = cfg.getTemplate("historicoTarefa.ftl");

            StringWriter writer = new StringWriter();

            template.process(data, writer);
            System.out.println(writer.toString());
            response.body(writer.toString());
            return writer.toString();
        });

        get("/annotation/listAll/:id", (request, response) -> {
            ArrayList<Anotacoes> anotacao = anotacaoService.listAll(request, response);
            
            Map<String, Object> data = new HashMap<>(); 
            data.put("anotacoes", anotacao);

            Template template = cfg.getTemplate("notesContainer.ftl");

            StringWriter writer = new StringWriter();

            template.process(data, writer);
            System.out.println(writer.toString());
            response.body(writer.toString());
            return writer.toString();
        });

        post("/annotation/add", (request, response) ->  anotacaoService.inserirAnotacao(request, response));

        get("/findTasks/notation/list/:userId", (request, response) -> {
            ArrayList<Tarefa> T = tarefaService.listAllTasksbyId(request, response);
            Collections.sort(T, Comparator.comparing(Tarefa::getDataprazo));

            ArrayList<Tarefa> tarefa = new ArrayList<Tarefa>();
            System.out.println(tarefa + "\n\n\n\n");

            for(int i = 0; i < T.size(); i++){
                if(T.get(i).getStatus() != 2){
                    tarefa.add(T.get(i));
                }
            }

            return gson.toJson(tarefa);
        });
        
        get("/findTasks/notation/:userId", (request, response) -> {
            ArrayList<Tarefa> tarefa = tarefaService.listAllTasksbyId(request, response);
            System.out.println(tarefa + "\n\n\n\n");

            Map<String, Object> data = new HashMap<>(); 
            data.put("tarefas", tarefa);

            Template template = cfg.getTemplate("anotacoes.ftl");

            StringWriter writer = new StringWriter();

            template.process(data, writer);
            System.out.println(writer.toString());
            response.body(writer.toString());
            return writer.toString();
        });

        get("findTasks/notation/find/:userId", (request, response) -> {
            ArrayList<Tarefa> T = tarefaService.listAllTasksbyId(request, response);
            Collections.sort(T, Comparator.comparing(Tarefa::getDataprazo));

            ArrayList<Tarefa> tarefa = new ArrayList<Tarefa>();
            System.out.println(tarefa + "\n\n\n\n");

            for(int i = 0; i < T.size(); i++){
                if(T.get(i).getStatus() != 2){
                    tarefa.add(T.get(i));
                }
            }

            Map<String, Object> data = new HashMap<>(); 
            data.put("tarefas", tarefa);

            Template template = cfg.getTemplate("tarefasVertical.ftl");

            StringWriter writer = new StringWriter();

            template.process(data, writer);
            System.out.println(writer.toString());
            response.body(writer.toString());
            return writer.toString();
        });

        //deletar tarefa
        delete("/tarefa/delete/:nome/:userid", (request, response) -> tarefaService.deleteTarefa(request, response));

        get("/findTasks/:userId", (request, response) -> {
            ArrayList<Tarefa> T = tarefaService.listAllTasksbyId(request, response);
            ArrayList<Tarefa> tarefa = new ArrayList<Tarefa>();
            System.out.println(tarefa + "\n\n\n\n");

            for(int i = 0; i < T.size(); i++){
                if(T.get(i).getStatus() != 2){
                    tarefa.add(T.get(i));
                }
            }

            Map<String, Object> data = new HashMap<>(); 
            data.put("tarefas", tarefa);

            Template template = cfg.getTemplate("index.ftl");

            StringWriter writer = new StringWriter();

            template.process(data, writer);
            System.out.println(writer.toString());
            response.body(writer.toString());
            return writer.toString();
        });

        /*get("/listar", (req, res) -> {

            res.type("application/json");

            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(usuarioService.buscarUsuarios())));
        }); */

        post("/inserir", (request, response) -> usuarioService.insertUsuario(request, response));

        post("tarefa/inserir", (request, response) -> tarefaService.insertTarefa(request, response));

        get("/usuario/:username/:password", (request, response) -> usuarioService.getUserbyNameandPassword(request, response));

        put("/atualizar/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));
            Object resultado = usuarioService.atualizarCadastro(req, res, id);

            res.type("application/json");

            return resultado;
        });

        delete("/delete/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null)
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Usuário não encontrado."));
            usuarioService.deletarUsuario(id);
            res.status(200);

            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Usuário deletado com sucesso."));
        });


        // Rotas para métodos de estudo
        get("/metodos", (req, res) -> {
            res.type("application/json");

            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS,
                    gson.toJsonTree(metodosEstudosService.buscarMetodos())));
        });

        post("/inserirMetodo", (req, res) -> {

            res.type("application/json");

            MetodosEstudos metodo = gson.fromJson(req.body(), MetodosEstudos.class);

            try {
                metodosEstudosService.insertMetodo(metodo);
            } catch (Exception e) {
                res.status(400);
                return gson.toJson("Erro: " + e.getMessage());
            }

            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(metodo)));
        });

        put("/atualizarMetodo/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));

            return metodosEstudosService.atualizarMetodo(req, res, id);
        });

        delete("/deleteMetodo/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));
            MetodosEstudos metodo = metodosEstudosService.buscarMetodoPorId(id);

            if (metodo == null) {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Método de estudo não encontrado."));
            }

            metodosEstudosService.deletarMetodo(id);
            res.status(200);

            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Método de estudo deletado com sucesso."));
        });


        get("/listarAnotacao/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));
            Anotacoes anotacao = anotacaoService.buscarAnotacaoPorId(id);
            
            if (anotacao == null) {
                res.status(404);
                return gson.toJson(new StandardResponse(StatusResponse.ERROR, "Anotação não encontrada."));
            }
            
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, gson.toJsonTree(anotacao)));
        });

        put("/atualizarAnotacao/:id", (req, res) -> {
            
            int id = Integer.parseInt(req.params(":id"));

            return anotacaoService.atualizarAnotacao(req, res, id);
        });

        delete("/deleteAnotacao/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));

            anotacaoService.deletarAnotacao(id);
            res.status(200);
            
            return gson.toJson(new StandardResponse(StatusResponse.SUCCESS, "Anotação deletada com sucesso."));
        });
    }
}
