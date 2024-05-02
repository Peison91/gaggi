package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConection;
import model.Proveedores;

public class ProveedoresDB {
    Connection conn;

    public ProveedoresDB(DBConection conexion){
        this.conn = conexion.getConnection();
    }

    public boolean insertarProveedores(Proveedores proveedores) throws Exception {
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "INSERT INTO proveedores (nombre, cuit, direccion, ciudad, Cbu_alias) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, proveedores.getNombre());
                statement.setString(2, proveedores.getCuit());
                statement.setString(3, proveedores.getDireccion());
                statement.setString(4, proveedores.getCiudad());
                statement.setString(5, proveedores.getCbu());

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    return true;
                }
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public boolean actualizarProveedores(Proveedores proveedores) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "UPDATE proveedores SET nombre=?, cuit=?, direccion=?, ciudad=?, Cbu_alias=? WHERE id=?";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, proveedores.getNombre());
                statement.setString(2, proveedores.getCuit());
                statement.setString(3, proveedores.getDireccion());
                statement.setString(4, proveedores.getCiudad());
                statement.setString(5, proveedores.getCbu());
                statement.setInt(6, proveedores.getId());

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    return true;
                }
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<Proveedores> todosProveedores() throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else {
                String sql = "SELECT * FROM proveedores";

                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                List<Proveedores> lstProveedores = new ArrayList<Proveedores>();

                while (result.next()) {
                    lstProveedores.add(new Proveedores(result.getInt("id"), result.getString("nombre"),
                            result.getString("cuit"), result.getString("direccion"),
                            result.getString("ciudad"),
                            result.getString("Cbu_alias")));
                }
                return lstProveedores;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Proveedores consultaProveedores(int id) throws Exception{
        try{
            String sql = "SELECT * FROM proveedores where id=" + id;

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                return new Proveedores(result.getInt("id"), result.getString("nombre"), result.getString("cuit"),
                        result.getString("direccion"), result.getString("ciudad"), result.getString("Cbu_alias"));
            }
            return null;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public boolean borrarProveedor(int id) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "DELETE FROM proveedores WHERE id=?";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);

                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    return true;
                }
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
