package Clientes;

import Productos.VentanaNuevoProductoPanel;

import javax.swing.*;
import java.awt.*;

public class VentanaClienteEditarFrame extends JFrame {

    public VentanaClienteEditarFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Editar Cliente");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(800,350);
        setLocation(470,250);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        VentanaClienteEditarPanel VentanaClienteEditarPanel = new VentanaClienteEditarPanel();
        add(VentanaClienteEditarPanel);
        setVisible(true);
    }
}
