package Productos;

import helper.GestionaExcel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

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

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(importarArchivo, gbc);

        gbc.gridy = 1;
        add(procesarArchivo, gbc);

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
