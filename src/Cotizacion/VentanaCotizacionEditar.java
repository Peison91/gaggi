package Cotizacion;

import DTO.DtoCotizacionDetalle;
import Utiles.Conexion;
import com.toedter.calendar.JDateChooser;
import database.Cotizacion_CabeceraDB;
import database.Cotizacion_DetalleDB;
import database.ProductosDB;
import helper.GestionaPDF;
import model.Cotizacion;
import model.Cotizacion_detalle;
import model.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Cotizacion.VentanaCotizacionEditar.PanelEditarCotizacion.listaDtoEditar;

public class VentanaCotizacionEditar extends JFrame {
    private Cotizacion cotizacion;
    private Cotizacion_detalle cotizacionDetalle;
    private DtoCotizacionDetalle dtoCotizacion;
    private int id_seleccionado;
    public VentanaCotizacionEditar(int id_seleccionado) throws Exception {
        this.id_seleccionado = id_seleccionado;
        System.out.println("valor id seleccionado: " + id_seleccionado);
        setTitle("Editar cotización");
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        miPantalla.getScreenSize();
        Dimension tamanoPantalla = miPantalla.getScreenSize();
        double alturaPantalla = tamanoPantalla.height;
        double anchoPantalla = tamanoPantalla.width;
        setSize((int) (anchoPantalla/1.62), (int) (alturaPantalla/1.16));
        setLocation(200, 40);
        Image icono = miPantalla.getImage("src/imagenes/gaggiicon.png");
        setIconImage(icono);
        PanelEditarCotizacion panelEditarCotizacion = new PanelEditarCotizacion();
        add(panelEditarCotizacion);
        setVisible(true);

    }
    class PanelEditarCotizacion extends JPanel{
        JLabel lblId, lblNumero, lblFechaHora, lblEstado, buscarCliente, lblValorFinal1;
        static JLabel lblValorFinalEditar;
        static JTextField txtIndiceAjusteEditar;
        static JTextField txtCliente;
        static int productId;
        static String descripcionProd;
        static double precioUni;
        static int cantProductoEditar;
        static double indiceAjusteEditar;
        JButton btnCliente, btnActualizar, btnCargarArticulo,btnEliminarArtTabla;
        static JButton btnEliminarTabla;
        JDateChooser calendario;
        static JScrollPane scroll;
        static JTable tabla = new JTable();
        static DefaultTableModel modelo;
        static int clienteID;
        JComboBox comboBoxEstado;
        static List<DtoCotizacionDetalle> listaDtoEditar = new ArrayList<>();
        static List<DtoCotizacionDetalle> listDtoEditarCarga = new ArrayList<>();
        List<Integer> listIdBorrar  = new ArrayList<>();
        HashMap<Integer,Integer> mapIdProductoIdCotizacionDetalle = new HashMap<>();


