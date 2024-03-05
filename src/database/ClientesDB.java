package com.gaggi.database;
import com.gaggi.model.*;
import com.gaggi.model.Clientes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesDB {
    Connection conn;

    public ClientesDB(DBConection conexion){
        this.conn = conexion.getConnection();
    }

    public boolean insertarCliente(Clientes cliente) throws Exception {
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "INSERT INTO clientes (nombre, cuit, direccion, email, telefono) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, cliente.getNombre());
                statement.setString(2, cliente.getCuit());
                statement.setString(3, cliente.getDireccion());
                statement.setString(4, cliente.getEmail());
                statement.setString(5, cliente.getTelefono());

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

    public boolean actualizarCliente(Clientes cliente) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "UPDATE clientes SET nombre=?, cuit=?, direccion=?, email=?, telefono=? WHERE id=?";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, cliente.getNombre());
                statement.setString(2, cliente.getCuit());
                statement.setString(3, cliente.getDireccion());
                statement.setString(4, cliente.getEmail());
                statement.setString(5, cliente.getTelefono());
                statement.setInt(6, cliente.getId());

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

    public List<Clientes> todosClientes() throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else {
                String sql = "SELECT * FROM clientes";

                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                List<com.gaggi.model.Clientes> lstClientes = new ArrayList<Clientes>();

                while (result.next()) {
                    lstClientes.add(new Clientes(result.getInt("id"), result.getString("nombre"),
                            result.getString("cuit"), result.getString("direccion"),
                            result.getString("email"), result.getString("telefono")));
                }
                return lstClientes;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Clientes consultaCliente(int id) throws Exception{
        try{
            String sql = "SELECT * FROM clientes where id=" + id;

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                return new Clientes(result.getInt("id"), result.getString("nombre"),result.getString("cuit"),
                        result.getString("direccion"),result.getString("email"),
                        result.getString("telefono"));
            }
            return null;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public boolean borrarCliente(int id) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "DELETE FROM clientes WHERE id=?";

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
