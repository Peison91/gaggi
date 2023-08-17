package Proveedores;

import Clientes.ClientesEditar;

import javax.swing.*;
import java.awt.*;

public class ProveedoresEditarFrame extends JFrame {
    public ProveedoresEditarFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Editar Proveedores");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.51), (int) (alturaPantalla/1.51));
        setLocation((int) (anchoPantalla/7.45), (int) (alturaPantalla/7.45));
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ProveedoresEditar proveedoresEditar = new ProveedoresEditar();
        add(proveedoresEditar);
        setVisible(true);
    }
}
