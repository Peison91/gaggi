package helper;

import Utiles.Conexion;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import database.ClientesDB;
import database.Cotizacion_CabeceraDB;
import database.ProductosDB;
import model.Clientes;
import model.Cotizacion;
import model.Cotizacion_detalle;
import model.Productos;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GestionaPDF {
    public void prueba(int id_cabecera) {
        Document document = new Document(PageSize.LETTER);
        try {
            // Especifica la ruta completa a la carpeta de descargas
            String homeDirectory = System.getProperty("user.home");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PDF_Cotización.pdf"));

            Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
            ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
            document.open();

            // Añadir título
            com.lowagie.text.Font fontTitle = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18, com.lowagie.text.Font.BOLD, new Color(0, 0, 255));
            com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph("Cotización de Productos", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Añadir espacio
            document.add(new com.lowagie.text.Paragraph("\n"));

            Cotizacion coti = cotizacionCabeceraDB.consultarCotizacion(id_cabecera);

            Date fechaCoti = coti.getFecha_cotizacion();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Formatear la fecha
            String fechaFormateada = dateFormat.format(fechaCoti);

            int idCliente = coti.getCliente_id();

            Clientes clien = clientesDB.consultaCliente(idCliente);
            String nombreCliente = clien.getNombre();



            com.lowagie.text.Paragraph numCotizacion = new com.lowagie.text.Paragraph("Nº Cotizacion: " + id_cabecera);
            document.add(numCotizacion);
            document.add(new com.lowagie.text.Paragraph("\n"));
            com.lowagie.text.Paragraph fecha = new com.lowagie.text.Paragraph("Fecha: " + fechaFormateada);
            document.add(fecha);
            document.add(new com.lowagie.text.Paragraph("\n"));
            com.lowagie.text.Paragraph cliente = new com.lowagie.text.Paragraph("Cliente: " + nombreCliente);
            document.add(cliente);
            document.add(new com.lowagie.text.Paragraph("\n"));

            // Crear la tabla
            PdfPTable table = new PdfPTable(4); // Número de columnas
            table.setWidthPercentage(100); // Ancho de la tabla en porcentaje de la página
            table.setSpacingBefore(10f); // Espacio antes de la tabla
            table.setSpacingAfter(10f); // Espacio después de la tabla

            // Configurar celdas de encabezado


            PdfPCell cell2 = new PdfPCell(new Phrase("Producto"));
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

            PdfPCell cell5 = new PdfPCell(new Phrase("Suma articulo"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(new Color(169, 169, 169));
            table.addCell(cell5);

            // Agregar detalles de la cotización

            ProductosDB productosDB = new ProductosDB(Conexion.conectar());

            List<Cotizacion_detalle> lstCotizacion = cotizacionCabeceraDB.consultarCotizacionDetalle(id_cabecera);
            double sumaTotalProductos = 0;
            for(Cotizacion_detalle cotizacion : lstCotizacion){
                Productos producto = productosDB.consultaProducto(cotizacion.getProducto_id());

                //String productoId = String.valueOf(cotizacion.getProducto_id());
                String cantidad = String.valueOf(cotizacion.getCantidad());
                String descripcion = producto.getDescripcion();
                String precioUnitario = String.valueOf(cotizacion.getPrecio_unitario());
                double precioTotalDouble = producto.getPrecio() * cotizacion.getCantidad();
                String precioTotalImprimir = String.valueOf(precioTotalDouble);
                sumaTotalProductos += precioTotalDouble;

                //table.addCell(productoId);
                table.addCell(descripcion);
                table.addCell(cantidad);
                table.addCell("$ " +precioUnitario);
                table.addCell("$ " +precioTotalImprimir);
            }
            // Agregar la tabla al documento
            document.add(table);
            lstCotizacion.clear();

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT,new Phrase("Total : $" + sumaTotalProductos),470,300,0);


            /*com.lowagie.text.Paragraph additionalText = new com.lowagie.text.Paragraph("Total : $" + sumaTotalProductos);
            document.add(additionalText);*/


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
        obj.prueba(19);
    }
}
