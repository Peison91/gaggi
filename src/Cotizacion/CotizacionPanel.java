package Cotizacion;
import Factura.FrameTablaClientes;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class CotizacionPanel extends JPanel {

    JLabel lblId, lblNumero, lblFechaHora, lblEstado, buscarCliente;
    JTextField txtNumero;
    static JTextField txtCliente;

    static int productoId;
    static String descripcionProd;
    static double precioUni;
    static int cantProducto;
    int sumaPrecioArticulos;
    JButton btnCliente, btnGuardar, btnCargarArticulo;
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



        lblFechaHora = new JLabel("Fecha-Hora:");
        lblFechaHora.setBounds(15, 90, 100, 30);
        calendario = new JDateChooser();
        calendario.setBounds(140, 90, 150, 30);


        lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(15, 125, 100, 30);

        lista = new JComboBox();
        lista.addItem("Aceptada");
        lista.addItem("Pendiente");
        lista.addItem("Rechazado");
        lista.setBounds(140, 125, 120, 30);

        scroll = new JScrollPane();
        scroll.setBounds(15, 270, 800, 400);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(300, 160, 100, 30);


        btnCargarArticulo = new JButton("Cargar articulo");
        btnCargarArticulo.setBounds(710, 220, 130, 40);
        btnCargarArticulo.addActionListener(e ->{
            try {
                FrameTablaProductosCotizacion frameTablaProductosCotizacion = new FrameTablaProductosCotizacion();
                frameTablaProductosCotizacion.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });



        add(btnCliente);
        add(txtCliente);
        add(buscarCliente);
        add(lblId);
        add(lblNumero);
        add(txtNumero);
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

    public double obtenerPrecioFinal(double precio,int cantidad){
        double precioFianl = precio * cantidad;
        return precioFianl;
    }
    }




