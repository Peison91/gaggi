package Cotizacion;

import Utiles.Conexion;
import database.ClientesDB;
import database.Cotizacion_CabeceraDB;
import database.Cotizacion_DetalleDB;

import database.EstadoDB;
import model.Clientes;
import model.Cotizacion;
import model.Estado;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class CotizacionEditarPanel extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarCotizacion;
    JButton btnModificar, btnEliminar, btnPDF;

    public CotizacionEditarPanel() throws Exception {
        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Cliente");
        filtro.addItem("Índice ajuste");
        filtro.addItem("Fecha");
        filtro.addItem("Estado");
        filtro.setBounds(20,20,100,30);
        setBackground(new Color(214,214,214));
        buscarCotizacion = new JTextField(15);
        buscarCotizacion.setBounds(150,20,200,30);
        buscarCotizacion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int opcion = filtro.getSelectedIndex();
                String valorBuscar = buscarCotizacion.getText();
                try {
                    ConstruirTabla(opcion, valorBuscar);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnPDF = new JButton("Abrir PDF", new ImageIcon("src/imagenes/pdf.png"));
        btnPDF.setBounds(390, 20, 130, 30);
        btnPDF.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
                String nombreFactura = (String) tabla.getValueAt(filaSeleccionada, 0);
                String rutaCarpeta = System.getProperty("user.home") + File.separator +"Desktop"+File.separator +"Cotización_" + nombreFactura + ".pdf";
                File archivoPDF = new File(rutaCarpeta);
                // Ver si el PDF existe
                if (archivoPDF.exists()) {
                    try {
                        Desktop.getDesktop().open(archivoPDF);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo seleccionada no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un archivo de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnModificar = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnModificar.setBounds(560, 20, 130, 30);
        btnModificar.addActionListener(e->{
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una cotización", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int id_cabecera = Integer.parseInt((String) tabla.getValueAt(fila, 0));
                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea modificar?", "Importante", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        VentanaCotizacionEditar ventanaCotizacionEditar = new VentanaCotizacionEditar(id_cabecera); // ACA SE DUPLICA

                        Cotizacion_DetalleDB cotizacionDetalleDB = new Cotizacion_DetalleDB(Conexion.conectar());
                        Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            try {
                ConstruirTabla(0,null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnEliminar = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnEliminar.setBounds(730, 20, 130,30);
        btnEliminar.addActionListener(e ->{
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una cotización", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int id_cabecera = Integer.parseInt((String) tabla.getValueAt(fila, 0));
                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?", "Importante", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        Cotizacion_DetalleDB cotizacionDetalleDB = new Cotizacion_DetalleDB(Conexion.conectar());
                        cotizacionDetalleDB.borrarCotDetalleCabecera(id_cabecera);
                        Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
                        cotizacionCabeceraDB.borrarCotizacion(id_cabecera);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Cotización eliminada");
                }
            }
            try {
                ConstruirTabla(0,null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        ConstruirTabla(0,null);
        scroll.setBounds(20,80,850,350);
        add(buscarCotizacion);
        add(btnPDF);
        add(btnModificar);
        add(btnEliminar);
        add(filtro);
        scroll.setViewportView(tabla);
        add(scroll);
        setLayout(null);
        System.out.println();
    }
    public void ConstruirTabla(int opBuscar, String valor) throws Exception{
        String[] titulo = {"ID","Cliente", "Fecha", "Índice ajuste", "Estado"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        ajustarAnchoColumnas();
        TableRowSorter tr = new TableRowSorter<>(modelo);
        tabla.setRowSorter(tr);
        if (valor != null && !valor.isEmpty()) {
            String valorBuscar = valor.toLowerCase();

            tr.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(valorBuscar), opBuscar));
        }
        JTableHeader titulo1 = tabla.getTableHeader();
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
    }
    private String[][] obtenerMatriz() throws Exception {
        Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
        List<Cotizacion> listCotizacionesCab = cotizacionCabeceraDB.todasCotizacionesCab();
        ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
        EstadoDB estadoDB = new EstadoDB(Conexion.conectar());

        String[][] matrizInfo = new String[listCotizacionesCab.size()][5];
        for (int i = 0; i < listCotizacionesCab.size(); i++) {
            Cotizacion cotizacion = listCotizacionesCab.get(i);
            Clientes cliente = clientesDB.consultaCliente(cotizacion.getCliente_id());
            Estado estado = estadoDB.consultaEstado(cotizacion.getEstado());

            matrizInfo[i][0] = String.valueOf(cotizacion.getId_cabecera());
            matrizInfo[i][1] = cliente.getNombre();
            matrizInfo[i][2] = String.valueOf(cotizacion.getFecha_cotizacion());
            matrizInfo[i][3] = String.valueOf(cotizacion.getIndice_ajuste());
            matrizInfo[i][4] = estado.getNombre();
        }
        return matrizInfo;
    }
    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {50, 200, 100, 100, 100};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }
}
