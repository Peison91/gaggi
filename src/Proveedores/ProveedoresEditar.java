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
    JButton btnNuevoProveedor;
    JButton btnModificarProveedor;
    JButton btnEliminarProveedor;
    static int idCliente;
    private VentanaProveedoresNuevoFrame ventanaProveedoresNuevoFrame;
    private VentanaProveedoresEditarFrame ventanaProveedoresEditarFrame;


    public ProveedoresEditar() throws Exception{
        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Nombre");
        filtro.addItem("CUIT");

        setBackground(new Color(214,214,214));
        buscarProveedor = new JTextField(15);
        buscarProveedor.setMinimumSize(new Dimension(500, 30));
        buscarProveedor.setPreferredSize(new Dimension(500, 30));
        buscarProveedor.setMaximumSize(new Dimension(500, 30));
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
        btnNuevoProveedor = new JButton("Nuevo" ,new ImageIcon("src/imagenes/nuevo.png"));
        btnNuevoProveedor.setPreferredSize(new Dimension(130, 30));
        btnNuevoProveedor.addActionListener(e->{
                    try {
                        if (ventanaProveedoresNuevoFrame == null) {
                            ventanaProveedoresNuevoFrame = new VentanaProveedoresNuevoFrame();
                        }
                        ventanaProveedoresNuevoFrame.setVisible(true);
                        ventanaProveedoresNuevoFrame.setResizable(false);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
        btnModificarProveedor = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnModificarProveedor.setPreferredSize(new Dimension(130, 30));
        btnEliminarProveedor = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnEliminarProveedor.setPreferredSize(new Dimension(130, 30));
        btnModificarProveedor.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor");
            } else {
                try{
                    if (ventanaProveedoresEditarFrame == null) {
                        ventanaProveedoresEditarFrame = new VentanaProveedoresEditarFrame();
                    }
                    ventanaProveedoresEditarFrame.setVisible(true);
                    ventanaProveedoresEditarFrame.setResizable(false);
                    idCliente = Integer.parseInt(tabla.getValueAt(fila,0).toString());
                    VentanaProveedoresEditarPanel.txtNombre.setText(tabla.getValueAt(fila,1).toString());
                    VentanaProveedoresEditarPanel.txtCuit.setText(tabla.getValueAt(fila,2).toString());
                    VentanaProveedoresEditarPanel.txtDireccion.setText(tabla.getValueAt(fila,3).toString());
                    VentanaProveedoresEditarPanel.txtCiudad.setText(tabla.getValueAt(fila,4).toString());
                    VentanaProveedoresEditarPanel.txtCBU.setText(tabla.getValueAt(fila,5).toString());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
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
        scroll.setViewportView(tabla);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(filtro, gbc);

        gbc.gridx = 1;
        add(buscarProveedor, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1.0;
        add(Box.createHorizontalGlue(), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0;
        add(btnNuevoProveedor, gbc);

        gbc.gridx = 4;
        add(btnModificarProveedor, gbc);

        gbc.gridx = 5;
        add(btnEliminarProveedor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scroll, gbc);
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
