package Utiles;

import database.DBConection;

public class Conexion {
//Cuando clonen el proyecto tienen que cambiar los datos de la conexión que ustedes tengan en su base.
public static DBConection conecc = new DBConection("localhost","root","");
    public Conexion(){
    }
    public static DBConection conectar(){
        conecc.conectar();
        return conecc;
    }
}

