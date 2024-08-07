package Productos;
import javax.swing.*;
import java.awt.*;

public class VentanaEditarProductoFrame extends JFrame {
        public VentanaEditarProductoFrame() throws Exception {
            setTitle("Gaggi Distribuidora - Editar producto");
            Toolkit miPantalla = Toolkit.getDefaultToolkit();
            miPantalla.getScreenSize();
            Dimension tamanoPantalla = miPantalla.getScreenSize();
            double alturaPantalla = tamanoPantalla.height;
            double anchoPantalla = tamanoPantalla.width;
            setSize(400,400);
            setLocation(470,250);
            Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
            setIconImage(icono);
            VentanaEditarProductoPanel productos = new VentanaEditarProductoPanel();
            add(productos);
            setVisible(true);
        }
}
