package Cotizacion;

import Factura.FrameTablaClientes;
import Utiles.Conexion;
import com.toedter.calendar.JDateChooser;
import database.FacturasDB;
import model.Facturas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CotizacionPanel extends JPanel {

    JLabel lblId, lblNumero, lblFechaHora, lblMonto, lblArchivo, lblEstado, buscarCliente;
    JTextField txtNumero, txtMonto, txtArchivo, txtBuscar;
    static JTextField txtCliente;
    static int clienteID;
    JButton btnCliente, btnGuardar, btnBorrar, btnActualizar;
    JDateChooser calendario;
    JScrollPane scroll;
    JTable tabla = new JTable();
    DefaultTableModel modelo;
    JComboBox lista;

    public CotizacionPanel()throws Exception{
        btnCliente = new JButton("Seleccione Cliente");
        btnCliente.setBounds(600, 20, 210, 30);
        btnCliente.addActionListener(e -> {
            try {
                FrameTablaClientes tablaClientes = new FrameTablaClientes();
                tablaClientes.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        lblId = new JLabel("10");
        /*lblId.setBounds(150, 25, 100, 25);
        //lblId.setText("5");*/
        buscarCliente = new JLabel("Seleccione cliente:");
        buscarCliente.setBounds(15, 20, 250, 30);
        txtCliente = new JTextField(15);
        txtCliente.setBounds(140, 20, 350, 30);


        lblNumero = new JLabel("Indice Ajuste:");
        lblNumero.setBounds(15, 55, 100, 30);
        txtNumero = new JTextField();
        txtNumero.setBounds(140, 55, 150, 30);

        lblMonto = new JLabel("Monto:");
        lblMonto.setBounds(15, 90, 100, 30);
        txtMonto = new JTextField();
        txtMonto.setBounds(140, 90, 150, 30);

        lblFechaHora = new JLabel("Fecha-Hora:");
        lblFechaHora.setBounds(15, 125, 100, 30);
        calendario = new JDateChooser();
        calendario.setBounds(140, 125, 150, 30);

        lblArchivo = new JLabel("Nombre Archivo:");
        lblArchivo.setBounds(15, 160, 100, 30);
        txtArchivo = new JTextField();
        txtArchivo.setBounds(140, 160, 150, 30);

        lblEstado = new JLabel("Estado");
        lblEstado.setBounds(15, 190, 100, 30);

        lista = new JComboBox();
        lista.addItem("Aceptada");
        lista.addItem("Pendiente");
        lista.addItem("Rechazado");
        lista.setBounds(15, 220, 120, 30);//ACA
        txtBuscar = new JTextField();
        txtBuscar.setBounds(130, 220, 200, 30);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int opcion = lista.getSelectedIndex();
                String valorBus = txtBuscar.getText();
                try {

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        scroll = new JScrollPane();
        scroll.setBounds(15, 270, 800, 400);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(300, 160, 100, 30);
        btnGuardar.addActionListener(e -> {
            FacturasDB facturasDB = new FacturasDB(Conexion.conectar());
            int idCliente = clienteID;
            String numero = txtNumero.getText();
            Double monto = Double.parseDouble(txtMonto.getText());
            String archivo = txtArchivo.getText();
            Facturas facturas = new Facturas(0, idCliente, numero, calendario.getDate(), monto, archivo);
            try {
                facturasDB.insertarFacturas(facturas);
                JOptionPane.showMessageDialog(null, "Guardado correctamente");

                lblId.setText("");

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnBorrar = new JButton("Eliminar");
        btnBorrar.setBounds(600, 220, 100, 30);
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(710, 220, 100, 30);
        btnActualizar.addActionListener(new ActionListener() {
            FacturasDB facturasDB = new FacturasDB(Conexion.conecc);

            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                } else {
                    Facturas facturas = new Facturas();
                    facturas.setId(Integer.parseInt(tabla.getValueAt(fila, 0).toString()));
                    facturas.setCliente_id(Integer.parseInt(tabla.getValueAt(fila, 1).toString()));
                    facturas.setNumero(tabla.getValueAt(fila, 2).toString());
                    java.util.Date miFecha = new java.util.Date();
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    String dato = tabla.getValueAt(fila, 3).toString().substring(0, 10);
                    try {
                        miFecha = formato.parse(dato);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);

                    }
                    facturas.setFecha_hora(miFecha);
                    facturas.setMonto(Double.parseDouble(tabla.getValueAt(fila, 4).toString()));
                    facturas.setArchivo(tabla.getValueAt(fila, 5).toString());
                    int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea modificar?", "Aviso", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        try {
                            facturasDB.actualizarFacturas(facturas);
                            JOptionPane.showMessageDialog(null, "Modificado correctamente");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una factura", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int id = Integer.parseInt((String) tabla.getValueAt(fila, 0));
                    UIManager.put("OptionPane.yesButtonText", "Si");
                    UIManager.put("OptionPane.noButtonText", "No");
                    int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?", "Importante", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        try {
                            FacturasDB facturasDB = new FacturasDB(Conexion.conectar());
                            facturasDB.borrarFactura(id);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        String nombreFacutra = (String) tabla.getValueAt(fila, 5);

                        String rutaPDF = "C:\\Users\\maxi_\\OneDrive\\Escritorio\\Codigo limpio\\" + nombreFacutra;

                        File archivo = new File(rutaPDF);

                        if (archivo.exists()) {
                            if (archivo.delete()) {
                                JOptionPane.showMessageDialog(null, "Factura eliminada");
                            } else {
                                System.out.println("No se pudo eliminar el archivo.");
                            }
                        } else {
                            System.out.println("El archivo no existe.");
                        }
                    }
                }
                try {

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(btnCliente);
        add(txtCliente);
        add(buscarCliente);
        add(lblId);
        add(lblNumero);
        add(txtNumero);
        add(lblMonto);
        add(txtMonto);
        add(calendario);
        add(lblFechaHora);
        add(lblArchivo);
        add(txtArchivo);
        add(lblEstado);
        add(txtBuscar);
        add(lista);
        add(scroll);
        add(btnGuardar);
        add(btnBorrar);
        add(btnActualizar);

        scroll.setViewportView(tabla);
        setLayout(null);
    }
    }




