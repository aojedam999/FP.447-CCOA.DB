package CCOADB.FP.dao.factory;

import CCOADB.FP.dao.ArticuloDAO;
import CCOADB.FP.dao.ClienteDAO;
import CCOADB.FP.dao.PedidoDAO;

public abstract class DAOFactory {

    public static final int MYSQL = 1;

    // Este método decide qué factory devolver (por ahora solo MySQL)
    public static DAOFactory getFactory(int type) {
        return switch (type) {
            case MYSQL -> new MySQLDAOFactory();
            default -> throw new IllegalArgumentException("Tipo de DAOFactory no soportado: " + type);
        };
    }

    // Estos métodos obligan a cada factory concreta a "dar" sus DAOs
    public abstract ClienteDAO getClienteDAO();
    public abstract ArticuloDAO getArticuloDAO();
    public abstract PedidoDAO getPedidoDAO();
}
