package model;

public class Clientes {
    int id;
    String nombre;
    String cuit;
    String direccion;
    String telefono;
    String email;

    public Clientes(){

    }

    public Clientes (int id, String nombre, String cuit, String direccion, String email, String telefono ){
        this.id = id;
        this.nombre = nombre;
        this.cuit = cuit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
