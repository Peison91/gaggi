package Productos;

import javax.swing.*;
import java.awt.*;

public class ProductosImportarFrame extends JFrame {
    public ProductosImportarFrame(){
        setTitle("Gaggi Distribuidora - Importar productos");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(400,250);
        setLocation(470,250);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ProductosImportar productos = new ProductosImportar();
        add(productos);
        setVisible(true);
    }
}
