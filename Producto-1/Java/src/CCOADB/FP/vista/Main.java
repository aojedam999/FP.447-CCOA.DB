package CCOADB.FP.vista;

import CCOADB.FP.controlador.Controlador;
import CCOADB.FP.util.ConexionBD;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        // Prueba de conexión con la BBDD
        try {
            Connection conexion = ConexionBD.getConexion();
            System.out.println("Conexión OK");
        } catch (Exception e) {
            System.out.println("Error en la conexión");
            e.printStackTrace();
        }

        // Flujo normal
        Controlador controlador = new Controlador();
        MenuConsola menu = new MenuConsola(controlador);
        menu.iniciar();
    }
}