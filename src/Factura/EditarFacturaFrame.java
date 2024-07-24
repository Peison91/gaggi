package Factura;

import javax.swing.*;
import java.awt.*;

public class EditarFacturaFrame extends JFrame {
    public EditarFacturaFrame() throws HeadlessException {
        setTitle("Gaggi Distribuidora - Editar factura");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize(400,350);
        setLocation(470,250);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        PanelEditarFactura panel = new PanelEditarFactura();
        add(panel);
        setVisible(true);
    }
}
