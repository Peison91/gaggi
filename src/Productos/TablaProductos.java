package Productos;
import com.gaggi.database.DBConection;
import com.gaggi.database.ProductosDB;
import com.gaggi.model.Productos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TablaProductos extends JPanel {
    JScrollPane scroll = new JScrollPane();
    JTable tabla = new JTable();
    DefaultTableModel modelo = new DefaultTableModel();

    public TablaProductos() throws Exception {
        tabla.setBounds(20,90,850, 350);
        scroll.setBounds(0,0,850,350);
        ConstruirTabla();
        add(scroll);
        setLayout(null);
    }
    public void ConstruirTabla() throws Exception{
        String[] titulo = {"ID","Descripción", "Código", "Abreviatura", "Precio unit.", "Stock mín.", "Stock"};
        String[][] informacion = obtenerMatriz();
        modelo = new DefaultTableModel(informacion, titulo);
        tabla.setModel(modelo);
        scroll.setViewportView(tabla);
        JTableHeader titulo1 = tabla.getTableHeader();
        titulo1.setBackground(new Color(236, 126, 29));
        titulo1.setFont(new Font("Calibri", Font.BOLD, 14));
    }
    private String[][] obtenerMatriz() throws Exception{
        DBConection conecc = new DBConection("localhost", "root", "selfa");
        conecc.conectar();
        ProductosDB productosDB = new ProductosDB(conecc);
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
