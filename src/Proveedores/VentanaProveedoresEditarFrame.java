package Proveedores;
import javax.swing.*;
import java.awt.*;

public class VentanaProveedoresEditarFrame extends JFrame {
    public VentanaProveedoresEditarFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Editar proveedor");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(400,350);
        setLocation(470,250);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        VentanaProveedoresEditarPanel VentanaProveedoresEditarPanel = new VentanaProveedoresEditarPanel();
        add(VentanaProveedoresEditarPanel);
        setVisible(true);
    }
}
