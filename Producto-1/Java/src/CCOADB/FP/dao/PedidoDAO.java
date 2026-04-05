package CCOADB.FP.dao;

import CCOADB.FP.modelo.Pedido;
import java.sql.Connection;
import java.util.List;

public interface PedidoDAO {

    void insertar(Pedido pedido);

    void insertar(Connection conn, Pedido pedido);

    List<Pedido> obtenerTodos();

    Pedido buscarPorId(int id);
}