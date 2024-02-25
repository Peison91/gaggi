package Productos;
import Utiles.Conexion;
import com.gaggi.database.DBConection;
import com.gaggi.database.ProductosDB;
import com.gaggi.model.Productos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;

import Utiles.Utiles;

public class ProductosEditar extends JPanel{
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarProducto;
    JButton btnModificarProd;
    JButton btnEliminarProd;


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
        btnModificarProd = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnModificarProd.setBounds(550, 20, 130, 50);
        btnEliminarProd = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnEliminarProd.setBounds(738, 20, 130,50);
        btnModificarProd.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
            } else {
                Productos producto = new Productos();

                producto.setId(Integer.parseInt(tabla.getValueAt(fila, 0).toString()));
                producto.setDescripcion(tabla.getValueAt(fila, 1).toString());
                producto.setCodigo(tabla.getValueAt(fila, 2).toString());
                producto.setAbreviatura(tabla.getValueAt(fila, 3).toString());
                //producto.setPrecio(Double.parseDouble(tabla.getValueAt(fila, 4).toString()));
                // validar carga de datos y mostrar aviso de error
                if(Utiles.isNumeric(tabla.getValueAt(fila, 4).toString())){
                    producto.setPrecio(Double.parseDouble(tabla.getValueAt(fila, 4).toString()));
                }else{
                    JOptionPane.showMessageDialog(null, "El precio no puede ser un texto", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
                //producto.setStock_minimo(Integer.parseInt(tabla.getValueAt(fila, 5).toString()));
                if(Utiles.isNumeric(tabla.getValueAt(fila, 5).toString())){
                    producto.setStock_minimo(Integer.parseInt(tabla.getValueAt(fila, 5).toString()));
                }
                else{
                    JOptionPane.showMessageDialog(null, "El stock debe ser un número entero", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
                //producto.setStock(Integer.parseInt(tabla.getValueAt(fila, 6).toString()));
                if(Utiles.isNumeric(tabla.getValueAt(fila, 6).toString())){
                    producto.setStock(Integer.parseInt(tabla.getValueAt(fila, 6).toString()));
                }
                else{
                    JOptionPane.showMessageDialog(null, "El stock debe ser un número entero", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea modificar?", "Aviso", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        ProductosDB productosDB = new ProductosDB(Conexion.conectar());
                        productosDB.actualizarProducto(producto);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
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
        scroll.setBounds(20,100,850,350);
        add(buscarProducto);
        add(filtro);
        add(btnModificarProd);
        add(btnEliminarProd);
        scroll.setViewportView(tabla);
        add(scroll);
        setLayout(null);
    }
    public void ConstruirTabla(int opBuscar, String valor) throws Exception{
        String[] titulo = {"ID","Descripción", "Código", "Abreviatura", "Precio unit.", "Stock mín.", "Stock"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
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
}