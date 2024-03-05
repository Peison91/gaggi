import Clientes.ClientesEditarFrame;
import Factura.FacturaFrame;
import Productos.ProductosEditarFrame;
import Productos.ProductosImportarFrame;
import Proveedores.ProveedoresEditarFrame;
import Proveedores.ProveedoresNuevoFrame;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private FacturaFrame facturaFrame;
    private ClientesEditarFrame clientesEditarFrame;
    private ProductosEditarFrame productosEditarFrame;
    private ProductosImportarFrame productosImportarFrame;
    private ProveedoresNuevoFrame proveedoresNuevoFrame;
    private ProveedoresEditarFrame proveedoresEditarFrame;
     Panel() {
        setLayout((new FlowLayout(FlowLayout.LEFT)));
        setBackground(new Color(214,214,214));
        JMenuBar barra = new JMenuBar();
        JMenu clientes = new JMenu("Clientes");
        clientes.setIcon(new ImageIcon("src/imagenes/Clientes.png"));
        clientes.setPreferredSize(new Dimension(115,30));
        JMenu productos = new JMenu("Productos");
        productos.setIcon(new ImageIcon("src/imagenes/producto.png"));
        productos.setPreferredSize(new Dimension(115,30));
        JMenu proveedor = new JMenu("Proveedores");
        proveedor.setIcon(new ImageIcon("src/imagenes/Carrito-de-compras.png"));
        proveedor.setPreferredSize(new Dimension(115,30));
        JMenu ventas = new JMenu("Ventas");
        ventas.setIcon(new ImageIcon("src/imagenes/pdf.png"));
        ventas.setPreferredSize(new Dimension(115,30));
        JMenuItem nuevaVenta = new JMenuItem("Nueva venta");
        nuevaVenta.setPreferredSize(new Dimension(150,20));
        nuevaVenta.addActionListener(e->{
            try {
                if(facturaFrame == null){
                    facturaFrame = new FacturaFrame();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            facturaFrame.setVisible(true);
        });

        JMenuItem editarProducto = new JMenuItem("Mi Stock");
        editarProducto.setPreferredSize(new Dimension(150,20));
        editarProducto.addActionListener(e -> {
            try {
                productosEditarFrame = new ProductosEditarFrame();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            productosEditarFrame.setVisible(true);
        });
        JMenuItem importarProductos = new JMenuItem("Importar por archivo");
        importarProductos.setPreferredSize(new Dimension(150,20));
        importarProductos.addActionListener(e ->{
            try {
                productosImportarFrame = new ProductosImportarFrame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            productosImportarFrame.setVisible(true);
        });

        JMenuItem editarCliente = new JMenuItem("Mis Clientes");
        editarCliente.setPreferredSize(new Dimension(150,20));
        editarCliente.addActionListener(e ->{
            try {
                if(clientesEditarFrame == null){
                    clientesEditarFrame = new ClientesEditarFrame();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            clientesEditarFrame.setVisible(true);
        });
        JMenuItem nuevoProveedor = new JMenuItem("Nuevo");
        nuevoProveedor.setPreferredSize(new Dimension(150,20));
        nuevoProveedor.addActionListener(e->{
            try{
                if(proveedoresNuevoFrame == null) {
                    proveedoresNuevoFrame = new ProveedoresNuevoFrame();
                    proveedoresNuevoFrame.setVisible(true);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });
        JMenuItem editarProveedor = new JMenuItem("Editar");
        editarProveedor.setPreferredSize(new Dimension(150,20));
        editarProveedor.addActionListener(e->{
            try{
                if(proveedoresEditarFrame == null) {
                    proveedoresEditarFrame = new ProveedoresEditarFrame();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            proveedoresEditarFrame.setVisible(true);
        });
        barra.add(clientes);
        barra.add(productos);
        barra.add(proveedor);
        barra.add(ventas);
        productos.add(editarProducto);
        productos.add(importarProductos);
        clientes.add(editarCliente);
        proveedor.add(nuevoProveedor);
        proveedor.add(editarProveedor);
        ventas.add(nuevaVenta);
        add(barra);
    }
}
class Panel2 extends JPanel {
    public Panel2() {
        setLayout((new FlowLayout(FlowLayout.RIGHT)));
        setBackground(new Color(214,214,214));

    }
}
class PanelFondo extends JPanel{
    private Image fondo;
    public PanelFondo(){
        setLayout(new FlowLayout(FlowLayout.CENTER));
    }
    public void paintComponent(Graphics g) {
        int width = this.getSize().width;
        int height = this.getSize().height;
        if (this.fondo != null) {
            g.drawImage(this.fondo, 0, 0, width, height, null);
        }
        super.paintComponent(g);
    }
    public void setBackground(String imagePath) {
        this.setOpaque(false);
        this.fondo = new ImageIcon(imagePath).getImage();
        repaint();
    }
}
