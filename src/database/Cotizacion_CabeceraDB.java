package database;
import model.Cotizacion;
import model.Cotizacion_detalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cotizacion_CabeceraDB {

    Connection conn;

    public Cotizacion_CabeceraDB(database.DBConection conexion) {
        this.conn = conexion.getConnection();
    }

    public boolean insertarCotizacion(Cotizacion cotizacion) throws Exception {
        try {
            if (this.conn == null) {
                throw new Exception("La conexión no esta establecida");
            } else {
                String sql = "INSERT INTO cotizacion_cabecera(cliente_id, fecha_cotizacion, indice_ajuste, estado_id) " +
                        "VALUES (?, ?, ?, ?)";

                Timestamp timestamp = new Timestamp(cotizacion.getFecha_cotizacion().getTime());

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, cotizacion.getCliente_id());
                statement.setTimestamp(2, timestamp);
                statement.setDouble(3, cotizacion.getIndice_ajuste());
                statement.setInt(4, cotizacion.getEstado());

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

    public boolean actualizarCotizacion(Cotizacion cotizacion) throws Exception{
        try{
            if(this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "UPDATE cotizacion_cabecera SET cliente_id=?, fecha_cotizacion=?, indice_ajuste=?, estado_id=?, WHERE id_cabecera=?";
                Timestamp timestamp = new Timestamp(cotizacion.getFecha_cotizacion().getTime());

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1,cotizacion.getCliente_id());
                statement.setTimestamp(2,timestamp);
                statement.setDouble(3,cotizacion.getIndice_ajuste());
                statement.setInt(4,cotizacion.getEstado());

                int rowsUpdated = statement.executeUpdate();
                if(rowsUpdated > 0){
                    return true;
                }
                return false;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    public boolean borrarCotizacion(int id)throws Exception{
        try{
            if(this.conn == null) {
                throw new Exception("La conexión no esta establecida");

            }else{
                String sql = "DELETE FROM cotizacion_cabecera WHERE id_cabecera=?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setInt(1,id);
                    int rowsDeleted = statement.executeUpdate();

                    if(rowsDeleted > 0){
                        return true;
                    }
                    return false;
                }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
    public Cotizacion consultarCotizacion(int id) throws Exception{
        try{
            if(this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "SELECT * FROM cotizacion_cabecera WHERE id_cabecera= " + id;

                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet result = statement.executeQuery(sql);
                if(result.next()){
                    return new Cotizacion(result.getInt("id_cabecera"),result.getInt("cliente_id"),result.getTimestamp("fecha_cotizacion")
                            ,result.getDouble("indice_ajuste"),result.getInt("estado_id"));
                }
                return  null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
    public Cotizacion_detalle consultarCotizacionDetalle(int id) throws Exception{
        try{
            if(this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "SELECT * FROM cotizacion_detalles WHERE cotizacion_cabecera_id= " + id;

                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet result = statement.executeQuery(sql);
                if(result.next()){
                    return new Cotizacion_detalle(result.getInt("id_cot_detalle"),result.getInt("cantidad"),result.getDouble("precio_unitario")
                            ,result.getDouble("precio_ajustado"),result.getInt("producto_id"));
                }
                return  null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
    public int obtenerIdCabecera() throws Exception{
        int idCabecera;
        try{
            if(this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "SELECT id_cabecera FROM cotizacion_cabecera ORDER BY fecha_cotizacion DESC LIMIT 1;";

                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet result = statement.executeQuery(sql);
                if(result.next())
                return  result.getInt("id_cabecera");
                else
                    return 0;
            }

        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<Cotizacion> todasCotizacionesCab()throws Exception{
        try{
            if(this.conn == null){
                throw new Exception("La conexión no esta establecida");
            }else{
                String sql = "SELECT * FROM cotizacion_cabecera;";

                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                List<Cotizacion> lstCotizacion = new ArrayList<>();

                while(resultSet.next()){
                    lstCotizacion.add(new Cotizacion(resultSet.getInt("id_cabecera"),resultSet.getInt("cliente_id"),
                            resultSet.getTimestamp("fecha_cotizacion"),resultSet.getDouble("indice_ajuste"), resultSet.getInt("estado_id")));
                }
                return  lstCotizacion;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }
}