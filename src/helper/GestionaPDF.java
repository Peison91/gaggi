package helper;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GestionaPDF {
    public void prueba(){
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PDF_Cotización"));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            Graphics g = cb.createGraphicsShapes(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight());
            g.setColor(Color.red);
            g.drawRect(1, 1, 593, 790);

            g.setColor(new Color(154, 171, 237));
            g.fillOval(290, 90, 280, 100);

            Font font1 = new Font("Tahoma", Font.BOLD + Font.ITALIC, 35);
            g.setFont(font1);

            g.setColor(Color.RED);
            g.drawString("PDF desde Java", 40, 150);

            g.setColor(Color.WHITE);
            g.drawString("PDF desde Java", 290, 150);

            ImageIcon img1 = new ImageIcon(getClass().getResource("imagenes/nuevo.png"));
            g.drawImage(img1.getImage(), 200, 250, 200, 200, null);

            Font font2 = new Font("Tahoma", Font.PLAIN, 15);
            g.setFont(font2);
            g.setColor(Color.BLACK);
            g.drawString("Escanea el código QR para visitar la lista de reproducción de YouTube", 60, 460);
            g.drawString("del curso de GUI en Java", 210, 480);
        }
        catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
        catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        document.close();

        JOptionPane.showMessageDialog(null,
                "Se creó el pdf");
    }
    public static void main(String[] args) {
        GestionaPDF obj = new GestionaPDF();
        obj.prueba();
    }
}
