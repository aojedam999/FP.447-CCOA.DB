package CCOADB.FP.dao.jpa;

import CCOADB.FP.dao.ArticuloDAO;
import CCOADB.FP.dao.ClienteDAO;
import CCOADB.FP.dao.PedidoDAO;
import CCOADB.FP.dao.factory.DAOFactory;
import CCOADB.FP.dao.jpa.ArticuloDAOJpa;
import CCOADB.FP.dao.jpa.ClienteDAOJpa;
import CCOADB.FP.dao.jpa.PedidoDAOJpa;

public class DAOFactoryJpa extends DAOFactory {

    @Override
    public ClienteDAO getClienteDAO() {
        return new ClienteDAOJpa();
    }

    @Override
    public ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOJpa();
    }

    @Override
    public PedidoDAO getPedidoDAO() {
        return new PedidoDAOJpa();
    }
}
