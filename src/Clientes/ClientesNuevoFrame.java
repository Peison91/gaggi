package Clientes;
import javax.swing.*;
import java.awt.*;

public class ClientesNuevoFrame extends JFrame {
    public ClientesNuevoFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Nuevo cliente");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.50), (int) (alturaPantalla/1.16));
        setLocation(200, 40);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ClientesNuevo clientes = new ClientesNuevo();
        add(clientes);
        setVisible(true);
    }
}
