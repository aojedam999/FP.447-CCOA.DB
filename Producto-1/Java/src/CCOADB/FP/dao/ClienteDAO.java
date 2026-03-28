package CCOADB.FP.dao;

import CCOADB.FP.modelo.Cliente;
import java.util.List;

public interface ClienteDAO {

    void insertar(Cliente cliente);

    List<Cliente> obtenerTodos();

    Cliente buscarPorEmail(String email);
}