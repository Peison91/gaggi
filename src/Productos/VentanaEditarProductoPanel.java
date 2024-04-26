package Productos;
import Utiles.Conexion;
import database.ProductosDB;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class VentanaEditarProductoPanel extends JPanel {
    static JTextField txtDescr;
    static JTextField txtCodProd;
    static JTextField txtAbrevProd;
    static JTextField txtPreciounitProd;
    static JTextField txtStockMin;
    static JTextField txtStock;



    public VentanaEditarProductoPanel() throws Exception {

        setBackground(new Color(214, 214, 214));
        JLabel nuevoProducto = new JLabel("Editar Producto");
        nuevoProducto.setBounds(100, 1, 200, 40);

        JLabel descrProd = new JLabel("Descripción:");
        descrProd.setBounds(20, 30, 200, 40);
        txtDescr = new JTextField(15);
        txtDescr.setBounds(100, 33, 300, 30);
        txtDescr.addActionListener(e -> {
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtCodProd.requestFocus();
        });

        JLabel codProd = new JLabel("Código:");
        codProd.setBounds(20, 70, 250, 40);
        txtCodProd = new JTextField(15);
        txtCodProd.setBounds(100, 73, 300, 30);
        txtCodProd.addActionListener(e -> {
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtAbrevProd.requestFocus();
        });
        JLabel abrevProd = new JLabel("Abreviación:");
        abrevProd.setBounds(20, 110, 250, 40);
        txtAbrevProd = new JTextField(15);
        txtAbrevProd.setBounds(100, 113, 300, 30);
        txtAbrevProd.addActionListener(e -> {
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtPreciounitProd.requestFocus();
        });

        JLabel precioUnitProd = new JLabel("Precio unit.:");
        precioUnitProd.setBounds(420, 30, 250, 40);
        txtPreciounitProd = new JTextField(15);
        txtPreciounitProd.setBounds(500, 33, 200, 30);
        txtPreciounitProd.addActionListener(e -> {
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtStockMin.requestFocus();
        });

        JLabel stockMin = new JLabel("Stock mín.:");
        stockMin.setBounds(420, 70, 250, 40);
        txtStockMin = new JTextField(15);
        txtStockMin.setBounds(500, 73, 200, 30);
        txtStockMin.addActionListener(e -> {
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtStock.requestFocus();
        });

        JLabel stockProd = new JLabel("Stock:");
        stockProd.setBounds(420, 110, 250, 40);
        txtStock = new JTextField(15);
        txtStock.setBounds(500, 113, 200, 30);

        JButton btnGuardar = new JButton("Guardar", new ImageIcon("src/imagenes/GuardarTodo.png"));
        btnGuardar.setBounds(350, 200, 120, 50);
        add(btnGuardar);
        setLayout(null);
        btnGuardar.addActionListener(e -> {
            ProductosDB productosDB = new ProductosDB(Conexion.conectar());
            String descr = txtDescr.getText();
            String cod = txtCodProd.getText();
            String abrev = txtAbrevProd.getText();
            double precioUnit = Double.parseDouble(txtPreciounitProd.getText());
            int stockmin = Integer.parseInt(txtStock.getText());
            int stock = Integer.parseInt(txtStock.getText());
            if (descr.length() == 0 || cod.length() == 0 || abrev.length() == 0) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios");
                return;
            }
            try {
                precioUnit = Double.parseDouble(txtPreciounitProd.getText());
                if (!txtPreciounitProd.getText().matches("^\\d+(\\.\\d{1,2})?$")) {
                    JOptionPane.showMessageDialog(null, "El precio unitario debe ser un número con hasta dos decimales");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El precio unitario debe ser un número válido");
                return;
            }
            try {
                stockmin = Integer.parseInt(txtStockMin.getText());
                stock = Integer.parseInt(txtStock.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Los campos de stock deben ser números enteros");
                return;
            }
            try {
                model.Productos productoEditar = productosDB.consultaProducto(ProductosEditar.idProducto);
                productoEditar.setDescripcion(descr);
                productoEditar.setCodigo(cod);
                productoEditar.setAbreviatura(abrev);
                productoEditar.setPrecio(precioUnit);
                productoEditar.setStock_minimo(stockmin);
                productoEditar.setStock(stock);

                productosDB.actualizarProducto(productoEditar);
                JOptionPane.showMessageDialog(null,"Se actualizo el articulo");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Error al actualizar articulo");
                throw new RuntimeException(ex);
            }


        });
        add(nuevoProducto);
        add(descrProd);
        add(txtDescr);
        add(codProd);
        add(txtCodProd);
        add(abrevProd);
        add(txtAbrevProd);
        add(precioUnitProd);
        add(txtPreciounitProd);
        add(stockMin);
        add(txtStockMin);
        add(stockProd);
        add(txtStock);

    }
}