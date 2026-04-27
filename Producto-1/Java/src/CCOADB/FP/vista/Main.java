package CCOADB.FP.vista;

import CCOADB.FP.controlador.Controlador;
import CCOADB.FP.util.ConexionBD;
import CCOADB.FP.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        // Prueba de conexión con la BBDD (JDBC)
        try {
            Connection conexion = ConexionBD.getConexion();
            System.out.println("Conexión JDBC OK");
        } catch (Exception e) {
            System.out.println("Error en la conexión JDBC");
            e.printStackTrace();
        }

        // Prueba de conexión con JPA
        try {
            EntityManager em = JPAUtil.getEntityManager();
            System.out.println("JPA conectado correctamente");
            em.close();
        } catch (Exception e) {
            System.out.println("Error con JPA");
            e.printStackTrace();
        }

        // Flujo normal
        Controlador controlador = new Controlador();
        MenuConsola menu = new MenuConsola(controlador);
        menu.iniciar();
    }
}