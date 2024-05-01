package Cotizacion;

import DTO.DtoCotizacionDetalle;
import Utiles.Conexion;
import database.ProductosDB;
import model.Productos;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelTablaProductosCotizacion extends JPanel {

    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarCliente;
    JButton seleccionarCliente;



    public PanelTablaProductosCotizacion() throws Exception {

        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Nombre");
        filtro.addItem("Codigo");
        seleccionarCliente = new JButton("Seleccionar");
        seleccionarCliente.setBounds(520, 20, 120, 30);
        seleccionarCliente.addActionListener(e -> {

            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada != -1) {

                int idProd = Integer.parseInt(tabla.getValueAt(filaSeleccionada,0).toString());
                String descripcion =  tabla.getValueAt(filaSeleccionada,1).toString();
                double precioU = Double.parseDouble(tabla.getValueAt(filaSeleccionada,4).toString());

                CotizacionPanel.productoId = idProd;
                CotizacionPanel.descripcionProd =descripcion;
                CotizacionPanel.precioUni = precioU;


                String cantidad = JOptionPane.showInputDialog(null,"Ingrese cantidad");
                int cantidadArt = Integer.parseInt(cantidad);
                CotizacionPanel.cantProducto = cantidadArt;
                double precioTotal = obtenerPrecioFinal(precioU,cantidadArt);

                DtoCotizacionDetalle dto = new DtoCotizacionDetalle(idProd,cantidadArt,descripcion,precioU,precioTotal);
                List<DtoCotizacionDetalle> listaDtoCot = CotizacionPanel.listDto;
                listaDtoCot.add(dto);
                CotizacionPanel.listDto = listaDtoCot;
                try {
                    CotizacionPanel.ConstruirTablaCotizacion(0,null);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelTablaProductosCotizacion.this);
                frame.dispose();


            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un articulo de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        filtro.setBounds(20, 20, 100, 30);
        buscarCliente = new JTextField(15);
        buscarCliente.setBounds(150, 20, 350, 30);
        buscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int opcion = filtro.getSelectedIndex();
                String valorBuscar = buscarCliente.getText();
                try {
                    ConstruirTabla(opcion, valorBuscar);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        ConstruirTabla(0, null);
        scroll.setBounds(20, 100, 600, 200);
        add(buscarCliente);
        add(filtro);
        scroll.setViewportView(tabla);
        add(scroll);
        add(seleccionarCliente);
        setLayout(null);

    }
    public double obtenerPrecioFinal(double precio,int cantidad){
        double precioFianl = precio * cantidad;
        return precioFianl;
    }

    public void ConstruirTabla(int i, Object o) throws Exception{
        String[] titulo = {"ID","Descripción", "Código", "Abreviatura", "Precio unit.", "Stock mín.", "Stock"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        scroll.setViewportView(tabla);
        JTableHeader titulo1 = tabla.getTableHeader();
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
        ajustarAnchoColumnas();
    }
    private String[][] obtenerMatriz() throws Exception{
        ProductosDB productosDB = new ProductosDB(Conexion.conectar());
        List<Productos> lstProductos = productosDB.todosProductos();
        String[][] matrizInfo = new String[lstProductos.size()][7];
        for(int i=0; i < lstProductos.size(); i++){
            matrizInfo[i][0] = lstProductos.get(i).getId() + "";
            matrizInfo[i][1] = lstProductos.get(i).getDescripcion() + "";
            matrizInfo[i][2] = lstProductos.get(i).getCodigo() + "";
            matrizInfo[i][3] = lstProductos.get(i).getAbreviatura() + "";
            matrizInfo[i][4] = lstProductos.get(i).getPrecio() + "";
            matrizInfo[i][5] = lstProductos.get(i).getStock_minimo() + "";
            matrizInfo[i][6] = lstProductos.get(i).getStock() + "";
        }
        return matrizInfo;
    }
    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {80, 300, 80, 200, 100, 100, 100};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }
}
