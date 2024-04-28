package Proveedores;
import javax.swing.*;
import java.awt.*;

public class VentanaProveedoresNuevoFrame extends JFrame {
    public VentanaProveedoresNuevoFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Nuevo proveedor");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(800,350);
        setLocation(470,250);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        VentanaProveedoresNuevoPanel ventanaProveedoresNuevoPanel = new VentanaProveedoresNuevoPanel();
        add(ventanaProveedoresNuevoPanel);
        setVisible(true);
    }
}
