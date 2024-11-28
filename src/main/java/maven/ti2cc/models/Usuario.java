package maven.ti2cc.models;

public class Usuario {

    private int id_usuario;
    private String username;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
        this.id_usuario = -1;
        this.username = "";
        this.nome = "";
        this.email = "";
        this.senha = "";
    }

    // Construtor
    public Usuario(String username,String nome, String email, String senha) {
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int id,String username,String nome, String email, String senha) {
        this.id_usuario = id;
        this.username = username;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }


    // Getters e Setters
    public int getIdUsuario() {
        return id_usuario;
    }

    public String getUsername(){
        return this.username;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString(){
        return "Usuario: " +
                "[id_usuario: " + id_usuario +
                ", nome: " + nome +
                ", email: " + email +
                ", senha: " + senha + "]";
    }
}
