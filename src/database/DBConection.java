package database;

import java.sql.*;

public class DBConection   {
    String url = "jdbc:mysql://localhost/gaggidb";
    String user= "root";
    String passwd= "rootroot";
    Connection conn;

    public DBConection(String servidor, String usuario, String contrasenia) {
        this.url = "jdbc:mysql://" + servidor + "/gaggidb";
        this.user = usuario;
        this.passwd = contrasenia;
    }

    public boolean conectar(){
        try {
            // 1. Carga el controlador de acceso a datos
            //Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. Conecta a la base de datos
            conn = DriverManager.getConnection(url, user, passwd);
            if (conn != null) {
                return true;
            }
            return false;

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            return false;
        }
    }

    public Connection getConnection(){
        return conn;
    }
}
