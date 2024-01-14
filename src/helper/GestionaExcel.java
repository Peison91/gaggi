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
                int codigoProducto = 0;
                String descProducto = null;
                double precioProducto = 0.0;
                boolean codigoEntero = false;

                while (columnas.hasNext()) {
                    columnaActual = columnas.next();
                    if (columnaActual.getCellType() == CellType.NUMERIC){
                        codigoEntero = true;
                        int indiceColumna = columnaActual.getColumnIndex();
                        switch (indiceColumna){
                            case 0: // columna A
                                codigoProducto = (int) columnaActual.getNumericCellValue();
                                break;
                            case 1: // columna B
                                descProducto = columnaActual.getStringCellValue();
                                break;
                            case 3: // columna D
                                precioProducto = columnaActual.getNumericCellValue();
                                break;
                        }
                    }
                }
                if(codigoEntero){
                    Productos producto = new Productos(codigoProducto, descProducto, precioProducto);
                    lista.add(producto);
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
