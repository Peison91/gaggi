package Factura;

import Utiles.Conexion;
import database.ClientesDB;
import database.FacturasDB;
import model.Clientes;
import model.Facturas;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PanelFactura extends JPanel {
    JLabel lblId, lblNumero, lblFechaHora, lblMonto, lblArchivo, lblFactura, buscarCliente;
    JTextField txtNumero, txtMonto, txtArchivo, txtBuscar;
    static JTextField txtCliente;
    static int clienteID;
    static int facturaID;
    JButton btnCliente, btnGuardar, btnBorrar, btnActualizar,btnAbrirPdf;
    JDateChooser calendario;
    JScrollPane scroll;
    JTable tabla = new JTable();
    DefaultTableModel modelo;
    JComboBox lista;
    private EditarFacturaFrame editarFacturaFrame;

    public PanelFactura() throws Exception {
        btnCliente = new JButton("Seleccionar", new ImageIcon("src/imagenes/Clientes.png"));
        btnCliente.addActionListener(e -> {
            try {
                FrameTablaClientes tablaClientes = new FrameTablaClientes();
                tablaClientes.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        lblId = new JLabel("10");
        buscarCliente = new JLabel("Cliente:");
        txtCliente = new JTextField(15);
        txtCliente.setEditable(false);

        lblNumero = new JLabel("Nro. Factura:");
        txtNumero = new JTextField();

        lblMonto = new JLabel("Monto:");
        txtMonto = new JTextField();

        lblFechaHora = new JLabel("Fecha:");
        calendario = new JDateChooser();
        calendario.setDateFormatString("dd/MM/yyyy");

        lblArchivo = new JLabel("Nombre Archivo:");
        txtArchivo = new JTextField();

        lblFactura = new JLabel("Buscar Factura:");

        lista = new JComboBox();
        lista.addItem("ID");
        lista.addItem("NOMBRE");
        lista.addItem("NUMERO");
        txtBuscar = new JTextField();
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
        construirTabla(0, null);
        btnGuardar = new JButton("Guardar", new ImageIcon("src/imagenes/GuardarTodo.png"));
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
                limpiarTxt(txtCliente);
                lblId.setText("");

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnBorrar = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnActualizar = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnActualizar.addActionListener(new ActionListener() {
            FacturasDB facturasDB = new FacturasDB(Conexion.conectar());
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                } else {
                    Facturas facturas = new Facturas();
                    ClientesDB clientesDB = new ClientesDB(Conexion.conectar());

                    int clienteId = Integer.parseInt(tabla.getValueAt(fila, 1).toString());
                    facturas.setId(Integer.parseInt(tabla.getValueAt(fila, 0).toString()));
                    facturaID = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                    facturas.setCliente_id(clienteId); // Usar el ID del cliente
                    facturas.setNumero(tabla.getValueAt(fila, 2).toString());
                    java.util.Date miFecha = new java.util.Date();
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    String dato = tabla.getValueAt(fila, 4).toString().substring(0, 10);
                    facturas.setFecha_hora(miFecha);
                    facturas.setMonto(Double.parseDouble(tabla.getValueAt(fila, 5).toString()));
                    facturas.setArchivo(tabla.getValueAt(fila, 6).toString());
                    UIManager.put("OptionPane.yesButtonText", "Si");
                    UIManager.put("OptionPane.noButtonText", "No");
                    int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea modificar?", "Aviso", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        try {
                            if (editarFacturaFrame == null) {
                                editarFacturaFrame = new EditarFacturaFrame();
                            }
                            editarFacturaFrame.setVisible(true);
                            editarFacturaFrame.setResizable(false);
                            facturaID = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                            PanelEditarFactura.cliente.setText("id: " + tabla.getValueAt( fila, 1).toString() + " - " + tabla.getValueAt(fila,2).toString());
                            PanelEditarFactura.txtNumero.setText(tabla.getValueAt(fila, 3).toString());
                            PanelEditarFactura.txtMonto.setText(tabla.getValueAt(fila, 5).toString());
                            PanelEditarFactura.calendario.setDate((Date) tabla.getValueAt(fila, 4));
                            PanelEditarFactura.txtArchivo.setText(tabla.getValueAt(fila, 6).toString());


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
        btnAbrirPdf = new JButton("Abrir PDF", new ImageIcon("src/imagenes/pdf.png"));
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

        scroll.setViewportView(tabla);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //Primera columna. Hombre, odio esto...
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(buscarCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblNumero, gbc);

        //gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblMonto, gbc);

        //gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblFechaHora, gbc);

        //gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblArchivo, gbc);

        //gbc.gridx = 0;
        gbc.gridy = 5;
        lblFactura.setPreferredSize(new Dimension(110,10));
        lblFactura.setMaximumSize(new Dimension(110,10));
        lblFactura.setMinimumSize(new Dimension(110,10));
        add(lblFactura, gbc);

        //gbc.gridx = 0;
        gbc.gridy = 6;
        add(lista, gbc);

        //Segunda columna, oh god
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.gridwidth = 2;
        txtCliente.setPreferredSize(new Dimension(240,30));
        txtCliente.setMaximumSize(new Dimension(240,30));
        txtCliente.setMinimumSize(new Dimension(240,30));
        add(txtCliente, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        txtNumero.setPreferredSize(new Dimension(150,30));
        txtNumero.setMaximumSize(new Dimension(150,30));
        txtNumero.setMinimumSize(new Dimension(150,30));
        add(txtNumero, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        txtMonto.setPreferredSize(new Dimension(150,30));
        txtMonto.setMaximumSize(new Dimension(150,30));
        txtMonto.setMinimumSize(new Dimension(150,30));
        add(txtMonto, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        calendario.setPreferredSize(new Dimension(150,30));
        calendario.setMaximumSize(new Dimension(150,30));
        calendario.setMinimumSize(new Dimension(150,30));
        add(calendario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        txtArchivo.setPreferredSize(new Dimension(150,30));
        txtArchivo.setMaximumSize(new Dimension(150,30));
        txtArchivo.setMinimumSize(new Dimension(150,30));
        add(txtArchivo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        txtBuscar.setPreferredSize(new Dimension(300,30));
        txtBuscar.setMaximumSize(new Dimension(300,30));
        txtBuscar.setMinimumSize(new Dimension(300,30));
        add(txtBuscar, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        add(Box.createHorizontalGlue(), gbc);

        gbc.gridy = 6;
        gbc.gridx = 3;
        btnAbrirPdf.setPreferredSize(new Dimension(130,30));
        btnAbrirPdf.setMaximumSize(new Dimension(130,30));
        btnAbrirPdf.setMinimumSize(new Dimension(130,30));
        add(btnAbrirPdf, gbc);

        gbc.gridy = 6;
        gbc.gridx = 4;
        btnActualizar.setPreferredSize(new Dimension(130,30));
        btnActualizar.setMaximumSize(new Dimension(130,30));
        btnActualizar.setMinimumSize(new Dimension(130,30));
        add(btnActualizar, gbc);

        gbc.gridy = 6;
        gbc.gridx = 5;
        btnBorrar.setPreferredSize(new Dimension(130,30));
        btnBorrar.setMaximumSize(new Dimension(130,30));
        btnBorrar.setMinimumSize(new Dimension(130,30));
        add(btnBorrar, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        btnCliente.setPreferredSize(new Dimension(130,30));
        btnCliente.setMaximumSize(new Dimension(130,30));
        btnCliente.setMinimumSize(new Dimension(130,30));
        add(btnCliente, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        btnGuardar.setPreferredSize(new Dimension(130,30));
        btnGuardar.setMaximumSize(new Dimension(130,30));
        btnGuardar.setMinimumSize(new Dimension(130,30));
        add(btnGuardar,gbc);

        //La maldita tabla
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 7;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scroll, gbc);

        ajustarAnchoColumnas();
    }

    public void construirTabla(int opBuscar, String valor) throws Exception {
        String[] titulo1 = {"Id Factura", "Id Cliente", "Nombre", "Número", "Fecha", "Monto", "Archivo"};
        String informacion[][] = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo1);
        tabla.setModel(modelo);
        //ocultar columna auxiliar
        tabla.getColumnModel().getColumn(1).setMinWidth(0);
        tabla.getColumnModel().getColumn(1).setMaxWidth(0);
        tabla.getColumnModel().getColumn(1).setWidth(0);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(0);

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
    }

    public String[][] obtenerMatriz() throws Exception {
        FacturasDB facturasDB = new FacturasDB(Conexion.conectar());
        List<Facturas> lstFacturas = facturasDB.todasFacturas();
        ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
        String matrizIfno[][] = new String[lstFacturas.size()][7];

        for (int i = 0; i < lstFacturas.size(); i++) {
            Facturas facturas = lstFacturas.get(i);
            Clientes cliente = clientesDB.consultaCliente(facturas.getCliente_id());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = dateFormat.format(facturas.getFecha_hora());

            matrizIfno[i][0] = lstFacturas.get(i).getId() + "";
            matrizIfno[i][1] = facturas.getCliente_id() + "";
            matrizIfno[i][2] = cliente.getNombre();
            matrizIfno[i][3] = lstFacturas.get(i).getNumero() + "";
            matrizIfno[i][4] = lstFacturas.get(i).getFecha_hora()+"";
            matrizIfno[i][5] = lstFacturas.get(i).getMonto() + "";
            matrizIfno[i][6] = lstFacturas.get(i).getArchivo() + "";
        }
        return matrizIfno;
    }

    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {80, 0, 280, 100, 190, 100, 100};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

    public void limpiarTxt(JTextField txt) {
        txt.setText(" ");
    }
}



