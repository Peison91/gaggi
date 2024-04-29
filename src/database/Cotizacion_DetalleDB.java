package database;

import DTO.DtoCotizacionDetalle;
import model.Cotizacion_detalle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Cotizacion_DetalleDB {

    Connection conn;

    public Cotizacion_DetalleDB(database.DBConection conexion) {
        this.conn = conexion.getConnection();
    }

        public boolean insertarCotizacionDetalle(Cotizacion_detalle cotizacionDetalle)throws Exception{

                try {
                    if (this.conn == null) {
                        throw new Exception("La conexion no esta establecida");
                    } else {
                        String sql = "INSERT INTO cotizacion_detalles (cantidad, precio_unitario, precio_ajustado, producto_id, cotizacion_cabecera_id) VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement statement = conn.prepareStatement(sql);
                        //statement.setInt(1, cotizacionDetalle.getId_cot_detalle());
                        statement.setInt(1, cotizacionDetalle.getCantidad());
                        statement.setDouble(2, cotizacionDetalle.getPrecio_unitario());
                        statement.setDouble(3, cotizacionDetalle.getPrecio_ajustado());
                        statement.setInt(4,cotizacionDetalle.getProducto_id());
                        statement.setInt(5,cotizacionDetalle.getCotizacion_cabecera_id());

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

    public boolean insertarCotizacionDetalleLista(List<Cotizacion_detalle> listaCotizacionDetalle)throws Exception{

        try {
            if (this.conn == null) {
                throw new Exception("La conexion no esta establecida");
            } else {
                String sql = "INSERT INTO cotizacion_detalles (cantidad, precio_unitario, precio_ajustado, producto_id, cotizacion_cabecera_id) VALUES (?, ?, ?, ?, ?)";



                for(Cotizacion_detalle cotizacionDetalle : listaCotizacionDetalle){

                    PreparedStatement statement = conn.prepareStatement(sql);
                    //statement.setInt(1, cotizacionDetalle.getId_cot_detalle());
                    statement.setInt(1, cotizacionDetalle.getCantidad());
                    statement.setDouble(2, cotizacionDetalle.getPrecio_unitario());
                    statement.setDouble(3, cotizacionDetalle.getPrecio_ajustado());
                    statement.setInt(4,cotizacionDetalle.getProducto_id());
                    statement.setInt(5,cotizacionDetalle.getCotizacion_cabecera_id());

                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted > listaCotizacionDetalle.size()) {
                        return true;
                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return false;
    }

    public boolean actualizarCotizacionDetalle(Cotizacion_detalle cotizacionDetalle) throws Exception{
        try{
            if(this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "UPDATE cotizacion_detalles SET id_cot_detalle=?, cantidad=?, precio_unitario=?, precio_ajustado=?, producto_id=?, cotizacion_cabecera_id, WHERE id_cot_detalle=?";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1,cotizacionDetalle.getId_cot_detalle());
                statement.setInt(2,cotizacionDetalle.getCantidad());
                statement.setDouble(3,cotizacionDetalle.getPrecio_unitario());
                statement.setDouble(4,cotizacionDetalle.getPrecio_ajustado());
                statement.setInt(5,cotizacionDetalle.getProducto_id());
                statement.setInt(6,cotizacionDetalle.getCotizacion_cabecera_id());

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

    public boolean borrarCotizacionDetalle(int id)throws Exception{
        try{
            if(this.conn == null) {
                throw new Exception("La conexion no esta establecida");

            }else{
                String sql = "DELETE FROM cotizacion_detalles WHERE id_cot_detalle=?";
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

    public Cotizacion_detalle consultarCotizacionDetalle(int id) throws Exception{
        try{
            if(this.conn == null){
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "SELECT * FROM cotizacion_detalles WHERE id_cot_detalle= " + id;

                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet result = statement.executeQuery(sql);
                if(result.next()){
                    return new Cotizacion_detalle(result.getInt("id_cot_detalle"), result.getInt("cantidad"), result.getDouble("precio_unitario")
                                                 ,result.getDouble("precio_ajustado"), result.getInt("producto_id"), result.getInt("cotizacion_cabecera_id"));
                }
                return  null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
    }





}
