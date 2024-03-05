package Factura;

import javax.swing.*;
import java.awt.*;

public class FrameTablaClientes extends JFrame {

    public FrameTablaClientes() throws Exception {

        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        int alturaPantalla = tamanoPantalla.height;
        int anchoPantalla = tamanoPantalla.width;
        setSize(anchoPantalla / 2, alturaPantalla / 2);
        setLocation(anchoPantalla / 4, alturaPantalla / 4);
        setTitle("Clientes");
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        PanelTablaClientes panelTablaClientes = new PanelTablaClientes();
        add(panelTablaClientes);
        setVisible(true);
    }
}
