package maven.ti2cc.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import maven.ti2cc.models.Anotacoes;

public class AnotacoesDAO extends DAO {

    public AnotacoesDAO() {
        super();
        connect();
    }

    public void finish() {
        super.close();
    }

    // Método para inserir uma nova anotação
    public Anotacoes insert(Anotacoes anotacao) throws SQLException {
        String sql = "INSERT INTO anotacoes (titulo, texto, conteudoResumo, conteudoTexto, dataCriacao, TAREFA_ID_TAREFA) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, anotacao.getTitulo());
            statement.setString(2, anotacao.getTexto());
            statement.setString(3, anotacao.getConteudoResumo());
            statement.setString(4, anotacao.getConteudoTexto());
            statement.setString(5, anotacao.getDataCriacao());
            statement.setInt(6, anotacao.getTarefaIdTarefa());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    anotacao.setIdAnotacao(id);
                    return anotacao;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir anotação: " + e.getMessage());
        }

        return anotacao;
    }


    // Método para deletar uma anotação
    public boolean deleteAnotacao(int id) {
        String sql = "DELETE FROM anotacoes WHERE ID_Anotacao = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

// Método para listar todas as anotações de um usuário específico
public ArrayList<Anotacoes> listAnotacoesPorUsuario(int usuarioId) {
    String sql = "SELECT a.* "
    + "FROM anotacoes a "
    + "JOIN tarefa t ON a.tarefa_id_tarefa = t.id_tarefa "
    + "JOIN usuario u ON t.usuario_id_usuario1 = u.id_usuario "
    + "WHERE u.id_usuario = ?;";


    ArrayList<Anotacoes> listaAnotacoes = new ArrayList<>();

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId); // Define o ID do usuário no parâmetro da query
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Anotacoes anotacao = new Anotacoes(
                        rs.getInt("id_anotacao"),
                        rs.getString("titulo"),
                        rs.getString("texto"),
                        rs.getString("conteudoresumo"),
                        rs.getString("conteudotexto"),
                        rs.getString("datacriacao"),
                        rs.getInt("tarefa_id_tarefa"));

                listaAnotacoes.add(anotacao);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return listaAnotacoes;
}

}
