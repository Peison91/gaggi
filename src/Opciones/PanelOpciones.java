package Opciones;
import javax.swing.*;
import java.io.File;
import java.util.prefs.Preferences;

public class PanelOpciones extends JPanel {
    private JTextField rutacarpetaPDF;
    private JButton seleccionar;
    private Preferences dir_por_defecto = Preferences.userNodeForPackage(PanelOpciones.class);
    private static final String PREF_RUTA_CARPETA = "rutaCarpeta";

    private String rutaCarpeta;


    public String getRutaCarpeta() {
        return rutaCarpeta;
    }

    public PanelOpciones() {
        setLayout(null);
        JLabel destino = new JLabel("Especifique carpeta de facturas PDF");
        destino.setBounds(25,10,300,20);
        rutaCarpeta = dir_por_defecto.get(PREF_RUTA_CARPETA, "");
        rutacarpetaPDF = new JTextField(20);
        rutacarpetaPDF.setBounds(25,35,300,30);
        rutacarpetaPDF.setEditable(false);
        rutacarpetaPDF.setText(rutaCarpeta);
        add(rutacarpetaPDF);
        seleccionar = new JButton("Seleccionar");
        seleccionar.setBounds(350, 35,115,30);
        seleccionar.addActionListener(e -> seleccionarCarpeta());
        add(seleccionar);
        add(destino);
    }
    private void seleccionarCarpeta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File carpetaSeleccionada = fileChooser.getSelectedFile();
            String carpetaAnterior = rutaCarpeta;
            rutaCarpeta = carpetaSeleccionada.getAbsolutePath();
            rutacarpetaPDF.setText(rutaCarpeta);
            if(!carpetaAnterior.equals(rutaCarpeta)){
                JOptionPane.showMessageDialog(this, "Carpeta seleccionada", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            dir_por_defecto.put(PREF_RUTA_CARPETA, rutaCarpeta);
            if (carpetaAnterior != null && carpetaAnterior.equals(rutaCarpeta)){
                JOptionPane.showMessageDialog(this, "Ya has seleccionado esta carpeta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                rutaCarpeta = carpetaAnterior;
                rutacarpetaPDF.setText(rutaCarpeta);
            }
        }
    }
}
