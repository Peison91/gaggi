package Factura;

import com.gaggi.database.ClientesDB;
import com.gaggi.database.DBConection;
import com.gaggi.database.FacturasDB;
import com.gaggi.model.Clientes;
import com.gaggi.model.Facturas;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PanelFactura extends JPanel {
    JLabel lblId, lblNumero, lblFechaHora, lblMonto, lblArchivo, lblFactura, buscarCliente;
    JTextField txtNumero, txtMonto, txtArchivo, txtBuscar, txtCliente;
    JButton btnCliente, btnGuardar, btnBorrar, btnActualizar;
    JDateChooser calendario;
    JScrollPane scroll;
    JTable tabla = new JTable();
    DefaultTableModel modelo;
    JComboBox lista;

    public PanelFactura() throws Exception {
        DBConection conecc = new DBConection("localhost", "root", "root");
        conecc.conectar();
        btnCliente = new JButton("Seleccione Cliente");
        btnCliente.setBounds(550, 20, 200, 30);
        btnCliente.addActionListener(e -> {
            try {
                FrameTablaClientes tablaClientes = new FrameTablaClientes();
                tablaClientes.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnCliente.addActionListener(e -> {

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
        construirTabla(0,null);
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(550,350,100,25);
        btnGuardar.addActionListener(e -> {
            FacturasDB facturasDB = new FacturasDB(conecc);
            int idCliente = Integer.parseInt(lblId.getText());
            String numero = txtNumero.getText();
            Double monto = Double.parseDouble(txtMonto.getText());
            String archivo = txtArchivo.getText();

            Facturas facturas = new Facturas(0,idCliente,numero, calendario.getDate(),monto,archivo);
            try {
                facturasDB.insertarFacturas(facturas);
                JOptionPane.showMessageDialog(null, "Guardado correctamente");
                construirTabla(0,null);
                limpiarTxt(txtNumero);
                limpiarTxt(txtMonto);
                limpiarTxt(txtArchivo);
                lblId.setText("");


            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnBorrar = new JButton("Eliminar");
        btnBorrar.setBounds(600,850,100,25);
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(710,850,100,25);
        btnActualizar.addActionListener(new ActionListener() {
            FacturasDB facturasDB = new FacturasDB(conecc);
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
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
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
        scroll.setViewportView(tabla);
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
        titulo.setBackground(new Color(211, 108, 45));
        titulo.setFont(new Font("Calibri", Font.BOLD, 14));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        // tabla.getColumn("EMAIL").setHeaderValue("TELEFONO");
        //tabla.getColumn("TELEFONO").setHeaderValue("EMAIL");
    }
    public String[][] obtenerMatriz() throws Exception {
        DBConection conecc = new DBConection("localhost", "root", "selfa");
        conecc.conectar();
        FacturasDB facturasDB = new FacturasDB(conecc);
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
    public void limpiarTxt(JTextField txt){
        txt.setText(" ");
    }
}