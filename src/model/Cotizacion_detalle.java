package model;

public class Cotizacion_detalle {
    private int id_cot_detalle;
    private int cantidad;
    private double precio_unitario;
    private double precio_ajustado;
    private int producto_id;
    private int cotizacion_cabecera_id;

    public Cotizacion_detalle() {
    }

    public Cotizacion_detalle(int id_cot_detalle, int cantidad, double precio_unitario, double precio_ajustado,int producto_id, int cotizacion_cabecera_id) {
        this.id_cot_detalle = id_cot_detalle;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.precio_ajustado = precio_ajustado;
        this.producto_id = producto_id;
        this.cotizacion_cabecera_id = cotizacion_cabecera_id;
    }



    public int getId_cot_detalle() {
        return id_cot_detalle;
    }

    public void setId_cot_detalle(int id_cot_detalle) {
        this.id_cot_detalle = id_cot_detalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getPrecio_ajustado() {
        return precio_ajustado;
    }

    public void setPrecio_ajustado(double precio_ajustado) {
        this.precio_ajustado = precio_ajustado;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getCotizacion_cabecera_id() {
        return cotizacion_cabecera_id;
    }

    public void setCotizacion_cabecera_id(int cotizacion_cabecera_id) {
        this.cotizacion_cabecera_id = cotizacion_cabecera_id;
    }
}
