package Cotizacion;

import javax.swing.*;
import java.awt.*;

public class FramaTablaProductosModificiar extends JFrame {

    public FramaTablaProductosModificiar() throws Exception {

        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        int alturaPantalla = tamanoPantalla.height;
        int anchoPantalla = tamanoPantalla.width;
        setSize(anchoPantalla / 2, alturaPantalla / 2);
        setLocation(anchoPantalla / 4, alturaPantalla / 4);
        setTitle("Selección de productos");
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        PanelTablaProductosModificar panelTablaPrductosModificiar = new PanelTablaProductosModificar();
        add(panelTablaPrductosModificiar);
        setVisible(true);
    }
}