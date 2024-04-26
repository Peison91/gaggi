package Productos;
import Utiles.Conexion;
import database.ProductosDB;
import model.Productos;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;

public class ProductosEditar extends JPanel{
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarProducto;
    JButton btnModificarProd;
    JButton btnEliminarProd;
    JButton btnNuevoProduc;
    static int idProducto;



    public ProductosEditar() throws Exception {
        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Descripción");
        filtro.addItem("Código");
        filtro.addItem("Abreviatura");
        filtro.setBounds(20,20,100,30);
        setBackground(new Color(214,214,214));
        buscarProducto = new JTextField(15);
        buscarProducto.setBounds(150,20,350,30);
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

        btnNuevoProduc = new JButton("Nuevo",new ImageIcon("src/imagenes/nuevo.png"));
        btnNuevoProduc.setBounds(550,20,130,30);
        btnNuevoProduc.addActionListener(e->{
                    try {
                        VentanaNuevoProductoFrame ventanaNuevo = new VentanaNuevoProductoFrame();
                        ventanaNuevo.setVisible(true);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                );

        btnModificarProd = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnModificarProd.setBounds(738, 20, 130, 30);
        btnEliminarProd = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnEliminarProd.setBounds(926, 20, 130,30);
        btnModificarProd.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
            } else {
                try {
                    VentanaEditarProductoFrame ventanaEditar = new VentanaEditarProductoFrame();
                    ventanaEditar.setVisible(true);
                    idProducto = Integer.parseInt(tabla.getValueAt(fila,0).toString());
                    VentanaEditarProductoPanel.txtDescr.setText(tabla.getValueAt(fila, 1).toString())  ;
                    VentanaEditarProductoPanel.txtCodProd.setText(tabla.getValueAt(fila, 2).toString());
                    VentanaEditarProductoPanel.txtAbrevProd.setText(tabla.getValueAt(fila,3).toString());
                    VentanaEditarProductoPanel.txtPreciounitProd.setText(tabla.getValueAt(fila,4).toString());
                    VentanaEditarProductoPanel.txtStockMin.setText(tabla.getValueAt(fila,5).toString());
                    VentanaEditarProductoPanel.txtStock.setText(tabla.getValueAt(fila,6).toString());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnEliminarProd.addActionListener(e ->{
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int id = Integer.parseInt((String) tabla.getValueAt(fila, 0));
                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?", "Importante", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        ProductosDB productosDB = new ProductosDB(Conexion.conectar());
                        productosDB.borrarProducto(id);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Producto eliminado");
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
        add(buscarProducto);
        add(filtro);
        add(btnModificarProd);
        add(btnEliminarProd);
        add(btnNuevoProduc);
        scroll.setViewportView(tabla);
        add(scroll);
        setLayout(null);
    }
    public void ConstruirTabla(int opBuscar, String valor) throws Exception{
        String[] titulo = {"ID","Descripción", "Código", "Abreviatura", "Precio unit.", "Stock mín.", "Stock"};
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