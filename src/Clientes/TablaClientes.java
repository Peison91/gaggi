package Clientes;
import Utiles.Conexion;
import database.ClientesDB;
import model.Clientes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class TablaClientes extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();

    public TablaClientes() throws Exception {
        tabla.setBounds(20,90,850, 350);
        scroll.setBounds(0,0,850,350);
        ConstruirTabla();
        add(scroll);
        setLayout(null);
    }

    public void ConstruirTabla() throws Exception {
        String[] titulo = {"ID","Nombre", "CUIT", "Dirección", "Email", "Teléfono"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        scroll.setViewportView(tabla);
        JTableHeader titulo1 = tabla.getTableHeader();
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));

        // Ajustar dimensiones de las columnas
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
