import Utiles.Conexion;
import javax.swing.*;


public class Main {
    public static void main(String[] args) throws Exception {
        Frame ventanaInicial = new Frame();
        ventanaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Conexion.conectar();
    }
}