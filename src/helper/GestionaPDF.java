package helper;

import Utiles.Conexion;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import database.Cotizacion_CabeceraDB;
import database.ProductosDB;
import model.Cotizacion_detalle;
import model.Productos;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class GestionaPDF {
    public void prueba(int id_cabecera) {
        Document document = new Document(PageSize.LETTER);
        try {
            // Especifica la ruta completa a la carpeta de descargas
            String homeDirectory = System.getProperty("user.home");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PDF_Cotización.pdf"));


            document.open();

            // Añadir título
            com.lowagie.text.Font fontTitle = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18, com.lowagie.text.Font.BOLD, new Color(0, 0, 255));
            com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph("Cotización de Productos", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Añadir espacio
            document.add(new com.lowagie.text.Paragraph("\n"));

            // Crear la tabla
            PdfPTable table = new PdfPTable(4); // Número de columnas
            table.setWidthPercentage(100); // Ancho de la tabla en porcentaje de la página
            table.setSpacingBefore(10f); // Espacio antes de la tabla
            table.setSpacingAfter(10f); // Espacio después de la tabla

            // Configurar celdas de encabezado
            PdfPCell cell1 = new PdfPCell(new Phrase("Producto"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(new Color(169, 169, 169));
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("Descripción"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(new Color(169, 169, 169));
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("Cantidad"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBackgroundColor(new Color(169, 169, 169));
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase("Precio"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(new Color(169, 169, 169));
            table.addCell(cell4);

            // Agregar detalles de la cotización
            Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
            ProductosDB productosDB = new ProductosDB(Conexion.conectar());

            List<Cotizacion_detalle> lstCotizacion = cotizacionCabeceraDB.consultarCotizacionDetalle(id_cabecera);

            for(Cotizacion_detalle cotizacion : lstCotizacion){
                Productos producto = productosDB.consultaProducto(cotizacion.getProducto_id());

                String productoId = String.valueOf(cotizacion.getProducto_id());
                String cantidad = String.valueOf(cotizacion.getCantidad());
                String descripcion = producto.getDescripcion();
                String precioUnitario = String.valueOf(cotizacion.getPrecio_unitario());

                table.addCell(productoId);
                table.addCell(descripcion);
                table.addCell(cantidad);
                table.addCell("$ " +precioUnitario);
                //table.addCell(producto.getPrecio() * cant_prod); ;
            }
            // Agregar la tabla al documento
            document.add(table);

        } catch (DocumentException | FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        document.close();

        JOptionPane.showMessageDialog(null, "Se creó el pdf");
    }
    public static void main(String[] args) {
        GestionaPDF obj = new GestionaPDF();
        obj.prueba(10);
    }
}
