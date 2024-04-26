package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Cotizacion {
    private int id_cabecera;
    private int cliente_id;
    private Date fecha_cotizacion;
    private int indice_ajuste;
    private int estado;
    private List<Cotizacion_detalle> listCotizacionDetalle = new ArrayList<>();

    public Cotizacion() {
    }

    public Cotizacion(int id_cabecera, int cliente_id, Date fecha_cotizacion, int indice_ajuste, int estado, List<Cotizacion_detalle> listCotizacionDetalle) {
        this.id_cabecera = id_cabecera;
        this.cliente_id = cliente_id;
        this.fecha_cotizacion = fecha_cotizacion;
        this.indice_ajuste = indice_ajuste;
        this.estado = estado;
        this.listCotizacionDetalle = listCotizacionDetalle;
    }

    //CONSTRUCTOR DE PRUEBA.
    public Cotizacion(int id_cabecera,int cliente_id, Date fecha_cotizacion, int indice_ajuste, int estado) {
        this.cliente_id = cliente_id;
        this.fecha_cotizacion = fecha_cotizacion;
        this.indice_ajuste = indice_ajuste;
        this.estado = estado;
    }


        public void ingresarDetalle(Cotizacion_detalle cotizacionDetalle){
          listCotizacionDetalle.add(cotizacionDetalle);

    }

    public int getId_cabecera() {
        return id_cabecera;
    }

    public void setId_cabecera(int id_cabecera) {
        this.id_cabecera = id_cabecera;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Date getFecha_cotizacion() {
        return  fecha_cotizacion;
    }

    public void setFecha_cotizacion(Date fecha_cotizacion) {
        this.fecha_cotizacion = fecha_cotizacion;
    }

    public int getIndice_ajuste() {
        return indice_ajuste;
    }

    public void setIndice_ajuste(int indice_ajuste) {
        this.indice_ajuste = indice_ajuste;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Cotizacion_detalle> getListCotizacionDetalle() {
        return listCotizacionDetalle;
    }

    public void setListCotizacionDetalle(List<Cotizacion_detalle> listCotizacionDetalle) {
        this.listCotizacionDetalle = listCotizacionDetalle;
    }
}
