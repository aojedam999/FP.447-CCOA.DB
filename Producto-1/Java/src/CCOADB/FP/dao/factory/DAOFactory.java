package CCOADB.FP.dao.factory;

import CCOADB.FP.dao.ArticuloDAO;
import CCOADB.FP.dao.ClienteDAO;
import CCOADB.FP.dao.PedidoDAO;
import CCOADB.FP.dao.jpa.DAOFactoryJpa;

public abstract class DAOFactory {

    public static final int MYSQL = 1;
    public static final int JPA = 2;

    public static DAOFactory getFactory(int type) {

        switch (type) {

            case MYSQL:
                return new MySQLDAOFactory();

            case JPA:
                return new DAOFactoryJpa();

            default:
                return null;
        }
    }

    public abstract ClienteDAO getClienteDAO();
    public abstract ArticuloDAO getArticuloDAO();
    public abstract PedidoDAO getPedidoDAO();
}