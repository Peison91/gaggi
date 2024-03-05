package Factura;
import Utiles.Conexion;
import com.gaggi.database.ClientesDB;
import com.gaggi.database.DBConection;
import com.gaggi.model.Clientes;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class PanelTablaClientes extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarCliente;
    JButton seleccionarCliente;


    public PanelTablaClientes() throws Exception {
        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Nombre");
        filtro.addItem("CUIT");
        seleccionarCliente = new JButton("Seleccionar");
        seleccionarCliente.setBounds(520, 20, 120, 30);
        seleccionarCliente.addActionListener(e -> {
            int filaSeleccionada = tabla.getSelectedRow();
            if (filaSeleccionada != -1) {
                String idClienteSeleccionado = (String) tabla.getValueAt(filaSeleccionada, 0);
                String nombreClienteSeleccionado = (String) tabla.getValueAt(filaSeleccionada, 1);
                String cuitClienteSeleccionado = (String) tabla.getValueAt(filaSeleccionada, 2);
                String mensaje = "Cliente seleccionado:\nID: " + idClienteSeleccionado +
                        "\nNombre: " + nombreClienteSeleccionado +
                        "\nCUIT: " + cuitClienteSeleccionado;
                JOptionPane.showMessageDialog(null, mensaje, "Cliente Seleccionado", JOptionPane.INFORMATION_MESSAGE);
                String datoId = tabla.getValueAt(filaSeleccionada,0).toString();
                String datoNombre = tabla.getValueAt(filaSeleccionada,1).toString();
                String datoCUIT = tabla.getValueAt(filaSeleccionada,2).toString();
                PanelFactura.txtCliente.setText(datoId + " - " + datoNombre + " - " + datoCUIT);
                PanelFactura.clienteID = Integer.parseInt(datoId);
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        filtro.setBounds(20, 20, 100, 30);
        buscarCliente = new JTextField(15);
        buscarCliente.setBounds(150, 20, 350, 30);
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




        ConstruirTabla(0, null);
        scroll.setBounds(20, 100, 600, 200);
        add(buscarCliente);
        add(filtro);
        scroll.setViewportView(tabla);
        add(scroll);
        add(seleccionarCliente);
        setLayout(null);




    }

    public void ConstruirTabla(int opBuscar, String valor) throws Exception {
        String[] titulo = {"ID", "Nombre", "CUIT", "Dirección", "Email", "Teléfono"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        TableRowSorter tr = new TableRowSorter<>(modelo);
        tabla.setRowSorter(tr);
        if (valor == null) {
            tr.setRowFilter(RowFilter.regexFilter("", 0));
        } else {
            tr.setRowFilter(RowFilter.regexFilter(valor, opBuscar));
        }
        JTableHeader titulo1 = tabla.getTableHeader();
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
        ajustarAnchoColumnas();
    }

    private void ajustarAnchoColumnas() {
        TableColumnModel columnModel = tabla.getColumnModel();
        int columnCount = columnModel.getColumnCount();
        int[] columnWidths = {50, 200, 100, 250, 200, 100};
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

    private String[][] obtenerMatriz() throws Exception {
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
