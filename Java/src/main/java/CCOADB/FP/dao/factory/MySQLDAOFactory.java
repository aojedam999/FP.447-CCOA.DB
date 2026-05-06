package CCOADB.FP.dao.factory;

import CCOADB.FP.dao.ArticuloDAO;
import CCOADB.FP.dao.ArticuloDAOImp;
import CCOADB.FP.dao.ClienteDAO;
import CCOADB.FP.dao.ClienteDAOImp;
import CCOADB.FP.dao.PedidoDAO;
import CCOADB.FP.dao.PedidoDAOImp;

public class MySQLDAOFactory extends DAOFactory {

    @Override
    public ClienteDAO getClienteDAO() {
        return new ClienteDAOImp();
    }

    @Override
    public ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOImp();
    }

    @Override
    public PedidoDAO getPedidoDAO() {
        return new PedidoDAOImp();
    }
}