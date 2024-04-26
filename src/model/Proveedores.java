package model;

public class Proveedores {
    private int id;
    private String nombre;
    private String cuit;
    private String direccion;
    private String ciudad;
    private String cbu;

    public Proveedores(){

    }

    public Proveedores(int id, String nombre, String cuit, String direccion, String ciudad, String cbu){
        this.id = id;
        this.nombre = nombre;
        this.cuit = cuit;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.cbu = cbu;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCbu() {return cbu;}

    public void setCbu(String cbu) {this.cbu = cbu;}

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
