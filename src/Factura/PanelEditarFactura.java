package Factura;
import Clientes.VentanaClienteEditarPanel;
import Utiles.Conexion;
import com.toedter.calendar.JDateChooser;
import database.FacturasDB;
import model.Facturas;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class PanelEditarFactura extends JPanel{
    JLabel buscarCliente, lblNumero, lblMonto, lblFecha, lblArchivo;
    static JTextField cliente;
    static JTextField txtNumero;
    static JTextField txtMonto;
    static JTextField txtArchivo;
    static JDateChooser calendario;
    static JButton btnGuardar;

    public PanelEditarFactura(){
        setBackground(new Color(214,214,214));
        buscarCliente = new JLabel("Cliente seleccionado:");
        lblNumero = new JLabel("Nro. Factura:");
        lblMonto = new JLabel("Monto");
        lblFecha = new JLabel("Fecha:");
        lblArchivo = new JLabel("Nombre Archivo:");
        cliente = new JTextField(15);
        txtNumero = new JTextField(15);
        txtMonto = new JTextField(15);
        txtArchivo = new JTextField(15);
        calendario = new JDateChooser();
        calendario.setDateFormatString("dd/MM/yyyy");
        btnGuardar = new JButton("Guardar", new ImageIcon("src/imagenes/GuardarTodo.png"));
        btnGuardar.addActionListener(e->{
            FacturasDB facturaDB = new FacturasDB(Conexion.conectar());
            int cliente1 = Integer.parseInt(cliente.getText());
            String numero = txtNumero.getText();
            Double monto = Double.parseDouble(txtMonto.getText());
            Date fecha = calendario.getDate();
            String archivo = txtArchivo.getText();
            if(cliente1 == 0 || numero.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
                return;
            }
            try{
                Facturas factura = facturaDB.consultaFacturas(PanelFactura.facturaID);
                factura.setCliente_id(cliente1);
                factura.setNumero(numero);
                factura.setMonto(monto);
                factura.setFecha_hora(fecha);
                factura.setArchivo(archivo);

                facturaDB.actualizarFacturas(factura);
                JOptionPane.showMessageDialog(null,"Se actualizó la factura");

                LimpiarTxt(cliente);
                LimpiarTxt(txtNumero);
                LimpiarTxt(txtMonto);
                LimpiarFecha(calendario);
                LimpiarTxt(txtArchivo);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelEditarFactura.this);
                frame.dispose();

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
        add(buscarCliente, gbc);

        gbc.gridy = 1;
        add(lblNumero, gbc);

        gbc.gridy = 2;
        add(lblMonto, gbc);

        gbc.gridy = 3;
        add(lblFecha, gbc);

        gbc.gridy = 4;
        add(lblArchivo, gbc);

        // Segunda columna
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        cliente.setPreferredSize(new Dimension(300, 30));
        add(cliente, gbc);

        gbc.gridy = 1;
        txtNumero.setPreferredSize(new Dimension(300, 30));
        add(txtNumero, gbc);

        gbc.gridy = 2;
        txtMonto.setPreferredSize(new Dimension(300, 30));
        add(txtMonto, gbc);

        gbc.gridy = 3;
        calendario.setPreferredSize(new Dimension(100, 30));
        add(calendario, gbc);

        gbc.gridy = 4;
        txtArchivo.setPreferredSize(new Dimension(300, 30));
        add(txtArchivo, gbc);

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
    public void LimpiarFecha(JDateChooser e){
        e.setDate(null);
    }
}
