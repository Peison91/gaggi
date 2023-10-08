package Opciones;

import javax.swing.*;
import java.awt.*;

public class Opciones extends JFrame {
    public Opciones(){
        setTitle("Gaggi Distribuidora - Opciones");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        setBounds(400,100,500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        PanelOpciones panelOpciones = new PanelOpciones();
        add(panelOpciones);
    }


}
