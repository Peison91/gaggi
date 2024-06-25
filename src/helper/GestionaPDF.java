package helper;

import Utiles.Conexion;
import com.lowagie.text.*;
import com.lowagie.text.Image;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GestionaPDF {
    public void prueba(int id_cabecera) {
        Document document = new Document(PageSize.LETTER);
        String rutaImg = "src/imagenes/logo_horizontal.jpg";
        try {
            // Especifica la ruta completa a la carpeta de descargas
            String homeDirectory = System.getProperty("user.home");
            String desktopDirectory = homeDirectory + File.separator + "Desktop";
            String desktopFilePath = desktopDirectory + File.separator + "Cotización_" + id_cabecera + ".pdf";

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(desktopFilePath));

            Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
            ClientesDB clientesDB = new ClientesDB(Conexion.conectar());
            document.open();

            Image image = Image.getInstance(rutaImg);
            Image img = Image.getInstance(rutaImg);
            img.setAlignment(Image.ALIGN_CENTER);
            img.scaleToFit(200, 100);
            document.add(img);

            // Añadir título
            com.lowagie.text.Font fontTitle = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18, com.lowagie.text.Font.BOLD, Color.black);
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

            com.lowagie.text.Paragraph numCotizacion = new com.lowagie.text.Paragraph("Nº Cotización: " + id_cabecera);
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

            // Establecer los anchos de las columnas
            float[] columnWidths = {3f, 1f, 1f, 1f}; // Anchos relativos para cada columna
            table.setWidths(columnWidths);

            // Configurar celdas de encabezado
            PdfPCell cell1 = new PdfPCell(new Phrase("Producto"));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(new Color(169, 169, 169));
            cell1.setFixedHeight(20f); // Establecer la altura de la fila
            table.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("Cantidad"));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(new Color(169, 169, 169));
            cell2.setFixedHeight(20f); // Establecer la altura de la fila
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("Precio"));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setBackgroundColor(new Color(169, 169, 169));
            cell3.setFixedHeight(20f); // Establecer la altura de la fila
            table.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase("Suma artículo"));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setBackgroundColor(new Color(169, 169, 169));
            cell4.setFixedHeight(20f); // Establecer la altura de la fila
            table.addCell(cell4);

            // Agregar detalles de la cotización
            ProductosDB productosDB = new ProductosDB(Conexion.conectar());

            List<Cotizacion_detalle> lstCotizacion = cotizacionCabeceraDB.consultarCotizacionDetalle(id_cabecera);
            double sumaTotalProductos = 0;
            for (Cotizacion_detalle cotizacion : lstCotizacion) {
                Productos producto = productosDB.consultaProducto(cotizacion.getProducto_id());

                String cantidad = String.valueOf(cotizacion.getCantidad());
                String descripcion = producto.getDescripcion();
                String precioUnitario = String.valueOf(cotizacion.getPrecio_ajustado());
                double precioTotalDouble = cotizacion.getPrecio_ajustado() * cotizacion.getCantidad();
                String precioTotalImprimir = String.valueOf(precioTotalDouble);
                sumaTotalProductos += precioTotalDouble;

                PdfPCell descripcionCell = new PdfPCell(new Phrase(descripcion));
                descripcionCell.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(descripcionCell);

                PdfPCell cantidadCell = new PdfPCell(new Phrase(cantidad));
                cantidadCell.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(cantidadCell);

                PdfPCell precioUnitarioCell = new PdfPCell(new Phrase("$ " + precioUnitario));
                precioUnitarioCell.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(precioUnitarioCell);

                PdfPCell precioTotalCell = new PdfPCell(new Phrase("$ " + precioTotalImprimir));
                precioTotalCell.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(precioTotalCell);
            }

            // Añadir filas vacías si es necesario
            int numberOfRows = lstCotizacion.size();
            while (numberOfRows < 19) {
                PdfPCell emptyCell1 = new PdfPCell(new Phrase(""));
                emptyCell1.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(emptyCell1);

                PdfPCell emptyCell2 = new PdfPCell(new Phrase(""));
                emptyCell2.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(emptyCell2);

                PdfPCell emptyCell3 = new PdfPCell(new Phrase(""));
                emptyCell3.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(emptyCell3);

                PdfPCell emptyCell4 = new PdfPCell(new Phrase(""));
                emptyCell4.setFixedHeight(20f); // Establecer la altura de la fila
                table.addCell(emptyCell4);

                numberOfRows++;
            }

            // Añadir la fila final con el total de los productos
            PdfPCell stringTotalCell = new PdfPCell(new Phrase("Total: "));
            stringTotalCell.setColspan(3); // Combinar las tres celdas
            stringTotalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            stringTotalCell.setPaddingLeft(10f); // Añadir padding a la izquierda
            stringTotalCell.setPaddingRight(10f); // Añadir padding a la derecha
            stringTotalCell.setFixedHeight(20f); // Establecer la altura de la fila
            stringTotalCell.setBackgroundColor(new Color(169, 169, 169)); // Establecer el color de fondo
            table.addCell(stringTotalCell);

            PdfPCell sumaTotalCell = new PdfPCell(new Phrase("$ " + sumaTotalProductos));
            sumaTotalCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            sumaTotalCell.setPaddingLeft(10f); // Añadir padding a la izquierda
            sumaTotalCell.setPaddingRight(10f); // Añadir padding a la derecha
            sumaTotalCell.setFixedHeight(20f); // Establecer la altura de la fila
            sumaTotalCell.setBackgroundColor(new Color(169, 169, 169)); // Establecer el color de fondo
            table.addCell(sumaTotalCell);

            // Agregar la tabla al documento
            document.add(table);

            lstCotizacion.clear();
        } catch (DocumentException | FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            document.close();
        }
    }

}
