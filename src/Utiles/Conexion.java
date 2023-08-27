package Utiles;

import com.gaggi.database.DBConection;

public class Conexion {
//Cuando clonen el proyecto tienen que cambiar los datos de la conexion que ustedes tengan en su base.
public static DBConection conecc = new DBConection("localhost","root","selfa");
    public Conexion(){
    }
    public static DBConection conectar(){
        conecc.conectar();
        return conecc;
    }
}

