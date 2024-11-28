package maven.ti2cc.DAO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAO {

    protected Connection connection;

    public DAO(){
        connection = null;
    }

    public boolean connect () {


        // Pega dados do banco de dados remoto
        String driverName = "org.postgresql.Driver";
        String serverName = "clock-server.postgres.database.azure.com";
        String myDataBase = "postgres";

        // Porta de conexão com o banco de dados remoto
        int port = 5432;

        // Conecta com o postgresql
        String url = "jdbc:postgresql://" +serverName+ ":" +port + "/" +myDataBase;
        String user = "yasminADM";
        String password = "@clock2024";

        boolean status = false;

        try {

            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
            status = (connection == null);
            System.out.println("Conectado com sucesso.");
        }

        catch (ClassNotFoundException e) {
            System.err.println("Conexão não efetuada.");
        }

        catch (SQLException e) {
            System.err.println("Erro ao conectar com o postgres database.");
            e.printStackTrace();
        }

        return status;

    }

    public boolean close () {

        boolean status = false;

        try {
            this.connection.close();
            status = true;
        }

        catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return status;
    }

    public static String toMD5 (String password) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes(), 0, password.length());

        return new BigInteger(1, md.digest()).toString(16);
    }
}
