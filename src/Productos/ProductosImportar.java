package Productos;

import Utiles.Conexion;
import com.gaggi.database.DBConection;
import com.gaggi.database.ProductosDB;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductosImportar extends JPanel {
    JButton importarArchivo;
    JButton procesarArchivo;
    private File archivoElegido;
    public ProductosImportar() {
        importarArchivo = new JButton("Importar archivo", new ImageIcon("src/imagenes/import.png"));
        importarArchivo.setBounds(100,40,200,40);
        importarArchivo.addActionListener(e-> importarArchivo());
        procesarArchivo = new JButton("Procesar archivo", new ImageIcon("src/imagenes/procesopng.png"));
        procesarArchivo.setBounds(100,100,200,40);
        procesarArchivo.addActionListener(e-> {
            try {
                procesarArchivoSeleccionado();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(importarArchivo);
        add(procesarArchivo);
        setLayout(null);

    }
    private void importarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoElegido = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Archivo seleccionado", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void procesarArchivoSeleccionado() throws IOException {
        Conexion.conectar();
        if (archivoElegido != null) {
            FileInputStream file = new FileInputStream(archivoElegido);
            XSSFWorkbook excel = new XSSFWorkbook(file);
            XSSFSheet hoja = excel.getSheetAt(0);

        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo antes de procesar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
