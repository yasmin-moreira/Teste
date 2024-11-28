package maven.ti2cc.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import maven.ti2cc.models.MetodosEstudos;

public class MetodosEstudosDAO extends DAO {

    public MetodosEstudosDAO() {
        super();
        connect();
    }

    public void finish() {
        super.close();
    }

    // Método para inserir um novo método de estudo
    public MetodosEstudos insert(MetodosEstudos metodo) throws SQLException {
        String sql = "INSERT INTO metodos_de_estudos (nomemetodo, descricaometodo) VALUES (?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, metodo.getNomeMetodo());
            statement.setString(2, metodo.getDescricaoMetodo());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    metodo.setId_metodo(id);
                    return metodo;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir método de estudo: " + e.getMessage());
        }

        return metodo;
    }

    // Método para buscar um método de estudo por ID
    public MetodosEstudos findById(int id) {
        String sql = "SELECT * FROM metodos_de_estudos WHERE id_metodo = ?";
        MetodosEstudos metodo = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                metodo = new MetodosEstudos(
                        rs.getInt("id_metodo"),
                        rs.getString("nomemetodo"),
                        rs.getString("descricaometodo"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log de erro
        }
        return metodo;
    }

    // Método para atualizar um método de estudo
    public boolean updateMetodo(MetodosEstudos metodo, int id) {
        String sql = "UPDATE metodos_de_estudos SET nomemetodo = ?, descricaometodo = ? WHERE id_metodo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, metodo.getNomeMetodo());
            stmt.setString(2, metodo.getDescricaoMetodo());
            stmt.setInt(3, id);

            int affectedRows = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + affectedRows);

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para deletar um método de estudo
    public boolean deleteMetodo(int id) {
        String sql = "DELETE FROM metodos_de_estudos WHERE id_metodo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos os métodos de estudo
    public List<MetodosEstudos> listMetodos() {
        String sql = "SELECT * FROM metodos_de_estudos";
        List<MetodosEstudos> listaMetodos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MetodosEstudos metodo = new MetodosEstudos(
                        rs.getInt("id_metodo"),
                        rs.getString("nomemetodo"),
                        rs.getString("descricaometodo"));

                listaMetodos.add(metodo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaMetodos;
    }
}
