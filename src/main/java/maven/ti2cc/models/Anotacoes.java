package maven.ti2cc.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Anotacoes {

    private int idAnotacao;
    private String titulo;
    private String texto;
    private String conteudoResumo;
    private String conteudoTexto;
    private String dataCriacao;
    private int tarefaIdTarefa;

    public Anotacoes() {
    }

    public Anotacoes(int idAnotacao, String titulo, String texto,
            String conteudoResumo, String conteudoTexto, String dataCriacao,
            int tarefaIdTarefa) {
                
        this.idAnotacao = idAnotacao;
        this.titulo = titulo;
        this.texto = texto;
        this.conteudoResumo = conteudoResumo;
        this.conteudoTexto = conteudoTexto;
        this.dataCriacao = dataCriacao;
        this.tarefaIdTarefa = tarefaIdTarefa;
    }

    public Anotacoes(int idAnotacao, String titulo, String texto,
            String conteudoResumo, String conteudoTexto, int tarefaIdTarefa) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        this.idAnotacao = idAnotacao;
        this.titulo = titulo;
        this.texto = texto;
        this.conteudoResumo = conteudoResumo;
        this.conteudoTexto = conteudoTexto;
        this.dataCriacao = now.format(formatter);
        this.tarefaIdTarefa = tarefaIdTarefa;
    }

    // Getters e Setters
    public int getIdAnotacao() {
        return idAnotacao;
    }

    public void setIdAnotacao(int idAnotacao) {
        this.idAnotacao = idAnotacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getConteudoResumo() {
        return conteudoResumo;
    }

    public void setConteudoResumo(String conteudoResumo) {
        this.conteudoResumo = conteudoResumo;
    }

    public String getConteudoTexto() {
        return conteudoTexto;
    }

    public void setConteudoTexto(String conteudoTexto) {
        this.conteudoTexto = conteudoTexto;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        this.dataCriacao = now.format(formatter);
    }

    public int getTarefaIdTarefa() {
        return tarefaIdTarefa;
    }

    public void setTarefaIdTarefa(int tarefaIdTarefa) {
        this.tarefaIdTarefa = tarefaIdTarefa;
    }

    // Método toString
    @Override
    public String toString() {
        return "Anotacoes: " +
                "[idAnotacao: " + idAnotacao +
                ", titulo: " + titulo +
                ", texto: " + texto +
                ", conteudoResumo: " + conteudoResumo +
                ", conteudoTexto: " + conteudoTexto +
                ", dataCriacao: " + dataCriacao +
                ", tarefaIdTarefa: " + tarefaIdTarefa + "]";
    }
}
