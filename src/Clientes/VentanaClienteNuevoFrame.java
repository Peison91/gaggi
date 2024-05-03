package Clientes;

import javax.swing.*;
import java.awt.*;

public class VentanaClienteNuevoFrame extends JFrame {
    public VentanaClienteNuevoFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Editar clientes");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(800,350);
        setLocation(470,250);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        VentanaClienteNuevoPanel VentanaClienteNuevoPanel = new VentanaClienteNuevoPanel();
        add(VentanaClienteNuevoPanel);
        setVisible(true);
    }
}
