package Cotizacion;

import javax.swing.*;
import java.awt.*;

public class FrameTablaClientesCotizacion extends JFrame {

    public FrameTablaClientesCotizacion() throws Exception {

        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        int alturaPantalla = tamanoPantalla.height;
        int anchoPantalla = tamanoPantalla.width;
        setSize(anchoPantalla / 2, alturaPantalla / 2);
        setLocation(anchoPantalla / 4, alturaPantalla / 4);
        setTitle("Clientes");
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        PanelTablaClientesCotizacion panelTablaClientes = new PanelTablaClientesCotizacion();
        add(panelTablaClientes);
        setVisible(true);
    }
}
