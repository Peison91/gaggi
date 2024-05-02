package Cotizacion;
import DTO.DtoCotizacionDetalle;
import Utiles.Conexion;
import com.toedter.calendar.JDateChooser;
import database.Cotizacion_CabeceraDB;
import database.Cotizacion_DetalleDB;
import model.Cotizacion;
import model.Cotizacion_detalle;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CotizacionPanel extends JPanel {

    JLabel lblId, lblNumero, lblFechaHora, lblEstado, buscarCliente, lblValorFinal1;
    static JLabel lblValorFinal2;
    static JTextField txtIndiceAjuste;
    static JTextField txtCliente;
    static int productoId;
    static String descripcionProd;
    static double precioUni;
    static int cantProducto;
    static double indiceAjuste;
    JButton btnCliente, btnGuardar, btnCargarArticulo;
    static JButton btnEliminarTabla;
    JDateChooser calendario;
    static JScrollPane scroll;
    static JTable tabla = new JTable();
    static DefaultTableModel modelo;
    static int clienteID;
    JComboBox lista;


    static List<DtoCotizacionDetalle> listDto;

    public CotizacionPanel()throws Exception{
        listDto = new ArrayList<>();
        btnCliente = new JButton("Seleccione Cliente");
        btnCliente.setBounds(600, 20, 210, 30);
        btnCliente.addActionListener(e -> {
            try {
                FrameTablaClientesCotizacion tablaClientes = new FrameTablaClientesCotizacion();
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
        txtIndiceAjuste = new JTextField("0.0");
        txtIndiceAjuste.setBounds(140, 55, 150, 30);



        lblFechaHora = new JLabel("Fecha-Hora:");
        lblFechaHora.setBounds(15, 90, 100, 30);
        calendario = new JDateChooser();
        calendario.setBounds(140, 90, 150, 30);


        lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(15, 125, 100, 30);

        lblValorFinal1 = new JLabel("Precio final: ");
        lblValorFinal1.setBounds(680,700,100,30);

        lblValorFinal2 = new JLabel();
        lblValorFinal2.setBounds(770,700,100,30);



        lista = new JComboBox();
        lista.addItem("Aceptada");
        lista.addItem("Pendiente");
        lista.addItem("Rechazado");
        lista.setBounds(140, 125, 120, 30);

        scroll = new JScrollPane();
        scroll.setBounds(15, 270, 800, 400);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(300, 160, 100, 30);
        btnGuardar.addActionListener(e->{

            Cotizacion cotizacion = new Cotizacion(0,clienteID,calendario.getDate(),0,1);
            Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
            Cotizacion_DetalleDB cotizacionDetalleDB = new Cotizacion_DetalleDB(Conexion.conectar());
            try {
                cotizacionCabeceraDB.insertarCotizacion(cotizacion);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            int idCabecera = 0;
            try {
                idCabecera = cotizacionCabeceraDB.obtenerIdCabecera();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            List<Cotizacion_detalle> cotDetalle = new ArrayList<>();
            for(int i = 0; i < listDto.size();i++){
                int cantidad = cantProducto;
                double precioUnit = precioUni;
                double precioAjust = 0;
                int producId = productoId;
                try {
                     idCabecera = cotizacionCabeceraDB.obtenerIdCabecera();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                Cotizacion_detalle cotizacionDetalle = new Cotizacion_detalle(0,cantidad,precioUnit,precioAjust,producId,idCabecera);
                cotDetalle.add(cotizacionDetalle);

            }

            try {
                cotizacionDetalleDB.insertarCotizacionDetalleLista(cotDetalle);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            JOptionPane.showMessageDialog(null,"Se creo exitosamente cotizacion");
            listDto.clear();
            try {
                ConstruirTablaCotizacion(0,null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                ConstruirTablaCotizacion(0,null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        btnCargarArticulo = new JButton("Cargar articulo");
        btnCargarArticulo.setBounds(710, 220, 130, 40);
        btnCargarArticulo.addActionListener(e ->{
            try {
                FrameTablaProductosCotizacion frameTablaProductosCotizacion = new FrameTablaProductosCotizacion();
                frameTablaProductosCotizacion.setVisible(true);
                String valorJX = txtIndiceAjuste.getText();
                indiceAjuste = Double.parseDouble(valorJX);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        add(lblValorFinal2);
        add(lblValorFinal1);
        add(btnCliente);
        add(txtCliente);
        add(buscarCliente);
        add(lblId);
        add(lblNumero);
        add(txtIndiceAjuste);
        add(calendario);
        add(lblFechaHora);
        add(lblEstado);
        add(lista);
        add(scroll);
        add(btnGuardar);
        add(btnCargarArticulo);
        scroll.setViewportView(tabla);
        setLayout(null);
    }



    public static void ConstruirTablaCotizacion(int i, Object o) throws Exception{
        String[] titulo = {"Codigo producto", "Cantidad", "Descripcion", "Precio unit.", "Precio total"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);


        scroll.setViewportView(tabla);
        JTableHeader titulo1 = tabla.getTableHeader();
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
       // ajustarAnchoColumnas();
    }


    private static String[][] obtenerMatriz() throws Exception{

      String[][] matrizInfo = new String[listDto.size()][6];
        for(int i=0; i < listDto.size(); i++){
            matrizInfo[i][0] = listDto.get(i).getCodigo_producto() + "";
            matrizInfo[i][1] = listDto.get(i).getCantidad_producto() + "";
            matrizInfo[i][2] = listDto.get(i).getNombre_producto() + "";
            matrizInfo[i][3] = listDto.get(i).getPrecio_unitario() + "";
            matrizInfo[i][4] = listDto.get(i).getPrecio_total() + "";

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




