import Utiles.Conexion;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        /*Cotizacion cotizacion = new Cotizacion(0,5,new Date(),0,1);
        Cotizacion_CabeceraDB cotizacionCabeceraDB = new Cotizacion_CabeceraDB(Conexion.conectar());
        cotizacionCabeceraDB.insertarCotizacion(cotizacion);
        int idCabecera = cotizacionCabeceraDB.obtenerIdCabecera();

        Cotizacion_detalle cotizacionDetalle = new Cotizacion_detalle(0,2,12.50,12.50,26,idCabecera);
        Cotizacion_detalle cotizacionDetall1 = new Cotizacion_detalle(0,2,13.50,13.50,30,idCabecera);
        Cotizacion_detalle cotizacionDetall2 = new Cotizacion_detalle(0,2,14.50,14.50,35,idCabecera);
        Cotizacion_DetalleDB cotizacionDetalleDB = new Cotizacion_DetalleDB(Conexion.conectar());
        List<Cotizacion_detalle> listCot = new ArrayList<>();
        listCot.add(cotizacionDetalle);
        listCot.add(cotizacionDetall1);
        listCot.add(cotizacionDetall2);

        cotizacionDetalleDB.insertarCotizacionDetalleLista(listCot);
        //cotizacionDetalleDB.insertarCotizacionDetalle(cotizacionDetalle);*/


        Frame ventanaInicial = new Frame();
        ventanaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Conexion.conectar();



           }
}