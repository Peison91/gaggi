import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() throws HeadlessException {
        setTitle("Gaggi Distribuidora - Menú Principal");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.33), (int) (alturaPantalla/1.33));
        setLocation((int) (anchoPantalla/7.0), (int) (alturaPantalla/7.0));
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Panel panel = new Panel();
        add(panel, BorderLayout.NORTH);
        Panel2 panel2 = new Panel2();
        add(panel2, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PanelFondo fondo = new PanelFondo();
        fondo.setBackground("src/imagenes/fondo.jpg");
        add(fondo);
        setVisible(true);
    }
}
