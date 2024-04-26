package database;
import model.Cotizacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


public class Cotizacion_CabeceraDB {

    Connection conn;

    public Cotizacion_CabeceraDB(database.DBConection conexion) {
        this.conn = conexion.getConnection();
    }

    public boolean insertarCotizacion(Cotizacion cotizacion) throws Exception {
        try {
            if (this.conn == null) {
                throw new Exception("La conexion no esta establecida");
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
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "UPDATE cotizacion_cabecera SET cliente_id=?, fecha_cotizacion=?, indice_ajuste=?, estado_id=?, WHERE id_cabecera=?";
                Timestamp timestamp = new Timestamp(cotizacion.getFecha_cotizacion().getTime());

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1,cotizacion.getCliente_id());
                statement.setTimestamp(2,timestamp);
                statement.setInt(3,cotizacion.getIndice_ajuste());
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
                throw new Exception("La conexion no esta establecida");

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
                throw new Exception("La conexion no esta establecida");
            }else{
                String sql = "SELECT * FROM cotizacion_cabecera WHERE id_cabecera= " + id;

                PreparedStatement statement = conn.prepareStatement(sql);

                ResultSet result = statement.executeQuery(sql);
                if(result.next()){
                    return new Cotizacion(result.getInt("id_cabecera"),result.getInt("cliente_id"),result.getTimestamp("fecha_cotizacion")
                            ,result.getInt("indice_ajuste"),result.getInt("estado"));
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
                throw new Exception("La conexion no esta establecida");
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







}