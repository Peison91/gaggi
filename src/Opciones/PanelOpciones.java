package Opciones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelOpciones extends JPanel {
    
    public PanelOpciones() {
        // Configura el diseño del panel.
        setLayout(new FlowLayout());

        // Crea un JComboBox con opciones de Look and Feel.
        String[] opcionesLookAndFeel = {"Metal", "Nimbus", "Windows", "Motif", "GTK"};
        JComboBox<String> lookAndFeelComboBox = new JComboBox<>(opcionesLookAndFeel);
        lookAndFeelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarLookAndFeel((String) lookAndFeelComboBox.getSelectedItem());
            }
        });
        add(lookAndFeelComboBox);
    }

    private void cambiarLookAndFeel(String selectedLookAndFeel) {
        try {
            // Cambia el Look and Feel según la selección del usuario.
            UIManager.setLookAndFeel(getLookAndFeelClassName(selectedLookAndFeel));

            // Actualiza el UI de la aplicación.
            SwingUtilities.updateComponentTreeUI(this);

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
}

