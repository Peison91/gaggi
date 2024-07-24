package Clientes;

import Utiles.Conexion;
import database.ClientesDB;
import model.Clientes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class VentanaClienteEditarPanel extends JPanel {
    static JTextField txtNombre;
    static JTextField txtCuit;
    static JTextField txtDireccion;
    static JTextField txtEmail;
    static JTextField txtTelefono;
    static JButton btnGuardar;
    public VentanaClienteEditarPanel(){
        setBackground(new Color(214,214,214));
        JLabel titulo = new JLabel("Editar clientes");
        titulo.setBounds(100, 1, 200, 40);

        JLabel nombre = new JLabel("Nombre:");
        nombre.setBounds(20, 30, 200, 40);

        JLabel cuit = new JLabel("CUIT:");
        cuit.setBounds(20, 70, 250, 40);

        JLabel direccion = new JLabel("Dirección:");
        direccion.setBounds(20, 110, 250, 40);

        JLabel email = new JLabel("Email:");
        email.setBounds(20, 150, 250, 40);

        JLabel telefono = new JLabel("Teléfono:");
        telefono.setBounds(20, 190, 250, 40);

        //TEXT FIELD
        txtNombre = new JTextField(15);
        txtNombre.setBounds(100, 33, 600, 30);
        txtNombre.addActionListener(e ->{
            e.setSource((char) KeyEvent.VK_CLEAR);
            txtCuit.requestFocus();
        });
        txtCuit = new JTextField(15);
        txtCuit.setBounds(100, 73, 600, 30);
        txtCuit.addActionListener(e->{
            e.setSource((char)KeyEvent.VK_CLEAR);
            txtDireccion.requestFocus();
        });
        txtDireccion = new JTextField(15);
        txtDireccion.setBounds(100, 113, 600, 30);
        txtDireccion.addActionListener(e->{
            e.setSource((char)KeyEvent.VK_CLEAR);
            txtEmail.requestFocus();
        });
        txtEmail = new JTextField(15);
        txtEmail.setBounds(100, 153, 600, 30);
        txtEmail.addActionListener(e->{
            e.setSource((char)KeyEvent.VK_CLEAR);
            txtTelefono.requestFocus();
        });
        txtTelefono = new JTextField(15);
        txtTelefono.setBounds(100, 193, 600, 30);

        btnGuardar = new JButton("Guardar", new ImageIcon("src/imagenes/GuardarTodo.png"));
        btnGuardar.setBounds(350, 250, 120, 50);
        btnGuardar.addActionListener(e -> {
            ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
            String nombre1 = txtNombre.getText();
            String cuit1 = txtCuit.getText();
            String direccion1 = txtDireccion.getText();
            String email1 = txtEmail.getText();
            String telefono1 = txtTelefono.getText();
            if (nombre1.isEmpty() || cuit1.isEmpty() || direccion1.isEmpty() || email1.isEmpty() || telefono1.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                return;
            }
            cuit1 = txtCuit.getText();
            if (cuit1.length() != 11) {
                JOptionPane.showMessageDialog(null, "El CUIT debe tener 11 dígitos");
                return;
            }
            try {
                Clientes clientes = clientesDB.consultaCliente(ClientesEditar.idCliente);
                clientes.setNombre(nombre1);
                clientes.setCuit(cuit1);
                clientes.setDireccion(direccion1);
                clientes.setEmail(email1);
                clientes.setTelefono(telefono1);

                clientesDB.actualizarCliente(clientes);
                JOptionPane.showMessageDialog(null,"Se actualizó el cliente");
                LimpiarTxt(txtNombre);
                LimpiarTxt(txtCuit);
                LimpiarTxt(txtDireccion);
                LimpiarTxt(txtEmail);
                LimpiarTxt(txtTelefono);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaClienteEditarPanel.this);
                frame.dispose();
                ClientesEditar.ConstruirTabla(0,null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Error al actualizar");
                throw new RuntimeException(ex);
            }

        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // Primera columna
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nombre, gbc);

        gbc.gridy = 1;
        add(cuit, gbc);

        gbc.gridy = 2;
        add(direccion, gbc);

        gbc.gridy = 3;
        add(email, gbc);

        gbc.gridy = 4;
        add(telefono, gbc);

        // Segunda columna
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre.setPreferredSize(new Dimension(300, 30));
        add(txtNombre, gbc);

        gbc.gridy = 1;
        txtCuit.setPreferredSize(new Dimension(300, 30));
        add(txtCuit, gbc);

        gbc.gridy = 2;
        txtDireccion.setPreferredSize(new Dimension(300, 30));
        add(txtDireccion, gbc);

        gbc.gridy = 3;
        txtEmail.setPreferredSize(new Dimension(300, 30));
        add(txtEmail, gbc);

        gbc.gridy = 4;
        txtTelefono.setPreferredSize(new Dimension(300, 30));
        add(txtTelefono, gbc);

        // Botón Guardar
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Abarcar dos columnas
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center the button in the panel
        add(btnGuardar, gbc);

    }
    public void LimpiarTxt(JTextField e) {
        e.setText("");
    }
}
