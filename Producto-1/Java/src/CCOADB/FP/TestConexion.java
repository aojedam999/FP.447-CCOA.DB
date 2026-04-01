package CCOADB.FP;

import CCOADB.FP.util.ConexionBD;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection conn = ConexionBD.getConexion()) {
            System.out.println("✅ Conexión OK: " + (conn != null && !conn.isClosed()));
        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}