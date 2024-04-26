package database;

import model.Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductosDB {
    Connection conn;

    public ProductosDB(database.DBConection conexion){
        this.conn = conexion.getConnection();
    }

    public boolean insertarProducto(Productos producto) throws Exception {
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "INSERT INTO productos (descripcion, codigo, abreviatura, precio, stock_minimo, stock) VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, producto.getDescripcion());
                statement.setString(2, producto.getCodigo());
                statement.setString(3, producto.getAbreviatura());
                statement.setDouble(4, producto.getPrecio());
                statement.setDouble(5, producto.getStock_minimo());
                statement.setInt(6, producto.getStock());

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

    public boolean actualizarProducto(Productos producto) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "UPDATE productos SET descripcion=?, codigo=?, abreviatura=?, precio=?, stock_minimo=?, stock=? WHERE id=?";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, producto.getDescripcion());
                statement.setString(2, producto.getCodigo());
                statement.setString(3, producto.getAbreviatura());
                statement.setDouble(4, producto.getPrecio());
                statement.setInt(5, producto.getStock_minimo());
                statement.setInt(6, producto.getStock());
                statement.setInt(7, producto.getId());

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

    public List<Productos> todosProductos() throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else {
                String sql = "SELECT * FROM productos";

                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                List<Productos> lstProductos = new ArrayList<Productos>();

                while (result.next()) {
                    lstProductos.add(new Productos(result.getInt("id"),result.getString("descripcion"),
                            result.getString("codigo"), result.getString("abreviatura"),
                            result.getDouble("precio"), result.getInt("stock_minimo"),
                            result.getInt("stock")));
                }
                return lstProductos;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Productos consultaProducto(int id) throws Exception{
        try{
            String sql = "SELECT * FROM productos where id=" + id;

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                return new Productos(result.getInt("id"), result.getString("descripcion"), result.getString("codigo"),
                            result.getString("abreviatura"), result.getDouble("precio"), result.getInt("stock_minimo"),
                            result.getInt("stock"));
            }
            return null;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Productos consultaProductoCodigo(String codigo) throws Exception{
        try{
            String sql = "SELECT * FROM productos where codigo=" + codigo;

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                return new Productos(result.getInt("id"), result.getString("descripcion"), result.getString("codigo"),
                        result.getString("abreviatura"), result.getDouble("precio"), result.getInt("stock_minimo"),
                        result.getInt("stock"));
            }
            return null;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public boolean borrarProducto(int id) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "DELETE FROM productos WHERE id=?";

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
