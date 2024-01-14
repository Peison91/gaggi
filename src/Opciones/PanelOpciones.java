/*package Opciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class PanelOpciones extends JPanel {
    private static final List<PanelOpciones> panelesRegistrados = new ArrayList<>();
    private static final String PREF_LOOK_AND_FEEL = "lookAndFeel";

    public PanelOpciones() {
        setLayout(new FlowLayout());
        JLabel etiqueta = new JLabel("Seleccione apariencia de la interfaz: ");
        String[] opcionesLookAndFeel = {"Metal", "Nimbus", "Windows", "Motif", "GTK"};
        JComboBox<String> lookAndFeelComboBox = new JComboBox<>(opcionesLookAndFeel);
        lookAndFeelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLookAndFeel = (String) lookAndFeelComboBox.getSelectedItem();
                cambiarLookAndFeel(selectedLookAndFeel);
                guardarPreferencia(selectedLookAndFeel);
            }
        });
        add(etiqueta);
        add(lookAndFeelComboBox);

        // Registra este panel en la lista global.
        panelesRegistrados.add(this);

        // Configura el Look and Feel inicial.
        String lookAndFeelInicial = obtenerPreferencia();
        cambiarLookAndFeel(lookAndFeelInicial);
    }

    private void cambiarLookAndFeel(String selectedLookAndFeel) {
        try {
            // Cambia el Look and Feel según la selección del usuario.
            UIManager.setLookAndFeel(getLookAndFeelClassName(selectedLookAndFeel));

            // Actualiza el UI de la aplicación para todos los paneles registrados.
            actualizarLookAndFeelEnTodosLosPaneles();

            // Repinta la ventana para aplicar los cambios.
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Window window = SwingUtilities.getWindowAncestor(PanelOpciones.this);
                    if (window != null) {
                        window.repaint();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void actualizarLookAndFeelEnTodosLosPaneles() {
        for (PanelOpciones panel : panelesRegistrados) {
            SwingUtilities.updateComponentTreeUI(panel);
        }
    }

    private void guardarPreferencia(String lookAndFeel) {
        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        prefs.put(PREF_LOOK_AND_FEEL, lookAndFeel);
    }

    private String obtenerPreferencia() {
        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        return prefs.get(PREF_LOOK_AND_FEEL, UIManager.getCrossPlatformLookAndFeelClassName());
    }

    private String getLookAndFeelClassName(String lookAndFeelName) {
        switch (lookAndFeelName) {
            case "Metal":
                return "javax.swing.plaf.metal.MetalLookAndFeel";
            case "Nimbus":
                return "javax.swing.plaf.nimbus.NimbusLookAndFeel";
            case "Windows":
                return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
            case "Motif":
                return "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            case "GTK":
                return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            default:
                return UIManager.getCrossPlatformLookAndFeelClassName();
        }
    }
}*/
