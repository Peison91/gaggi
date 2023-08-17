package Proveedores;
import javax.swing.*;
import java.awt.*;

public class ProveedoresNuevoFrame extends JFrame {
    public ProveedoresNuevoFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Nuevo proveedor");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.50), (int) (alturaPantalla/1.16));
        setLocation(200, 40);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ProveedoresNuevo proveedoresNuevo = new ProveedoresNuevo();
        add(proveedoresNuevo);
        setVisible(true);
    }
}
