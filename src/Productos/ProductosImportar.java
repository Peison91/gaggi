package Productos;

import Utiles.Conexion;
import com.gaggi.database.DBConection;
import com.gaggi.database.ProductosDB;
import helper.GestionaExcel;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductosImportar extends JPanel {
    JButton importarArchivo;
    JButton procesarArchivo;
    private File archivoElegido;
    GestionaExcel gestionaExcel = new GestionaExcel();
    public ProductosImportar() {
        importarArchivo = new JButton("Importar archivo", new ImageIcon("src/imagenes/import.png"));
        importarArchivo.setBounds(100,40,200,40);
        importarArchivo.addActionListener(e-> importarArchivo());
        procesarArchivo = new JButton("Procesar archivo", new ImageIcon("src/imagenes/procesopng.png"));
        procesarArchivo.setBounds(100,100,200,40);
        procesarArchivo.addActionListener(e-> {
            try {
                gestionaExcel.EjecutarProceso(archivoElegido.toString());
                String mensajeCargaModif = "Se ingresaron : " + gestionaExcel.getCantidadProducIngresados() + "\nSe modificaron : " + gestionaExcel.getCantidadProducModificados();
                JOptionPane.showMessageDialog(this, "Éxito al cargar archivo" + "\n" + mensajeCargaModif , "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar archivo", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar archivo", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                throw new RuntimeException(ex);
            }
        });
        add(importarArchivo);
        add(procesarArchivo);
        setLayout(null);

    }
    private void importarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel 2007 o superiores", "xlsx");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoElegido = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Archivo seleccionado", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