        public PanelEditarCotizacion()throws Exception{
            System.out.println(mapIdProductoIdCotizacionDetalle);
            obtenerCotizacionCabecera();
            listaDtoEditar.clear();
            obtenerCotizacionDetalle();



            btnCliente = new JButton("Seleccione cliente");
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
            buscarCliente = new JLabel("Cliente seleccionado:");
            buscarCliente.setBounds(15, 20, 250, 30);
            txtCliente = new JTextField(15);
            txtCliente.setBounds(140, 20, 250, 30);
            txtCliente.setEditable(true);
            txtCliente.setText(String.valueOf(cotizacion.getCliente_id()));

            lblNumero = new JLabel("Indice Ajuste:");
            lblNumero.setBounds(15, 55, 100, 30);
            txtIndiceAjusteEditar = new JTextField("0.0");
            txtIndiceAjusteEditar.setBounds(140, 55, 150, 30);
            txtIndiceAjusteEditar.setText(String.valueOf(cotizacion.getIndice_ajuste()));

            lblFechaHora = new JLabel("Fecha-Hora:");
            lblFechaHora.setBounds(15, 90, 100, 30);
            calendario = new JDateChooser();
            calendario.setBounds(140, 90, 150, 30);
            calendario.setDate(cotizacion.getFecha_cotizacion());

            lblEstado = new JLabel("Estado:");
            lblEstado.setBounds(15, 125, 100, 30);

            lblValorFinal1 = new JLabel("Precio final: ");
            lblValorFinal1.setBounds(660,520,100,30);

            lblValorFinalEditar = new JLabel();
            lblValorFinalEditar.setBounds(770,520,100,30);
            double sumaPrecios = PrecioFinalCotizacion();
            String sumaPreciosString = String.valueOf(sumaPrecios);
            lblValorFinalEditar.setText(sumaPreciosString);

            comboBoxEstado = new JComboBox();
            comboBoxEstado.addItem("Pendiente");
            comboBoxEstado.addItem("Aceptada");
            comboBoxEstado.addItem("Rechazado");
            comboBoxEstado.setBounds(140, 125, 120, 30);

            scroll = new JScrollPane();
            scroll.setBounds(15, 200, 800, 300);

            btnActualizar = new JButton("Actualizar",  new ImageIcon("src/imagenes/GuardarTodo.png"));
            btnActualizar.setBounds(355, 520, 150, 40);
            btnActualizar.addActionListener(e->{
                //Cotizacion cotizacion = new Cotizacion(0,clienteID,calendario.getDate(),0, comboBoxEstado.getSelectedIndex());
                Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
                Cotizacion_DetalleDB cotizacionDetalleDB = new Cotizacion_DetalleDB(Conexion.conectar());
                ProductosDB productosDB = new ProductosDB(Conexion.conectar());

                if(listIdBorrar.size() > 0){
                    for(int i = 0; i < listIdBorrar.size(); i++){
                        System.out.println(listIdBorrar.get(i));
                        try {
                            cotizacionDetalleDB.borrarCotizacionDetalle(listIdBorrar.get(i));
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                int cambioEstado;

                if (comboBoxEstado.getSelectedIndex() == 0){
                     cambioEstado = 1;
                    try {
                        List <Cotizacion_detalle> lstCotizacion = cotizacionCabeceraDB.consultarCotizacionDetalle(id_seleccionado);
                        int cantProduc;
                        int idProduc;
                        for(Cotizacion_detalle cotizacion : lstCotizacion){
                            cantProduc = cotizacion.getCantidad();
                            idProduc = cotizacion.getProducto_id();
                            productosDB.descontarStock(idProduc,cantProduc);
                        }
                        double precioU = 0;

                        System.out.println(listDtoEditarCarga.size());
                        if(listDtoEditarCarga.size() > 0){
                            for(DtoCotizacionDetalle dto : listDtoEditarCarga){
                                Cotizacion_detalle cotizacion = new Cotizacion_detalle();
                                System.out.println();

                                precioU = obtenerPrecio(dto.getCodigo_producto());

                                cotizacion.setCantidad(dto.getCantidad_producto());
                                cotizacion.setPrecio_unitario(precioU);
                                cotizacion.setPrecio_ajustado(dto.getPrecio_unitario());
                                cotizacion.setProducto_id(dto.getCodigo_producto());
                                cotizacion.setCotizacion_cabecera_id(id_seleccionado);

                                cotizacionDetalleDB.insertarCotizacionDetalle(cotizacion);

                            }
                        }

                        UIManager.put("OptionPane.yesButtonText", "Si");
                        UIManager.put("OptionPane.noButtonText", "No");
                        int i = JOptionPane.showConfirmDialog(null, "¿Crear y/o editar pdf?", "Importante", JOptionPane.YES_NO_OPTION);
                        if (i == 0) {
                            GestionaPDF gestionaPDF = new GestionaPDF();
                            gestionaPDF.generarPdf(id_seleccionado);
                        }


                        listDtoEditarCarga.clear();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                }else if(comboBoxEstado.getSelectedIndex() == 1){
                    cambioEstado = 2;
                    try {
                        cotizacionCabeceraDB.actualizarEstadoCotizacion(id_seleccionado,cambioEstado);
                        List <Cotizacion_detalle> lstCotizacion = cotizacionCabeceraDB.consultarCotizacionDetalle(id_seleccionado);
                        int cantProduc;
                        int idProduc;
                        for(Cotizacion_detalle cotizacion : lstCotizacion){
                            cantProduc = cotizacion.getCantidad();
                            idProduc = cotizacion.getProducto_id();
                            productosDB.descontarStock(idProduc,cantProduc);
                        }
                        double precioU = 0;

                        System.out.println(listDtoEditarCarga.size());
                        if(listDtoEditarCarga.size() > 0){
                            for(DtoCotizacionDetalle dto : listDtoEditarCarga){
                                Cotizacion_detalle cotizacion = new Cotizacion_detalle();
                                System.out.println();

                                precioU = obtenerPrecio(dto.getCodigo_producto());

                                cotizacion.setCantidad(dto.getCantidad_producto());
                                cotizacion.setPrecio_unitario(precioU);
                                cotizacion.setPrecio_ajustado(dto.getPrecio_unitario());
                                cotizacion.setProducto_id(dto.getCodigo_producto());
                                cotizacion.setCotizacion_cabecera_id(id_seleccionado);

                                cotizacionDetalleDB.insertarCotizacionDetalle(cotizacion);

                            }
                        }

                        UIManager.put("OptionPane.yesButtonText", "Si");
                        UIManager.put("OptionPane.noButtonText", "No");
                        int i = JOptionPane.showConfirmDialog(null, "¿Crear y/o editar pdf?", "Importante", JOptionPane.YES_NO_OPTION);
                        if (i == 0) {
                            GestionaPDF gestionaPDF = new GestionaPDF();
                            gestionaPDF.generarPdf(id_seleccionado);
                        }


                        listDtoEditarCarga.clear();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

                else{
                    cambioEstado = 3;
                    try {
                        cotizacionCabeceraDB.actualizarEstadoCotizacion(id_seleccionado,cambioEstado);

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

                System.out.println(cambioEstado);
                System.out.println(mapIdProductoIdCotizacionDetalle);


            });

            btnCargarArticulo = new JButton("Cargar productos", new ImageIcon("src/imagenes/nuevo.png"));
            btnCargarArticulo.setBounds(325, 160, 200, 30);
            btnCargarArticulo.addActionListener(e ->{
                try {
                    FramaTablaProductosModificiar framaTablaProductosModificiar = new FramaTablaProductosModificiar();
                    framaTablaProductosModificiar.setVisible(true);
                    String valorJX = txtIndiceAjusteEditar.getText();
                    indiceAjusteEditar = Double.parseDouble(valorJX);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            try {
                ConstruirTablaCotizacionEditar(0,null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            btnEliminarArtTabla = new JButton("Eliminar");
            btnEliminarArtTabla.setBounds(600,160,130,30);
            btnEliminarArtTabla.addActionListener(e ->{

                Cotizacion_DetalleDB cotizacionDetalleDB = new Cotizacion_DetalleDB(Conexion.conectar());
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un producto", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int id = Integer.parseInt((String) tabla.getValueAt(fila, 0));
                    System.out.println(id);
                    UIManager.put("OptionPane.yesButtonText", "Si");
                    UIManager.put("OptionPane.noButtonText", "No");
                    int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?", "Importante", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        try {
                            for(DtoCotizacionDetalle cotizDto : listaDtoEditar){
                                if(cotizDto.getCodigo_producto() == id){
                                    //listIdBorrar.add(id);
                                    listaDtoEditar.remove(cotizDto);
                                    if(mapIdProductoIdCotizacionDetalle.containsKey(id)){
                                        int idCotDet = mapIdProductoIdCotizacionDetalle.get(id);
                                        listIdBorrar.add(idCotDet);
                                        //System.out.println(listIdBorrar);
                                        mapIdProductoIdCotizacionDetalle.remove(id);
                                        //System.out.println(id);
                                        //System.out.println(mapIdProductoIdCotizacionDetalle);
                                    }

                                    break;
                                }
                            }
                            //cotizacionDetalleDB.borrarCotizacionDetalle(id);

                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
                try {
                    ConstruirTablaCotizacionEditar(0,null);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });


           // limpiarListaTabla();

            add(lblValorFinalEditar);
            add(lblValorFinal1);
            add(btnCliente);
            add(txtCliente);
            add(buscarCliente);
            add(lblId);
            add(lblNumero);
            add(txtIndiceAjusteEditar);
            add(calendario);
            add(lblFechaHora);
            add(lblEstado);
            add(comboBoxEstado);
            add(scroll);
            add(btnActualizar);
            add(btnCargarArticulo);
            add(btnEliminarArtTabla);
            scroll.setViewportView(tabla);
            setLayout(null);
        }

        public static void ConstruirTablaCotizacionEditar(int i, Object o) throws Exception{
            String[] titulo = {"ID", "Descripción", "Cantidad", "Precio unit.", "Precio total"};
            String[][] informacion = obtenerMatriz();
            modelo = new DefaultTableModel(informacion, titulo);
            tabla.setModel(modelo);
            scroll.setViewportView(tabla);
            JTableHeader titulo1 = tabla.getTableHeader();
            titulo1.setBackground(new Color(236, 126, 29));
            titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
        }


        private static String[][] obtenerMatriz() throws Exception{

            String[][] matrizInfo = new String[listaDtoEditar.size()][6];
            for(int i=0; i < listaDtoEditar.size(); i++){
                matrizInfo[i][0] = listaDtoEditar.get(i).getCodigo_producto() + "";
                matrizInfo[i][1] = listaDtoEditar.get(i).getNombre_producto() + "";
                matrizInfo[i][2] = listaDtoEditar.get(i).getCantidad_producto() + "";
                matrizInfo[i][3] = listaDtoEditar.get(i).getPrecio_unitario() + "";
                matrizInfo[i][4] = listaDtoEditar.get(i).getPrecio_total() + "";
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
        } private void obtenerCotizacionCabecera() throws Exception {
            Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
            cotizacion = cotizacionCabeceraDB.consultarCotizacion(id_seleccionado);
        }
        private List<DtoCotizacionDetalle> obtenerCotizacionDetalle() throws Exception {

            Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
            ProductosDB productosDB = new ProductosDB(Conexion.conectar());

            List <Cotizacion_detalle> lstCotizacion = cotizacionCabeceraDB.consultarCotizacionDetalle(id_seleccionado);
            /*for(Cotizacion_detalle lista : lstCotizacion){
                System.out.println("Id detalle " + lista.getId_cot_detalle() +" Cantidad " + lista.getCantidad() + " id Producto " + lista.getProducto_id() + " Precio " + lista.getPrecio_unitario());
            }*/
            for(Cotizacion_detalle cotizacion : lstCotizacion){

                Productos producto = productosDB.consultaProducto(cotizacion.getProducto_id());

                int idCotDetalle = cotizacion.getId_cot_detalle();
                int codProducto = cotizacion.getProducto_id();
                int cant_prod = cotizacion.getCantidad();
                String nomb_prod = producto.getDescripcion();
                double precUni = cotizacion.getPrecio_ajustado();
                double precioTotal = cotizacion.getPrecio_ajustado() * cant_prod;

                mapIdProductoIdCotizacionDetalle.put(codProducto,idCotDetalle);

                dtoCotizacion = new DtoCotizacionDetalle(codProducto,cant_prod,nomb_prod,precUni,precioTotal);
                listaDtoEditar.add(dtoCotizacion);
            }

            System.out.println(mapIdProductoIdCotizacionDetalle);

            return listaDtoEditar;

        }
        private void limpiarListaTabla(){
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(VentanaCotizacionEditar.this);
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent windowEvent) {

                }

                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    listaDtoEditar.clear();
                }

                @Override
                public void windowClosed(WindowEvent windowEvent) {

                }

                @Override
                public void windowIconified(WindowEvent windowEvent) {

                }

                @Override
                public void windowDeiconified(WindowEvent windowEvent) {

                }

                @Override
                public void windowActivated(WindowEvent windowEvent) {

                }

                @Override
                public void windowDeactivated(WindowEvent windowEvent) {

                }
            });

            }
        }
    public double PrecioFinalCotizacion(){
        double valorFinal = 0;
        for(DtoCotizacionDetalle producto : listaDtoEditar){
            valorFinal += producto.getPrecio_total();
        }
        return Math.round(valorFinal* 100.0) / 100.0;
    }

    public double obtenerPrecio(int producId) throws Exception {
        ProductosDB productosDB = new ProductosDB(Conexion.conectar());
        Productos producto = productosDB.consultaProducto(producId);
        double precio = producto.getPrecio();
        return precio;
    }
}


