package CCOADB.FP.util;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * NO USAR con JPA.
 * Esta clase está diseñada para JDBC con transacciones manuales.
 */
@Deprecated
public class TransactionManager {

    @FunctionalInterface
    public interface TransactionalWork<T> {
        T execute(Connection conn) throws Exception;
    }

    public static <T> T runInTransaction(TransactionalWork<T> work) throws Exception {
        Connection conn = null;
        boolean oldAutoCommit = true;

        try {
            conn = ConexionBD.getConexion();
            oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            T result = work.execute(conn);

            conn.commit();
            return result;

        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ignored) {}
            }
            throw e;

        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(oldAutoCommit); } catch (SQLException ignored) {}
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }
    }
}