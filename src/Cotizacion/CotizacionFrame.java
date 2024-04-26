package Cotizacion;

import Factura.PanelFactura;

import javax.swing.*;
import java.awt.*;

public class CotizacionFrame extends JFrame {

    public CotizacionFrame() throws Exception {

        setTitle("Cotizacion");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.62), (int) (alturaPantalla/1.16));
        setLocation(200, 40);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        CotizacionPanel cotizacionPanel = new CotizacionPanel();
        add(cotizacionPanel);
        setVisible(true);
    }

}
