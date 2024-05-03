package Proveedores;
import Utiles.Conexion;
import database.ProveedoresDB;
import model.Proveedores;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class VentanaProveedoresNuevoPanel extends JPanel {
    JTextField txtNombre;
    JTextField txtCuit;
    JTextField txtDireccion;
    JTextField txtCiudad;
    JTextField txtCBU;
    JButton btnGuardar;

    public VentanaProveedoresNuevoPanel() throws Exception {
        setBackground(new Color(214,214,214));
        JLabel titulo = new JLabel("Nuevo proveedor");
        titulo.setBounds(100, 1, 200, 40);

        JLabel nombre = new JLabel("Nombre:");
        nombre.setBounds(20, 30, 200, 40);

        JLabel cuit = new JLabel("CUIT:");
        cuit.setBounds(20, 70, 250, 40);

        JLabel direccion = new JLabel("Dirección:");
        direccion.setBounds(20, 110, 250, 40);

        JLabel ciudad = new JLabel("Ciudad:");
        ciudad.setBounds(20, 150, 250, 40);

        JLabel cbu = new JLabel("Alias/CBU:");
        cbu.setBounds(20, 190, 250, 40);

        txtNombre = new JTextField(15);
        txtNombre.setBounds(100, 33, 600, 30);
        txtNombre.addActionListener(e->{
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtCuit.requestFocus();
        });
        txtCuit = new JTextField(15);
        txtCuit.setBounds(100, 73, 600, 30);
        txtCuit.addActionListener(e->{
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtDireccion.requestFocus();
        });
        txtDireccion = new JTextField(15);
        txtDireccion.setBounds(100, 113, 600, 30);
        txtDireccion.addActionListener(e->{
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtCiudad.requestFocus();
        });
        txtCiudad = new JTextField(15);
        txtCiudad.setBounds(100, 153, 600, 30);
        txtCiudad.addActionListener(e->{
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtCBU.requestFocus();
        });
        txtCBU = new JTextField(15);
        txtCBU.setBounds(100,193,600,30);
        btnGuardar = new JButton("Guardar", new ImageIcon("src/imagenes/GuardarTodo.png"));
        btnGuardar.setBounds(350, 250, 120, 50);
        btnGuardar.addActionListener(e -> {
            ProveedoresDB proveedoresDB = new ProveedoresDB(Conexion.conectar());
            String nombre1 = txtNombre.getText();
            String cuit1 = txtCuit.getText();
            String direccion1 = txtDireccion.getText();
            String ciudad1 = txtCiudad.getText();
            String cbu1 = txtCBU.getText();
            if (nombre1.isEmpty() || cuit1.isEmpty() || direccion1.isEmpty() || ciudad1.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                return;
            }
            cuit1 = txtCuit.getText();
            if (cuit1.length() != 11) {
                JOptionPane.showMessageDialog(null, "El CUIT debe tener 11 dígitos");
                return;
            }
            Proveedores proveedores = new Proveedores(0,nombre1,cuit1,direccion1,ciudad1,cbu1);
            try {
                proveedoresDB.insertarProveedores(proveedores);
                JOptionPane.showMessageDialog(null,"Cargado correctamente");
                LimpiarTxt(txtNombre);
                LimpiarTxt(txtCuit);
                LimpiarTxt(txtDireccion);
                LimpiarTxt(txtCiudad);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        add(titulo);
        add(nombre);
        add(txtNombre);
        add(cuit);
        add(txtCuit);
        add(direccion);
        add(txtDireccion);
        add(ciudad);
        add(txtCiudad);
        add(cbu);
        add(txtCBU);
        add(btnGuardar);
        setLayout(null);
    }
    public void LimpiarTxt(JTextField e) {
        e.setText("");
    }
}
