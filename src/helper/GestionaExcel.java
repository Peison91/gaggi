package helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class GestionaExcel {
    public void EjecutarProceso(){
        String directorioActual = System.getProperty("user.dir");
        File archivo = new File(directorioActual,"precios.xlsx");

        try {
            InputStream input = new FileInputStream(archivo);
            XSSFWorkbook libro = new XSSFWorkbook(input);
            XSSFSheet hoja = libro.getSheetAt(0);

            Iterator<Row> filas = hoja.rowIterator();
            Iterator<Cell> columnas;

            Row filaActual;
            Cell columnaActual;
            /*
            while(filas.hasNext()){
                filaActual = filas.next();
                columnas = filaActual.cellIterator();

                while(columnas.hasNext()){
                    columnaActual = columnas.next();
                    if (columnaActual.getCellType() == CellType.STRING){
                        String valor = columnaActual.getStringCellValue();
                        System.out.println(valor);
                    }
                    if (columnaActual.getCellType() == CellType.NUMERIC){
                        double valor = columnaActual.getNumericCellValue();
                        System.out.println(valor);
                    }
                    if (columnaActual.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(columnaActual)){
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        Date fecha = columnaActual.getDateCellValue();
                        System.out.println(formato.format(fecha));
                    }
                }
            }
            */

            ArrayList<Productos> lista = new ArrayList<>();
            while(filas.hasNext()) {
                filaActual = filas.next();
                columnas = filaActual.cellIterator();
                int codigoProducto = -1;
                String descProducto = null;
                double precioProducto = -1.0;

                while (columnas.hasNext()) {
                    columnaActual = columnas.next();
                    if (columnaActual.getCellType() == CellType.NUMERIC) {
                        int columnIndex = columnaActual.getColumnIndex();
                        switch (columnIndex) {
                            case 0: // Código de producto
                                codigoProducto = (int) columnaActual.getNumericCellValue();
                                break;
                            case 1: // Descripción de producto
                                descProducto = columnaActual.getStringCellValue();
                                break;
                            case 3: // Precio de producto
                                precioProducto = columnaActual.getNumericCellValue();
                                break;
                        }
                    }
                    if (codigoProducto != -1 && descProducto != null && precioProducto != -1.0) {
                        Productos producto = new Productos(codigoProducto, descProducto, precioProducto);
                        lista.add(producto);
                    }
                }
            }
            for (Productos producto : lista) {
                System.out.println(producto);
            }
            input.close();
            libro.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    class Productos {
        private int codigoProducto;
        private String descProducto;
        private double precioProducto;

        public Productos(int codigoProducto, String descProducto, double precioProducto) {
            this.codigoProducto = codigoProducto;
            this.descProducto = descProducto;
            this.precioProducto = precioProducto;
        }
    }
    public static void main(String[] args) {
        GestionaExcel g1 = new GestionaExcel();
        g1.EjecutarProceso();
    }
}
