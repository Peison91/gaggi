package Proveedores;
import Utiles.Conexion;
import database.ProveedoresDB;
import model.Proveedores;
import javax.swing.UIManager;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ProveedoresEditar extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarProveedor;
    JButton btnModificarProveedor;
    JButton btnEliminarProveedor;


    public ProveedoresEditar() throws Exception{
        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Nombre");
        filtro.addItem("CUIT");
        filtro.setBounds(20,20,100,30);
        setBackground(new Color(214,214,214));
        buscarProveedor = new JTextField(15);
        buscarProveedor.setBounds(150,20,350,30);
        buscarProveedor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int opcion = filtro.getSelectedIndex();
                String valorBuscar = buscarProveedor.getText();
                try {
                    ConstruirTabla(opcion, valorBuscar);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnModificarProveedor = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnModificarProveedor.setBounds(550, 20, 130, 30);
        btnEliminarProveedor = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnEliminarProveedor.setBounds(738, 20, 130,30);
        btnModificarProveedor.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor");
            } else {
                Proveedores proveedores = new Proveedores();

                proveedores.setId(Integer.parseInt(tabla.getValueAt(fila, 0).toString()));
                proveedores.setNombre(tabla.getValueAt(fila, 1).toString());
                proveedores.setCuit(tabla.getValueAt(fila, 2).toString());
                proveedores.setDireccion(tabla.getValueAt(fila, 3).toString());
                proveedores.setCiudad(tabla.getValueAt(fila, 4).toString());

                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea modificar?", "Aviso", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        ProveedoresDB proveedoresDB = new ProveedoresDB(Conexion.conectar());
                        proveedoresDB.actualizarProveedores(proveedores);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        btnEliminarProveedor.addActionListener(e ->{
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int id = Integer.parseInt((String) tabla.getValueAt(fila, 0));
                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?", "Importante", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        ProveedoresDB proveedoresDB = new ProveedoresDB(Conexion.conectar());
                        proveedoresDB.borrarProveedor(id);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado");
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
        add(buscarProveedor);
        add(filtro);
        add(btnModificarProveedor);
        add(btnEliminarProveedor);
        scroll.setViewportView(tabla);
        add(scroll);
        setLayout(null);
    }
    public void ConstruirTabla(int opBuscar, String valor) throws Exception{
        String[] titulo = {"ID","Nombre", "CUIT", "Dirección", "Ciudad", "CBU/Alias"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        ajustarAnchoColumnas();
        TableRowSorter tr = new TableRowSorter<>(modelo);
        tabla.setRowSorter(tr);
        if(valor == null){
            tr.setRowFilter(RowFilter.regexFilter(buscarProveedor.getText(), 0));
        }else{
            tr.setRowFilter(RowFilter.regexFilter(buscarProveedor.getText(), opBuscar));
        }
        JTableHeader titulo1 = tabla.getTableHeader();
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
    }
    private String[][] obtenerMatriz() throws Exception{
        ProveedoresDB proveedoresDB = new ProveedoresDB(Conexion.conectar());
        List<Proveedores> lstProveedores = proveedoresDB.todosProveedores();
        String[][] matrizInfo = new String[lstProveedores.size()][6];
        for(int i=0; i < lstProveedores.size(); i++){
            matrizInfo[i][0] = lstProveedores.get(i).getId() + "";
            matrizInfo[i][1] = lstProveedores.get(i).getNombre() + "";
            matrizInfo[i][2] = lstProveedores.get(i).getCuit() + "";
            matrizInfo[i][3] = lstProveedores.get(i).getDireccion() + "";
            matrizInfo[i][4] = lstProveedores.get(i).getCiudad() + "";
            matrizInfo[i][5] = lstProveedores.get(i).getCbu() + "";
        }
        return matrizInfo;
    }
    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {80, 280, 125, 125, 125, 125};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }
}
