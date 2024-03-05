package helper;

import Utiles.Conexion;
import com.gaggi.database.ProductosDB;
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
    int  cantidadProducIngresados = 0;
    int cantidadProducModificados = 0;
    public void EjecutarProceso(String excel) throws Exception {
        File archivo = new File(excel);
        ArrayList<Productos> lista = new ArrayList<>();



        try {
            InputStream input = new FileInputStream(archivo);
            XSSFWorkbook libro = new XSSFWorkbook(input);
            XSSFSheet hoja = libro.getSheetAt(0);

            Iterator<Row> filas = hoja.rowIterator();
            Iterator<Cell> columnas;

            Row filaActual;
            Cell columnaActual;


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
                    //Se convierte a entero para ignorar cabeceras y filas vacias.
                    if (indiceColumna == 0 && convertirEntero(columnaActual)) {
                        codigoProducto = Integer.parseInt(columnaActual.toString());
                        auxiliar = true;
                    }
                    if (auxiliar) {
                        switch (indiceColumna) {
                            case 1: // columna B
                                descProducto = columnaActual.getStringCellValue();
                                break;
                            case 3: // columna D
                                precioProducto = convertirPrecio(columnaActual.getStringCellValue());
                                break;
                        }
                    }
                }
                if (codigoProducto != 0) {
                    Productos producto = new Productos(codigoProducto, descProducto, precioProducto);
                    lista.add(producto);
                }
            }

            input.close();
            libro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ProductosDB productosDB = new ProductosDB(Conexion.conectar());


        for (Productos producto : lista) {
            String codigoProduc = String.valueOf(producto.getCodigoProducto());


            com.gaggi.model.Productos produc = productosDB.consultaProductoCodigo(codigoProduc);

            if(produc == null){
                com.gaggi.model.Productos productoInsertar = new com.gaggi.model.Productos(0, producto.getDescProducto()
                        , codigoProduc
                        , "s/d",
                        producto.getPrecioProducto(),
                        0,
                        0);

                productosDB.insertarProducto(productoInsertar);
                cantidadProducIngresados++;

            }else if(produc != null && producto.getPrecioProducto() != produc.getPrecio()){

                produc.setPrecio(producto.precioProducto);
                productosDB.actualizarProducto(produc);
                cantidadProducModificados++;
            }

        }

    }


    public int getCantidadProducIngresados() {
        return cantidadProducIngresados;
    }

    public int getCantidadProducModificados() {
        return cantidadProducModificados;
    }


    private boolean convertirEntero(Cell columnaCodigo) {
        try {
            Integer.parseInt(columnaCodigo.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String formatoPrecio(String precio) {
        DecimalFormat formato = new DecimalFormat("#.##");
        return formato.format(precio);
    }

    private Double convertirPrecio(String precio) {
        StringBuilder formateada = new StringBuilder();
        char c;

        for (int i = 0; i < precio.length(); i++) {
            c = precio.charAt(i);

            if (Character.isDigit(c)) {
                formateada.append(c);
            } else if (c == '.' || c == ',') {
                formateada.append('.');
            }
        }

        String resultadoFinal = formateada.toString();

        if (!resultadoFinal.isEmpty()) {
            // Reemplazar puntos adicionales con un solo punto
            resultadoFinal = resultadoFinal.replaceAll("\\.(?=.*\\.)", "");

            try {
                return Double.parseDouble(resultadoFinal);
            } catch (NumberFormatException e) {
                System.err.println("Error al convertir precio: " + e.getMessage());
            }
        }

        return 0.0;
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

        public int getCodigoProducto() {
            return codigoProducto;
        }

        public void setCodigoProducto(int codigoProducto) {
            this.codigoProducto = codigoProducto;
        }

        public String getDescProducto() {
            return descProducto;
        }

        public void setDescProducto(String descProducto) {
            this.descProducto = descProducto;
        }

        public double getPrecioProducto() {
            return precioProducto;
        }

        public void setPrecioProducto(double precioProducto) {
            this.precioProducto = precioProducto;
        }

    }

}
