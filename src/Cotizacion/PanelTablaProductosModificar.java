package Cotizacion;

import DTO.DtoCotizacionDetalle;
import Utiles.Conexion;
import database.ProductosDB;
import model.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static Cotizacion.CotizacionPanel.listDto;
import static Cotizacion.VentanaCotizacionEditar.PanelEditarCotizacion.*;


public class PanelTablaProductosModificar extends JPanel {

    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarProducto;
    JButton btnSelecProducto;
    double valorAjusteArt;
    double valorAjusteTotal;

    Productos producto;

    public PanelTablaProductosModificar() throws Exception {

        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Nombre");
        filtro.addItem("Codigo");
        btnSelecProducto = new JButton("Seleccionar");
        btnSelecProducto.setBounds(520, 20, 120, 30);
        btnSelecProducto.addActionListener(e -> {

            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada != -1) {

                double ajustArt;
                double ajustPreTot;

                int idProd = Integer.parseInt(tabla.getValueAt(filaSeleccionada,0).toString());
                String descripcion =  tabla.getValueAt(filaSeleccionada,1).toString();
                double precioU = Double.parseDouble(tabla.getValueAt(filaSeleccionada,4).toString());

                ProductosDB productosDB = new ProductosDB(Conexion.conectar());
                try {
                    producto = productosDB.consultaProducto(idProd);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                String cantidad = JOptionPane.showInputDialog(null,"Ingrese cantidad");
                int cantidadArt = Integer.parseInt(cantidad);

                if(cantidadArt > producto.getStock()){
                    JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", JOptionPane.ERROR_MESSAGE);

                }else if(cantidadArt <= producto.getStock()){
                    cantProductoEditar = cantidadArt;
                    double precioTotal = obtenerPrecioFinalArticulos(precioU,cantidadArt);

                    if(indiceAjusteEditar< 0){
                        ajustArt = PrecioArticuloConAjuste(precioU,indiceAjusteEditar);
                        ajustPreTot = PrecioArticuloConAjuste(precioTotal,indiceAjusteEditar);
                        valorAjusteArt = ajustArt + precioU ;
                        valorAjusteTotal = ajustPreTot + precioTotal ;
                        DtoCotizacionDetalle dto = new DtoCotizacionDetalle(idProd,cantidadArt,descripcion,valorAjusteArt,valorAjusteTotal);
                        List<DtoCotizacionDetalle> listaDtoCot = listaDtoEditar;
                        listaDtoCot.add(dto);
                        listDtoEditarCarga.add(dto);
                        listaDtoEditar = listaDtoCot;
                    }else if(indiceAjusteEditar > 0){
                        valorAjusteArt = precioU + PrecioArticuloConAjuste(precioU,indiceAjusteEditar);
                        valorAjusteTotal = precioTotal + PrecioArticuloConAjuste(precioTotal,indiceAjusteEditar);
                        DtoCotizacionDetalle dto = new DtoCotizacionDetalle(idProd,cantidadArt,descripcion,valorAjusteArt,valorAjusteTotal);
                        List<DtoCotizacionDetalle> listaDtoCot = listaDtoEditar;
                        listaDtoCot.add(dto);
                        listDtoEditarCarga.add(dto);
                        listaDtoEditar = listaDtoCot;
                    }else{
                        DtoCotizacionDetalle dto = new DtoCotizacionDetalle(idProd,cantidadArt,descripcion,precioU,precioTotal);
                        List<DtoCotizacionDetalle> listaDtoCot = listaDtoEditar;
                        listaDtoCot.add(dto);
                       listDtoEditarCarga.add(dto);
                        listaDtoEditar = listaDtoCot;
                    }

                    try {
                        ConstruirTablaCotizacionEditar(0,null);

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                   double sumaPrecios = PrecioFinalCotizacion();
                    String sumaPreciosString = String.valueOf(sumaPrecios);
                    lblValorFinalEditar.setText(sumaPreciosString);

                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PanelTablaProductosModificar.this);
                    frame.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un articulo de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        filtro.setBounds(20, 20, 100, 30);
        buscarProducto = new JTextField(15);
        buscarProducto.setBounds(150, 20, 350, 30);
        buscarProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int opcion = filtro.getSelectedIndex();
                String valorBuscar = buscarProducto.getText();
                try {
                    ConstruirTabla(opcion, valorBuscar);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        ConstruirTabla(0, null);
        scroll.setBounds(20, 100, 600, 200);
        add(buscarProducto);
        add(filtro);
        scroll.setViewportView(tabla);
        add(scroll);
        add(btnSelecProducto);
        setLayout(null);

    }
    public double obtenerPrecioFinalArticulos(double precio, int cantidad){
        double precioFianl = precio * cantidad;
        return Math.round(precioFianl * 100.0) / 100.0;
    }

    public double PrecioFinalCotizacion(){
        double valorFinal = 0;
        for(DtoCotizacionDetalle producto : listaDtoEditar){
            valorFinal += producto.getPrecio_total();
        }
        return Math.round(valorFinal* 100.0) / 100.0;
    }

    public double PrecioArticuloConAjuste(double valor,double ajuste){
        double precArtAjustado = valor * ajuste / 100;

        return Math.round(precArtAjustado* 100.0) / 100.0;
    }


    public void ConstruirTabla(int i, Object o) throws Exception{
        String[] titulo = {"ID","Descripción", "Código", "Abreviatura", "Precio unit.", "Stock mín.", "Stock",};
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


