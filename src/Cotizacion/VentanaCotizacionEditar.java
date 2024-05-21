package Cotizacion;

import DTO.DtoCotizacionDetalle;
import Utiles.Conexion;
import com.toedter.calendar.JDateChooser;
import database.Cotizacion_CabeceraDB;
import database.Cotizacion_DetalleDB;
import database.ProductosDB;
import model.Cotizacion;
import model.Cotizacion_detalle;
import model.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaCotizacionEditar extends JFrame {
    private Cotizacion cotizacion;
    private Cotizacion_detalle cotizacionDetalle;
    private DtoCotizacionDetalle dtoCotizacion;
    private int id_seleccionado;
    public VentanaCotizacionEditar(int id_seleccionado) throws Exception {
        this.id_seleccionado = id_seleccionado;
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
        static JLabel lblValorFinal2;
        static JTextField txtIndiceAjuste;
        static JTextField txtCliente;
        static int productId;
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
        static List<DtoCotizacionDetalle> listaDtoEditar = new ArrayList<>();

        static List<DtoCotizacionDetalle> listDto;

        public PanelEditarCotizacion()throws Exception{
            obtenerCotizacionCabecera();
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
            txtIndiceAjuste = new JTextField("0.0");
            txtIndiceAjuste.setBounds(140, 55, 150, 30);
            txtIndiceAjuste.setText(String.valueOf(cotizacion.getIndice_ajuste()));

            lblFechaHora = new JLabel("Fecha-Hora:");
            lblFechaHora.setBounds(15, 90, 100, 30);
            calendario = new JDateChooser();
            calendario.setBounds(140, 90, 150, 30);
            calendario.setDate(cotizacion.getFecha_cotizacion());

            lblEstado = new JLabel("Estado:");
            lblEstado.setBounds(15, 125, 100, 30);

            lblValorFinal1 = new JLabel("Precio final: ");
            lblValorFinal1.setBounds(660,520,100,30);

            lblValorFinal2 = new JLabel();
            lblValorFinal2.setBounds(770,520,100,30);

            lista = new JComboBox();
            lista.addItem("Aceptada");
            lista.addItem("Pendiente");
            lista.addItem("Rechazado");
            lista.setBounds(140, 125, 120, 30);

            scroll = new JScrollPane();
            scroll.setBounds(15, 200, 800, 300);

            btnGuardar = new JButton("Guardar",  new ImageIcon("src/imagenes/GuardarTodo.png"));
            btnGuardar.setBounds(355, 520, 150, 40);
            btnGuardar.addActionListener(e->{
                Cotizacion cotizacion = new Cotizacion(0,clienteID,calendario.getDate(),0, lista.getSelectedIndex());
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
                    int producId = productId;
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
                JOptionPane.showMessageDialog(null,"Cotización actualizada");
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

            btnCargarArticulo = new JButton("Cargar productos", new ImageIcon("src/imagenes/nuevo.png"));
            btnCargarArticulo.setBounds(325, 160, 200, 30);
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


            try {
                ConstruirTablaCotizacion(0,null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

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
            String[] titulo = {"Código producto", "Descripción", "Cantidad", "Precio unit.", "Precio total"};
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

            for(Cotizacion_detalle cotizacion : lstCotizacion){
                Productos producto = productosDB.consultaProducto(cotizacion.getProducto_id());

                int codProducto = cotizacion.getProducto_id();
                int cant_prod = cotizacion.getCantidad();
                String nomb_prod = producto.getDescripcion();
                double precUni = cotizacion.getPrecio_unitario();
                double precioTotal = producto.getPrecio() * cant_prod;


                dtoCotizacion = new DtoCotizacionDetalle(codProducto,cant_prod,nomb_prod,precUni,precioTotal);
                listaDtoEditar.add(dtoCotizacion);
            }

            return listaDtoEditar;



            //query join tablas
        /*SELECT * FROM gaggidb.cotizacion_cabecera co
        join clientes cli on co.cliente_id = cli.id;*/
        }
    }

}
