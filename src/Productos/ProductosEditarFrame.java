package Productos;
import javax.swing.*;
import java.awt.*;

public class ProductosEditarFrame extends JFrame{
    public ProductosEditarFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Mis productos");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(1000,600);
        setLocation((int) (anchoPantalla/7.45), (int) (alturaPantalla/7.45));
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ProductosEditar productos = new ProductosEditar();
        add(productos);
        setVisible(true);
    }
}
