package Factura;
import javax.swing.*;
import java.awt.*;

public class FacturaFrame extends JFrame {

    public FacturaFrame() throws Exception {

        setTitle("Facturas");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.62), (int) (alturaPantalla/1.16));
        setLocation(200, 40);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        PanelFactura panelFactura = new PanelFactura();
        add(panelFactura);
        setVisible(true);
    }
}
