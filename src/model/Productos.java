package com.gaggi.model;

public class Productos {
    int id;
    String descripcion;
    String codigo;
    String abreviatura;
    double precio;
    int stock_minimo;
    int stock;


    public Productos(){

    }

    public Productos(int id, String descripcion, String codigo, String abreviatura, double precio, int stock_minimo, int sotck){
        this.id = id;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.abreviatura = abreviatura;
        this.precio = precio;
        this.stock_minimo = stock_minimo;
        this.stock = sotck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
