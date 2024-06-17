package database;

import model.Estado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EstadoDB {
    Connection conn;
    public EstadoDB(DBConection conexion){
        this.conn = conexion.getConnection();
    }
    public List<Estado> todosEstados() throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else {
                String sql = "SELECT * FROM estado";
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                List<Estado> listaEstado = new ArrayList<>();

                while (result.next()) {
                    listaEstado.add(new Estado(result.getInt("id_estado"), result.getString("nombre_estado")));
                }
                return listaEstado;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Estado consultaEstado(int id) throws Exception{
        try{
            String sql = "SELECT * FROM estado where id_estado=" + id;

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                return new Estado(result.getInt("id_estado"), result.getString("nombre_estado"));
            }
            return null;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

