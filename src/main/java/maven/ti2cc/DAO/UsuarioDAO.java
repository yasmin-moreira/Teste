package maven.ti2cc.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import maven.ti2cc.models.Usuario;

public class UsuarioDAO extends DAO {

    public UsuarioDAO() {
        super();
        connect();
    }

    public void finish() {
        super.close();
    }


    public Usuario getUser(String nome, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE username = ? AND senha = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {    
                    Usuario usuario = new Usuario();
                    usuario.setId_usuario(rs.getInt("id_usuario"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setUsername(rs.getString("username"));
                    // Adicione outros campos conforme necessário
                    return usuario;
                }
            }
        }
        return null; // Retorna null se o usuário não for encontrado
    }
    

    public Usuario insert(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, username) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getSenha());
            statement.setString(4, usuario.getUsername());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    usuario.setId_usuario(id); // Atualiza o id do usuário
                    return usuario; // Retorna o usuário com o id gerado
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir usuário: " + e.getMessage());
        }

        return usuario; // Retornar o objeto Usuario com o ID atualizado
    }

    // Método para buscar um usuário por ID
    public Usuario findById(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?"; // Usando placeholder
        Usuario usuario = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Definindo o valor do placeholder
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
              

                usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log de erro
        }
        return usuario; // Retorna o objeto Usuario ou null se não encontrado
    }


    // Método para atualizar um usuário
    public boolean updateUser(Usuario usuario, int id) {

        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, datacadastro = ? WHERE id_usuario = " + id;


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            //stmt.setInt(5, usuario.getId_usuario());

            int affectedRows = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + affectedRows); // Log do número de linhas afetadas

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para deletar um usuário
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?"; // Usando placeholder

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Definindo o valor do placeholder
            int rowsAffected = stmt.executeUpdate(); // Execute the update

            return rowsAffected > 0; // Retorna verdadeiro se uma linha foi afetada
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Método para listar todos os usuários
  /*  public List<Usuario> listUsers() {

        String sql = "SELECT * FROM usuario";
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {


                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"));
                        listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaUsuarios;
    } */

}
