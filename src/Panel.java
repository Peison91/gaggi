import Clientes.ClientesEditarFrame;
import Cotizacion.CotizacionEditarFrame;
import Cotizacion.CotizacionFrame;
import Factura.FacturaFrame;
import Productos.ProductosEditarFrame;
import Productos.ProductosImportarFrame;
import Proveedores.ProveedoresEditarFrame;

import javax.swing.*;
import java.awt.*;


public class Panel extends JPanel {
    private FacturaFrame facturaFrame;
    private ClientesEditarFrame clientesEditarFrame;
    private ProductosEditarFrame productosEditarFrame;
    private ProductosImportarFrame productosImportarFrame;
    private ProveedoresEditarFrame proveedoresEditarFrame;
     Panel() {
        setLayout((new FlowLayout(FlowLayout.LEFT)));
        setBackground(new Color(214,214,214));
        JMenuBar barra = new JMenuBar();

        JMenu clientes = new JMenu("Clientes");
        clientes.setIcon(new ImageIcon("src/imagenes/Clientes.png"));

        JMenu productos = new JMenu("Productos");
        productos.setIcon(new ImageIcon("src/imagenes/producto.png"));

        JMenu compras = new JMenu("Compras");
        compras.setIcon(new ImageIcon("src/imagenes/Carrito-de-compras.png"));

        JMenu ventas = new JMenu("Ventas");
        ventas.setIcon(new ImageIcon("src/imagenes/factura.png"));

        JMenu cotizacion = new JMenu("Cotizaciones");
        cotizacion.setIcon(new ImageIcon("src/imagenes/money.png"));

        JMenuItem nuevaCotizacion = new JMenuItem("Crear");
        nuevaCotizacion.addActionListener(actionEvent -> {
            try{
                CotizacionFrame cotizacionFrame = new CotizacionFrame();
                cotizacionFrame.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        JMenuItem editarCotizacion = new JMenuItem("Actualizar");
        editarCotizacion.addActionListener(actionEvent ->{
            try{
                CotizacionEditarFrame cotizacionEditarFrame = new CotizacionEditarFrame();
                cotizacionEditarFrame.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        JMenuItem nuevaVenta = new JMenuItem("Registrar venta");
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
        JMenuItem editarProducto = new JMenuItem("Mi stock");
        editarProducto.addActionListener(e -> {
            try {
                if (productosEditarFrame == null) {
                    productosEditarFrame = new ProductosEditarFrame();
                }
            } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            productosEditarFrame.setVisible(true);
        });
        JMenuItem importarProductos = new JMenuItem("Importar por archivo");
        importarProductos.addActionListener(e ->{
            try {
                if (productosImportarFrame == null) {
                    productosImportarFrame = new ProductosImportarFrame();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            productosImportarFrame.setVisible(true);
            productosImportarFrame.setResizable(false);
        });
        JMenuItem editarCliente = new JMenuItem("Mis clientes");
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
        JMenuItem misProveedores = new JMenuItem("Mis proveedores");
        misProveedores.addActionListener(e->{
            try{
                if(proveedoresEditarFrame == null) {
                    proveedoresEditarFrame = new ProveedoresEditarFrame();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            proveedoresEditarFrame.setVisible(true);
        });
        JMenuItem misCompras = new JMenuItem("Registro de compras");
        barra.add(clientes);
        barra.add(productos);
        barra.add(compras);
        barra.add(ventas);
        barra.add(cotizacion);
        productos.add(editarProducto);
        productos.add(importarProductos);
        clientes.add(editarCliente);
        compras.add(misProveedores);
        compras.add(misCompras);
        ventas.add(nuevaVenta);
        cotizacion.add(nuevaCotizacion);
        cotizacion.add(editarCotizacion);
        add(barra);
    }
}
