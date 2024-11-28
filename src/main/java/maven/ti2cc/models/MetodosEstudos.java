package maven.ti2cc.models;

public class MetodosEstudos {

        private int id_metodo;
        private String nomemetodo;
        private String descricaometodo;

        public MetodosEstudos(int id_metodo, String nomemetodo, String descricaometodo) {
            this.id_metodo = id_metodo;
            this.nomemetodo = nomemetodo;
            this.descricaometodo = descricaometodo;
        }

        public int getId_metodo() {
            return id_metodo;
        }

        // Setter para id_metodo
        public void setId_metodo(int id_metodo) {
            this.id_metodo = id_metodo;
        }

        // Getter para nomemetodo
        public String getNomeMetodo() {
            return nomemetodo;
        }

        // Setter para nomemetodo
        public void setNomeMetodo(String nomemetodo) {
            this.nomemetodo = nomemetodo;
        }

        // Getter para descricaometodo
        public String getDescricaoMetodo() {
            return descricaometodo;
        }

        // Setter para descricaometodo
        public void setDescricao(String descricaometodo) {
            this.descricaometodo = descricaometodo;
        }

    }

