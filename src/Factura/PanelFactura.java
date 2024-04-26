package Factura;

import Utiles.Conexion;
import database.FacturasDB;
import model.Facturas;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PanelFactura extends JPanel {
    JLabel lblId, lblNumero, lblFechaHora, lblMonto, lblArchivo, lblFactura, buscarCliente;
    JTextField txtNumero, txtMonto, txtArchivo, txtBuscar;
    static JTextField txtCliente;
    static int clienteID;
    JButton btnCliente, btnGuardar, btnBorrar, btnActualizar,btnAbrirPdf;
    JDateChooser calendario;
    JScrollPane scroll;
    JTable tabla = new JTable();
    DefaultTableModel modelo;
    JComboBox lista;


    public PanelFactura() throws Exception {
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


        lblNumero = new JLabel("Nro. Factura:");
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

        lblFactura = new JLabel("Buscar Factura:");
        lblFactura.setBounds(15, 190, 100, 30);

        lista = new JComboBox();
        lista.addItem("ID");
        lista.addItem("NOMBRE");
        lista.addItem("NUMERO");
        lista.setBounds(15, 220, 100, 30);
        txtBuscar = new JTextField();
        txtBuscar.setBounds(130, 220, 200, 30);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int opcion = lista.getSelectedIndex();
                String valorBus = txtBuscar.getText();
                try {
                    construirTabla(opcion, valorBus);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        scroll = new JScrollPane();
        scroll.setBounds(15, 270, 800, 400);
        construirTabla(0, null);
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
                construirTabla(0, null);
                limpiarTxt(txtNumero);
                limpiarTxt(txtMonto);
                limpiarTxt(txtArchivo);
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
                    construirTabla(0, null);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnAbrirPdf = new JButton("Abrir PDF");
        btnAbrirPdf.setBounds(600, 160, 210, 30);
        btnAbrirPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String nombreFactura = (String) tabla.getValueAt(filaSeleccionada, 5);
                    String rutaCarpeta = System.getProperty("user.dir");
                    File archivoPDF = new File(rutaCarpeta, nombreFactura);
                    // ver si el pdf existe
                    if (archivoPDF.exists()) {
                        try {
                            Desktop.getDesktop().open(archivoPDF);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La factura seleccionada no existe", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
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
        add(lblFactura);
        add(txtBuscar);
        add(lista);
        add(scroll);
        add(btnGuardar);
        add(btnBorrar);
        add(btnActualizar);
        add(btnAbrirPdf);
        scroll.setViewportView(tabla);
        ajustarAnchoColumnas();
        setLayout(null);
    }

    public void construirTabla(int opBuscar, String valor) throws Exception {
        String[] titulo1 = {"Id", "Nombre", "Número", "Fecha-Hora", "Monto", "Archivo"};
        String informacion[][] = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo1);
        tabla.setModel(modelo);

        TableRowSorter tr = new TableRowSorter<>(modelo);
        tabla.setRowSorter(tr);

        if (valor == null) {
            tr.setRowFilter(RowFilter.regexFilter(txtBuscar.getText(), 0));
        } else {
            tr.setRowFilter(RowFilter.regexFilter(txtBuscar.getText(), opBuscar));
        }
        JTableHeader titulo = tabla.getTableHeader();
        titulo.setBackground(new Color(236, 126, 29));
        titulo.setFont(new Font("Calibri", Font.BOLD, 14));
        //tabla.getTableHeader().setReorderingAllowed(false);
        //tabla.getTableHeader().setResizingAllowed(false);

    }

    public String[][] obtenerMatriz() throws Exception {
        FacturasDB facturasDB = new FacturasDB(Conexion.conectar());
        List<Facturas> lstFacturas = facturasDB.todasFacturas();
        String matrizIfno[][] = new String[lstFacturas.size()][6];
        for (int i = 0; i < lstFacturas.size(); i++) {
            matrizIfno[i][0] = lstFacturas.get(i).getId() + "";
            matrizIfno[i][1] = lstFacturas.get(i).getCliente_id() + "";
            matrizIfno[i][2] = lstFacturas.get(i).getNumero() + "";
            matrizIfno[i][3] = lstFacturas.get(i).getFecha_hora() + "";
            matrizIfno[i][4] = lstFacturas.get(i).getMonto() + "";
            matrizIfno[i][5] = lstFacturas.get(i).getArchivo() + "";
        }
        return matrizIfno;
    }
    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {80, 280, 100, 190, 100, 100};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }
    public void limpiarTxt(JTextField txt) {
        txt.setText(" ");
    }


}



