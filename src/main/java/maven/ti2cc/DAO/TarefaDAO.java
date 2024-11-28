package maven.ti2cc.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Array;

import maven.ti2cc.models.Tarefa;

public class TarefaDAO extends DAO {

    public TarefaDAO() {
        super();
        connect();
    }

    public void finish() {
        super.close();
    }

    public Tarefa insertTarefa(Tarefa tarefa) throws Exception {
        // Converte o array de booleanos para Boolean[] (auto-boxing)
        boolean[] recorrenciaArray = tarefa.isRecorrencia();
        Boolean[] recorrenciaArrayObject = new Boolean[recorrenciaArray.length];
        for (int i = 0; i < recorrenciaArray.length; i++) {
            recorrenciaArrayObject[i] = recorrenciaArray[i];
        }

        try {
            if (tarefa.getUsuarioIdUsuario1() <= 0) {
                throw new RuntimeException("ID do usuário inválido.");
            }

            // Verifica se o usuário existe antes de inserir a tarefa
            if (!usuarioExiste(tarefa.getUsuarioIdUsuario1())) {
                throw new RuntimeException("Usuário não encontrado.");
            }

            // SQL para inserir a tarefa
            String sql = "INSERT INTO tarefa (nometarefa, descricao, dataprazo, usuario_id_usuario1, recorrencia, status, prioridade) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Usando RETURN_GENERATED_KEYS para pegar o ID gerado
            try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                // Configura os parâmetros do PreparedStatement
                stmt.setString(1, tarefa.getNomeTarefa());
                stmt.setString(2, tarefa.getDescricao());
                stmt.setString(3, tarefa.getDataprazo());
                stmt.setInt(4, tarefa.getUsuarioIdUsuario1());

                // Converte o array de Boolean[] para um tipo SQL Array
                Array sqlArray = connection.createArrayOf("boolean", recorrenciaArrayObject);
                stmt.setArray(5, sqlArray); // A posição correta para o array de booleanos

                stmt.setInt(6, tarefa.getStatus());
                stmt.setInt(7, tarefa.getPrioridade());

                // Executa a inserção no banco de dados
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    // Obtém o ID gerado
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        tarefa.setIdTarefa(id); // Define o ID da tarefa
                    }
                }
            }
            System.out.println("Tarefa inserida com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tarefa no sistema: " + e.getMessage());
        }

        return tarefa;
    }

    private boolean usuarioExiste(int usuarioId) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar a existência do usuário: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<Tarefa> findAllTasks(int userID) {
        String sql = "SELECT * FROM tarefa WHERE usuario_id_usuario1 = ?";
        ArrayList<Tarefa> tarefas = new ArrayList<>();
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID); // Define o parâmetro do SQL.
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Cria um novo objeto Tarefa.
                    Tarefa tarefa = new Tarefa();
    
                    // Define os campos da tarefa com base nos resultados.
                    tarefa.setIdTarefa(rs.getInt("id_tarefa"));
                    tarefa.setNomeTarefa(rs.getString("nomeTarefa"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setDataprazo(rs.getString("dataprazo"));
                    tarefa.setUsuarioIdUsuario1(rs.getInt("usuario_id_usuario1"));

                    // Lê o array boolean[] diretamente, dependendo do suporte do driver.
                    Array recorrenciaArray = rs.getArray("recorrencia"); // Supondo que o banco suporte arrays.
                    if (recorrenciaArray != null) {
                        Object[] recorrenciaObj = (Object[]) recorrenciaArray.getArray();
                        boolean[] recorrencia = new boolean[recorrenciaObj.length];
                        for (int i = 0; i < recorrenciaObj.length; i++) {
                            recorrencia[i] = (Boolean) recorrenciaObj[i];
                        }
                        tarefa.setRecorrencia(recorrencia);
                    }
                    tarefa.setStatus(rs.getInt("status"));

                    tarefa.setPrioridade(rs.getInt("prioridade"));
    
    
                    // Adiciona a tarefa à lista.
                    tarefas.add(tarefa);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Lida com erros de SQL.
        }
    
        return tarefas; // Retorna a lista de tarefas.
    }
    

    // Método para buscar uma tarefa por ID
    /*
     * public Tarefa findById(int id) {
     * String sql = "SELECT * FROM tarefa WHERE id_tarefa = ?";
     * Tarefa tarefa = null;
     * 
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setInt(1, id);
     * ResultSet rs = stmt.executeQuery();
     * 
     * if (rs.next()) {
     * tarefa = new Tarefa(
     * rs.getInt("id_tarefa"),
     * rs.getString("nometarefa"),
     * rs.getString("descricao"),
     * rs.getString("dataprazo"),
     * rs.getString("prioridade"),
     * rs.getString("status"),
     * rs.getString("datacriacao"),
     * rs.getBoolean("recorrencia"),
     * rs.getString("diasdasemana"),
     * rs.getInt("usuario_id_usuario1")
     * );
     * }
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * return tarefa;
     * }
     */

    /*
     * public boolean updateTarefa(Tarefa tarefa, int id) {
     * String sql =
     * "UPDATE tarefa SET nometarefa = ?, descricao = ?, dataprazo = ?, prioridade = ?, status = ?, datacriacao = ?, recorrencia = ?, diasdasemana = ?, usuario_id_usuario1 = ? WHERE id_tarefa = ?"
     * ;
     * 
     * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
     * stmt.setString(1, tarefa.getNomeTarefa());
     * stmt.setString(2, tarefa.getDescricao());
     * stmt.setString(3, tarefa.getDataprazo());
     * stmt.setString(4, tarefa.getPrioridade());
     * stmt.setString(5, tarefa.getStatus());
     * stmt.setString(6, tarefa.getDataCriacao());
     * stmt.setBoolean(7, tarefa.isRecorrencia());
     * stmt.setString(8, tarefa.getDiasDaSemana());
     * stmt.setInt(9, tarefa.getUsuarioIdUsuario1());
     * stmt.setInt(10, id);
     * 
     * int affectedRows = stmt.executeUpdate();
     * System.out.println("Linhas afetadas: " + affectedRows);
     * 
     * return affectedRows > 0;
     * } catch (SQLException e) {
     * e.printStackTrace();
     * return false;
     * }
     * }
     */

    // Método para deletar uma tarefa
    public boolean deleteTarefa(String nome, int id) {
        String sql = "UPDATE tarefa SET status = 2 WHERE nometarefa = ? AND usuario_id_usuario1 = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todas as tarefas
    /*
     * public List<Tarefa> listarTarefas() {
     * String sql = "SELECT * FROM tarefa";
     * List<Tarefa> listaTarefas = new ArrayList<>();
     * 
     * try (PreparedStatement stmt = connection.prepareStatement(sql);
     * ResultSet rs = stmt.executeQuery()) {
     * 
     * while (rs.next()) {
     * Tarefa tarefa = new Tarefa(
     * rs.getInt("id_tarefa"),
     * rs.getString("nometarefa"),
     * rs.getString("descricao"),
     * rs.getString("dataprazo"),
     * rs.getString("prioridade"),
     * rs.getString("status"),
     * rs.getString("datacriacao"),
     * rs.getBoolean("recorrencia"),
     * rs.getString("diasdasemana"),
     * rs.getInt("usuario_id_usuario1")
     * );
     * listaTarefas.add(tarefa);
     * }
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * 
     * return listaTarefas;
     * }
     */
    /*
     * public List<Tarefa> listarTarefasPorUsuario(int idUsuario) {
     * String sql = "SELECT * FROM tarefa WHERE id_usuario = ?";
     * List<Tarefa> listaTarefas = new ArrayList<>();
     * 
     * try (PreparedStatement stmt = connection.prepareStatement(sql);
     * ResultSet rs = stmt.executeQuery()) {
     * 
     * while (rs.next()) {
     * Tarefa tarefa = new Tarefa(
     * rs.getInt("id_tarefa"),
     * rs.getString("nometarefa"),
     * rs.getString("descricao"),
     * rs.getString("dataprazo"),
     * rs.getString("prioridade"),
     * rs.getString("status"),
     * rs.getString("datacriacao"),
     * rs.getBoolean("recorrencia"),
     * rs.getString("diasdasemana"),
     * rs.getInt("usuario_id_usuario1")
     * );
     * listaTarefas.add(tarefa);
     * }
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * 
     * return listaTarefas;
     * }
     */
}
