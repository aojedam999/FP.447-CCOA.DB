package CCOADB.FP.dao;

import CCOADB.FP.modelo.Articulo;
import java.sql.Connection;
import java.util.List;

public interface ArticuloDAO {

    void insertar(Articulo articulo);

    List<Articulo> obtenerTodos();

    Articulo buscarPorId(int id);

    Articulo buscarPorCodigo(String codigo);

    void actualizarStock(int idArticulo, int nuevoStock);

    void reducirStock(Connection conn, String codigo, int cantidad);
}