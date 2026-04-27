package CCOADB.FP.util;

import CCOADB.FP.dao.ArticuloDAOImp;
import CCOADB.FP.dao.PedidoDAOImp;
import CCOADB.FP.modelo.Pedido;
import java.sql.CallableStatement;
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
            CallableStatement cs = conn.prepareCall("{CALL crear_pedido(?, ?, ?, ?, ?)}");

            cs.setInt(1, pedido.getCliente().getId());
            cs.setInt(2, pedido.getArticulo().getId());
            cs.setTimestamp(3, java.sql.Timestamp.valueOf(pedido.getFechaHora()));
            cs.setString(4, pedido.getEstado().name());
            cs.setInt(5, pedido.getUnidades());

            cs.execute();

            articuloDAO.reducirStock(conn,
                    pedido.getArticulo().getCodigo(),
                    pedido.getUnidades());

            return true;
        });
    }
}