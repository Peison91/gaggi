package Clientes;
import Utiles.Conexion;
import com.gaggi.database.ClientesDB;
import model.Clientes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ClientesNuevo extends JPanel {
    JTextField txtNombre;
    JTextField txtCuit;
    JTextField txtDireccion;
    JTextField txtEmail;
    JTextField txtTelefono;
    JButton btnGuardar;
    TablaClientes tablaClientes = new TablaClientes();

    public ClientesNuevo() throws Exception {
        Conexion.conectar();
        setBackground(new Color(214,214,214));
        JLabel titulo = new JLabel("Nuevo Cliente");
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
            e.setSource((char)KeyEvent.VK_CLEAR);
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
        btnGuardar.setBounds(750, 33, 120, 50);
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
            Clientes clientes = new Clientes(0, nombre1, cuit1, direccion1, email1, telefono1);
            try {
                clientesDB.insertarCliente(clientes);
                JOptionPane.showMessageDialog(null,"Cargado correctamente");
                LimpiarTxt(txtNombre);
                LimpiarTxt(txtCuit);
                LimpiarTxt(txtDireccion);
                LimpiarTxt(txtEmail);
                LimpiarTxt(txtTelefono);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                tablaClientes.ConstruirTabla();
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
        add(email);
        add(txtEmail);
        add(telefono);
        add(txtTelefono);
        add(btnGuardar);
        setLayout(null);
        tablaClientes.setBounds(20,250,850,350);
        add(tablaClientes);
    }
    public void LimpiarTxt(JTextField e) {
        e.setText("");
    }
}
