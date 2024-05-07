package database;

import model.Facturas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturasDB {
    Connection conn;

    public FacturasDB(DBConection conexion){
        this.conn = conexion.getConnection();
    }

    public boolean insertarFacturas(Facturas factura) throws Exception {
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{

                String sql = "INSERT INTO facturas (cliente_id, numero, fecha_hora, monto, archivo) VALUES (?, ?, ?, ?, ?)";

                Timestamp timestamp = new Timestamp(factura.getFecha_hora().getTime());

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, factura.getCliente_id());
                statement.setString(2, factura.getNumero());
                statement.setTimestamp(3, timestamp);
                statement.setDouble (4, factura.getMonto());
                statement.setString(5, factura.getArchivo());

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

    public boolean actualizarFacturas(Facturas factura) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "UPDATE facturas SET cliente_id=?, numero=?, fecha_hora=?, monto=?, archivo=? WHERE id=?";

                Timestamp timestamp = new Timestamp(factura.getFecha_hora().getTime());

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, factura.getCliente_id());
                statement.setString(2, factura.getNumero());
                statement.setTimestamp(3, timestamp);
                statement.setDouble(4, factura.getMonto());
                statement.setString(5, factura.getArchivo());
                statement.setInt(6, factura.getId());

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

    public List<Facturas> todasFacturas() throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else {
                String sql = "SELECT * FROM facturas";

                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);

                List<model.Facturas> lstFacturas = new ArrayList<Facturas>();

                while (result.next()) {
                    lstFacturas.add(new Facturas(result.getInt("id"), result.getInt("cliente_id"),
                            result.getString("numero"), result.getTimestamp("fecha_hora"),
                            result.getDouble("monto"), result.getString("archivo")));
                }
                return lstFacturas;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Facturas consultaFacturas(int id) throws Exception{
        try{
            String sql = "SELECT * FROM facturas where id=" + id;

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery(sql);
            if(result.next()){
                return new Facturas(result.getInt("id"), result.getInt("cliente_id"),
                        result.getString("numero"), result.getTimestamp("fecha_hora"),
                        result.getDouble("monto"), result.getString("archivo"));
            }
            return null;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public boolean borrarFactura(int id) throws Exception{
        try{
            if (this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "DELETE FROM facturas WHERE id=?";

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