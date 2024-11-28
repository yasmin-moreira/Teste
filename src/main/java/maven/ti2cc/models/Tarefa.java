package maven.ti2cc.models;

public class Tarefa {

    private int idTarefa;
    private String nomeTarefa;
    private String dataprazo;
    private int prioridade;
    private String descricao;
    private int status;
    private boolean[] recorrencia;
    private int usuarioIdUsuario1;

    public Tarefa() {
        this.nomeTarefa = "";
        this.descricao = "";
        this.dataprazo = "";
        this.prioridade = 0;
        this.status = -1;
        this.usuarioIdUsuario1 = 0;
    }

    public Tarefa(String nomeTarefa, String descricao, String dataprazo,
            int prioridade, int status, String dataCriacao, String diasDaSemana, int usuarioIdUsuario1) {

        this.nomeTarefa = nomeTarefa;
        this.descricao = descricao;
        this.dataprazo = dataprazo;
        this.prioridade = prioridade;
        this.status = status;
        this.usuarioIdUsuario1 = usuarioIdUsuario1;
    }

    public int getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(int idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataprazo() {
        return dataprazo; // Verifique se o nome do método está correto
    }

    public void setDataprazo(String dataprazo) {
        this.dataprazo = dataprazo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public boolean[] getRecorrencia(){
        return this.recorrencia;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

     public boolean[] isRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(boolean[] recorrencia) {
        this.recorrencia = recorrencia;
    }


    public void setRecorrencia(String recorrencia) {
        recorrencia = recorrencia.replace("[", "").replace("]", "").trim();
    
        String[] partes = recorrencia.split(",");
    
        for (int i = 0; i < 7; i++) {
            this.recorrencia[i] = Boolean.parseBoolean(partes[i].trim());
        }
    }
    

    public int getUsuarioIdUsuario1() {
        return usuarioIdUsuario1;
    }

    public void setUsuarioIdUsuario1(int usuarioIdUsuario1) {
        this.usuarioIdUsuario1 = usuarioIdUsuario1;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
        // "idTarefa=" + this.getIdTarefa() +
                ", nomeTarefa='" + nomeTarefa + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataprazo='" + dataprazo + '\'' +
                ", prioridade='" + prioridade + '\'' +
                ", status='" + status + '\''  +
                ", recorrencia=" + recorrencia + '\'' +
                ", usuarioIdUsuario1=" + usuarioIdUsuario1 +
                '}';
    }
}
