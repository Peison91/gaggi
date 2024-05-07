package Clientes;

import javax.swing.*;
import java.awt.*;

public class ClientesEditarFrame extends JFrame {
    public ClientesEditarFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Editar clientes");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.51), (int) (alturaPantalla/1.5));
        setLocationRelativeTo(null);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ClientesEditar clientesEditar = new ClientesEditar();
        add(clientesEditar);
        setVisible(true);
    }
}
