package DTO;

import java.text.DecimalFormat;

public class DtoCotizacionDetalle {
    private int codigo_producto;
    private int cantidad_producto;
    private String nombre_producto;
    private double precio_unitario;
    private double precio_total;

    public DtoCotizacionDetalle() {
    }

    public DtoCotizacionDetalle(int codigo_producto, int cantidad_producto, String nombre_producto, double precio_unitario, double precio_total) {
        this.codigo_producto = codigo_producto;
        this.cantidad_producto = cantidad_producto;
        this.nombre_producto = nombre_producto;
        this.precio_unitario = precio_unitario;
        this.precio_total = precio_total;
    }

    public int getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(int codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio_unitario() {

        return Math.round(precio_unitario * 100.0) / 100.0;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getPrecio_total() {
        return Math.round(precio_total * 100.0) / 100.0;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }
}
