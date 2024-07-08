package Clientes;

import Utiles.Conexion;
import database.ClientesDB;
import model.Clientes;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ClientesEditar extends JPanel {
    static JScrollPane scroll = new JScrollPane();
    static JTable tabla = new JTable();
    static DefaultTableModel modelo = new DefaultTableModel();
    static JTextField buscarCliente;
    JButton btnModificarCliente;
    JButton btnEliminarCliente;
    JButton btnNuevoCliente;
    static int idCliente;

    public ClientesEditar() throws Exception {
        Conexion.conectar();

        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Nombre");
        filtro.addItem("CUIT");

        buscarCliente = new JTextField(15);
        buscarCliente.setMinimumSize(new Dimension(500, 30));
        buscarCliente.setPreferredSize(new Dimension(500, 30));
        buscarCliente.setMaximumSize(new Dimension(500, 30));
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

        btnNuevoCliente = new JButton("Nuevo", new ImageIcon("src/imagenes/nuevo.png"));
        btnNuevoCliente.setPreferredSize(new Dimension(130, 30));
        btnNuevoCliente.addActionListener(e -> {
            try {
                VentanaClienteNuevoFrame ventana = new VentanaClienteNuevoFrame();
                ventana.setResizable(false);
                ventana.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnModificarCliente = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnModificarCliente.setPreferredSize(new Dimension(130, 30));
        btnModificarCliente.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
            } else {
                try {
                    VentanaClienteEditarFrame ventana = new VentanaClienteEditarFrame();
                    ventana.setVisible(true);
                    ventana.setResizable(false);
                    idCliente = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
                    VentanaClienteEditarPanel.txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                    VentanaClienteEditarPanel.txtCuit.setText(tabla.getValueAt(fila, 2).toString());
                    VentanaClienteEditarPanel.txtDireccion.setText(tabla.getValueAt(fila, 3).toString());
                    VentanaClienteEditarPanel.txtEmail.setText(tabla.getValueAt(fila, 4).toString());
                    VentanaClienteEditarPanel.txtTelefono.setText(tabla.getValueAt(fila, 5).toString());

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnEliminarCliente = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnEliminarCliente.setPreferredSize(new Dimension(130, 30));
        btnEliminarCliente.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int id = Integer.parseInt((String) tabla.getValueAt(fila, 0));
                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?", "Importante", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
                        clientesDB.borrarCliente(id);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Cliente eliminado");
                }
            }
            try {
                ConstruirTabla(0, null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        ConstruirTabla(0, null);
        scroll.setViewportView(tabla);

        // Configurar layout del panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Agregar filtro JComboBox y JTextField al lado izquierdo superior
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(filtro, gbc);

        gbc.gridx = 1;
        add(buscarCliente, gbc);

        // Espacio de relleno entre JTextField y botones
        gbc.gridx = 2;
        gbc.weightx = 1.0;
        add(Box.createHorizontalGlue(), gbc);

        // Agregar botones al lado derecho superior
        gbc.gridx = 3;
        gbc.weightx = 0;
        add(btnNuevoCliente, gbc);

        gbc.gridx = 4;
        add(btnModificarCliente, gbc);

        gbc.gridx = 5;
        add(btnEliminarCliente, gbc);

        // Configurar JScrollPane para la tabla
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scroll, gbc);
    }

    public static void ConstruirTabla(int opBuscar, String valor) throws Exception {
        String[] titulo = {"ID", "Nombre", "CUIT", "Dirección", "Email", "Teléfono"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        ajustarAnchoColumnas();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelo);
        tabla.setRowSorter(tr);
        if (valor == null) {
            tr.setRowFilter(RowFilter.regexFilter(buscarCliente.getText(), 0));
        } else {
            tr.setRowFilter(RowFilter.regexFilter(buscarCliente.getText(), opBuscar));
        }
        JTableHeader titulo1 = tabla.getTableHeader();
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    private static void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {50, 200, 100, 250, 200, 100};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

    private static String[][] obtenerMatriz() throws Exception {
        ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
        List<Clientes> lstClientes = clientesDB.todosClientes();
        String[][] matrizInfo = new String[lstClientes.size()][6];
        for (int i = 0; i < lstClientes.size(); i++) {
            matrizInfo[i][0] = lstClientes.get(i).getId() + "";
            matrizInfo[i][1] = lstClientes.get(i).getNombre() + "";
            matrizInfo[i][2] = lstClientes.get(i).getCuit() + "";
            matrizInfo[i][3] = lstClientes.get(i).getDireccion() + "";
            matrizInfo[i][4] = lstClientes.get(i).getEmail() + "";
            matrizInfo[i][5] = lstClientes.get(i).getTelefono() + "";
        }
        return matrizInfo;
    }
}
