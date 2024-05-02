package Cotizacion;

import Utiles.Conexion;
import database.Cotizacion_CabeceraDB;
import model.Cotizacion;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;

public class CotizacionEditarPanel extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarCotizacion;

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
        buscarCotizacion.setBounds(150,20,350,30);
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
        ConstruirTabla(0,null);
        scroll.setBounds(20,80,850,350);
        add(buscarCotizacion);
        add(filtro);
        scroll.setViewportView(tabla);
        add(scroll);
        setLayout(null);
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
    private String[][] obtenerMatriz() throws Exception{
        Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
        List<Cotizacion> listCotizacionesCab = cotizacionCabeceraDB.todasCotizacionesCab();
        String[][] matrizInfo = new String[listCotizacionesCab.size()][5];
        for(int i=0; i < listCotizacionesCab.size(); i++){
            matrizInfo[i][0] = listCotizacionesCab.get(i).getId_cabecera() + "";
            matrizInfo[i][1] = listCotizacionesCab.get(i).getCliente_id() + "";
            matrizInfo[i][2] = listCotizacionesCab.get(i).getFecha_cotizacion() + "";
            matrizInfo[i][3] = listCotizacionesCab.get(i).getIndice_ajuste() + "";
            matrizInfo[i][4] = listCotizacionesCab.get(i).getEstado() + "";
        }
        return matrizInfo;
    }
    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {100, 100, 100, 100, 100};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }
}