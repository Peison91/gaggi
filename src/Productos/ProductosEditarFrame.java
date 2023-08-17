package Productos;
import javax.swing.*;
import java.awt.*;

public class ProductosEditarFrame extends JFrame{
    public ProductosEditarFrame() throws Exception {
        setTitle("Gaggi Distribuidora - Editar Productos");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.51), (int) (alturaPantalla/1.5));
        setLocation((int) (anchoPantalla/7.45), (int) (alturaPantalla/7.45));
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        ProductosEditar productos = new ProductosEditar();
        /*JButton btnCerrar = new JButton("Cerrar", new ImageIcon("src/imagenes/eliminar.png"));
        btnCerrar.setBounds(740, 73, 130, 50);
        add(btnCerrar);
        btnCerrar.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });*/
        add(productos);
        setVisible(true);
    }
}
