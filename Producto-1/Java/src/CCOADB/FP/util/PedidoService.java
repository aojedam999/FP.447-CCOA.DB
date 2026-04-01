package CCOADB.FP.servicios;

import CCOADB.FP.dao.ArticuloDAOImp;
import CCOADB.FP.dao.PedidoDAOImp;
import CCOADB.FP.modelo.Pedido;
import CCOADB.FP.util.TransactionManager;

public class PedidoService {

    private final PedidoDAOImp pedidoDAO;
    private final ArticuloDAOImp articuloDAO;

    public PedidoService(PedidoDAOImp pedidoDAO, ArticuloDAOImp articuloDAO) {
        this.pedidoDAO = pedidoDAO;
        this.articuloDAO = articuloDAO;
    }

    public void crearPedidoConTransaccion(Pedido pedido) throws Exception {
        TransactionManager.runInTransaction(conn -> {
            // 1) Insertar pedido
            pedidoDAO.insertarPedido(conn, pedido);

            // 2) Reducir stock
            articuloDAO.reducirStock(conn,
                    pedido.getArticulo().getCodigo(),
                    pedido.getUnidades());

            return true;
        });
    }
}