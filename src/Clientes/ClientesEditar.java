package Clientes;
import Utiles.Conexion;
import com.gaggi.database.ClientesDB;
import com.gaggi.database.DBConection;
import model.Clientes;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ClientesEditar extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();
    JTextField buscarCliente;
    JButton btnModificarCliente;
    JButton btnEliminarCliente;

    public ClientesEditar() throws Exception {
        Conexion.conectar();

        JComboBox<String> filtro = new JComboBox<>();
        filtro.addItem("ID");
        filtro.addItem("Nombre");
        filtro.addItem("CUIT");
        filtro.setBounds(20,20,100,30);
        setBackground(new Color(214,214,214));
        buscarCliente = new JTextField(15);
        buscarCliente.setBounds(150,20,350,30);
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
        btnModificarCliente = new JButton("Modificar", new ImageIcon("src/imagenes/modificar.png"));
        btnModificarCliente.setBounds(550, 20, 130, 30);
        btnEliminarCliente = new JButton("Eliminar", new ImageIcon("src/imagenes/borrar.png"));
        btnEliminarCliente.setBounds(738, 20, 130,30);
        btnModificarCliente.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
            } else {
                Clientes clientes = new Clientes();
                clientes.setId(Integer.parseInt(tabla.getValueAt(fila, 0).toString()));
                clientes.setNombre(tabla.getValueAt(fila, 1).toString());
                clientes.setCuit(tabla.getValueAt(fila, 2).toString());
                clientes.setDireccion(tabla.getValueAt(fila, 3).toString());
                clientes.setEmail(tabla.getValueAt(fila, 4).toString());
                clientes.setTelefono(tabla.getValueAt(fila, 5).toString());
                UIManager.put("OptionPane.yesButtonText", "Si");
                UIManager.put("OptionPane.noButtonText", "No");
                int i = JOptionPane.showConfirmDialog(null, "¿Seguro que desea modificar?", "Aviso", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
                        clientesDB.actualizarCliente(clientes);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        btnEliminarCliente.addActionListener(e ->{
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
                    }                    JOptionPane.showMessageDialog(null, "Cliente eliminado");
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
        add(buscarCliente);
        add(filtro);
        add(btnModificarCliente);
        add(btnEliminarCliente);
        scroll.setViewportView(tabla);
        add(scroll);
        setLayout(null);
    }
    public void ConstruirTabla(int opBuscar, String valor) throws Exception{
        String[] titulo = {"ID","Nombre", "CUIT", "Dirección", "Email", "Teléfono"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        ajustarAnchoColumnas();
        TableRowSorter tr = new TableRowSorter<>(modelo);
        tabla.setRowSorter(tr);
        if(valor == null){
            tr.setRowFilter(RowFilter.regexFilter(buscarCliente.getText(), 0));
        }else{
            tr.setRowFilter(RowFilter.regexFilter(buscarCliente.getText(), opBuscar));
        }
        JTableHeader titulo1 = tabla.getTableHeader();
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
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

    private String[][] obtenerMatriz() throws Exception{

        DBConection conecc = new DBConection("localhost", "root", "root");
        conecc.conectar();
        ClientesDB clientesDB = new ClientesDB(conecc);

        List<Clientes> lstClientes = clientesDB.todosClientes();
        String[][] matrizInfo = new String[lstClientes.size()][6];
        for(int i=0; i < lstClientes.size(); i++){
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
