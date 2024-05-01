package Cotizacion;

import Factura.PanelTablaClientes;

import javax.swing.*;
import java.awt.*;

public class FrameTablaProductosCotizacion extends JFrame {

    public FrameTablaProductosCotizacion() throws Exception {

        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        int alturaPantalla = tamanoPantalla.height;
        int anchoPantalla = tamanoPantalla.width;
        setSize(anchoPantalla / 2, alturaPantalla / 2);
        setLocation(anchoPantalla / 4, alturaPantalla / 4);
        setTitle("Productos");
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        PanelTablaProductosCotizacion panelTablaPrductos = new PanelTablaProductosCotizacion();
        add(panelTablaPrductos);
        setVisible(true);
    }
}
