package Proveedores;
import Utiles.Conexion;
import com.gaggi.database.DBConection;
import com.gaggi.database.ProveedoresDB;
import com.gaggi.model.Proveedores;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TablaProveedores extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();

    public TablaProveedores() throws Exception {
        tabla.setBounds(20,90,850, 350);
        scroll.setBounds(0,0,850,350);
        ConstruirTabla();
        add(scroll);
        setLayout(null);
    }
    public void ConstruirTabla() throws Exception{
        String[] titulo = {"ID","Nombre", "CUIT", "Dirección", "Ciudad", "CBU/Alias"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        scroll.setViewportView(tabla);
        JTableHeader titulo1 = tabla.getTableHeader();
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
}
