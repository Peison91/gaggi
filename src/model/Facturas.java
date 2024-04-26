package model;

import java.util.*;

public class Facturas {
    int id;
    int cliente_id;
    String numero;
    Date fecha_hora;
    double monto;
    String archivo;

    public Facturas(){

    }

    public Facturas(int id, int cliente_id, String numero, Date fecha_hora, double monto, String archivo){
        this.id = id;
        this.archivo = archivo;
        this.fecha_hora = fecha_hora;
        this.cliente_id = cliente_id;
        this.numero = numero;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public java.util.Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
}
