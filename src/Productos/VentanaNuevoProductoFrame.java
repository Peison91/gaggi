package Productos;
import javax.swing.*;
import java.awt.*;

public class VentanaNuevoProductoFrame extends JFrame{

    public VentanaNuevoProductoFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Nuevo producto");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(800,350);
        setLocation(470,250);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        VentanaNuevoProductoPanel ventanaNuevoProductoPanel = new VentanaNuevoProductoPanel();
        add(ventanaNuevoProductoPanel);
        setVisible(true);
    }

}
