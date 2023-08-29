import Utiles.Conexion;
import com.gaggi.database.DBConection;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        Frame ventanaInicial = new Frame();
        ventanaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Conexion.conectar();
    }
}