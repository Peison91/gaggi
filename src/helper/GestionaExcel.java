package helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

            ArrayList<Productos> lista = new ArrayList<>();

            while (filas.hasNext()) {

                filaActual = filas.next();
                columnas = filaActual.cellIterator();
                int codigoProducto = 0;
                String descProducto = null;
                double precioProducto = 0.0;
                boolean auxiliar = false;

                while (columnas.hasNext()) {
                    columnaActual = columnas.next();
                    int indiceColumna = columnaActual.getColumnIndex();
                    if (indiceColumna == 0 && convertirEntero(columnaActual)) {
                        codigoProducto = Integer.parseInt(columnaActual.toString());
                        auxiliar = true;
                    }
                    if (auxiliar){
                        switch (indiceColumna) {
                            case 1: // columna B
                                descProducto = columnaActual.getStringCellValue();
                                break;
                            case 3: // columna D
                                precioProducto = Double.parseDouble(convertirPrecio(columnaActual.getStringCellValue()));
                                break;
                        }
                    }
                }
                if (codigoProducto != 0) {
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
    private boolean convertirEntero(Cell columnaCodigo){
        try{
            Integer.parseInt(columnaCodigo.toString());
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    private String formatoPrecio(String precio){
        DecimalFormat formato = new DecimalFormat("#.##");
        return formato.format(precio);
    }
    private String convertirPrecio(String precio){
        StringBuilder miNumero = new StringBuilder();
        char c;
        char d = 0;
        for(int i=0; precio.length()>i; i++ ) {
            c = precio.charAt(i);
            if (c == '.') {
                d = 0;
            }
            if(c == ',')
            {
                d = '.';
            }
            if(c=='.' || c==',')
            {
                miNumero.append(d);
            }
            else {
                miNumero.append(c);
            }

        }
        return miNumero.toString();
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
