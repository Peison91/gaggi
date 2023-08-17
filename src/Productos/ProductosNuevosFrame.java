package Productos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProductosNuevosFrame extends JFrame {
    public ProductosNuevosFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Nuevo Producto");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.5), (int) (alturaPantalla/1.33));
        setLocation((int) (anchoPantalla/7.45), (int) (alturaPantalla/7.45));
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ProductosNuevo productos = new ProductosNuevo();
        add(productos);
        setVisible(true);
    }
}
