package CCOADB.FP.controlador;

import CCOADB.FP.modelo.*;
import CCOADB.FP.modelo.excepciones.*;

import CCOADB.FP.dao.*;
import CCOADB.FP.dao.factory.DAOFactory;

import java.util.List;

public class Controlador {

    private Empresa empresa;

    private ClienteDAO clienteDAO;
    private PedidoDAO pedidoDAO;

    public Controlador() {
        empresa = new Empresa("Online Store");

        DAOFactory factory = DAOFactory.getFactory(DAOFactory.JPA);

        clienteDAO = factory.getClienteDAO();
        pedidoDAO = factory.getPedidoDAO();
    }

    public void addCliente(Cliente cliente) throws Exception {
        try {
            clienteDAO.insertar(cliente);
        } catch (Exception e) {
            throw new Exception("Error al añadir cliente: posible duplicado o fallo en BD");
        }
    }

    public List<Cliente> getClientes() {
        return clienteDAO.obtenerTodos();
    }

    public void mostrarClientesEstandar() {
        for (Cliente c : clienteDAO.obtenerTodos()) {
            if (c instanceof ClienteEstandar) {
                System.out.println(c);
            }
        }
    }

    public void mostrarClientesPremium() {
        for (Cliente c : clienteDAO.obtenerTodos()) {
            if (c instanceof ClientePremium) {
                System.out.println(c);
            }
        }
    }

    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        Cliente cliente = clienteDAO.buscarPorEmail(email);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente con email '" + email + "' no encontrado.");
        }

        return cliente;
    }

    public void addArticulo(Articulo articulo) {
        empresa.añadirArticulo(articulo);
    }

    public List<Articulo> getArticulos() {
        return empresa.getArticulos();
    }

    public Articulo buscarArticuloPorCodigo(String codigo) throws ArticuloNoEncontradoException {
        for (Articulo a : empresa.getArticulos()) {
            if (a.getCodigo().equalsIgnoreCase(codigo)) {
                return a;
            }
        }
        throw new ArticuloNoEncontradoException("Artículo con código '" + codigo + "' no encontrado.");
    }

    public void addPedido(Pedido pedido) throws StockInsuficienteException {

        if (!pedido.getArticulo().hayStock(pedido.getUnidades())) {
            throw new StockInsuficienteException("No hay suficiente stock para el artículo: "
                    + pedido.getArticulo().getDescripcion());
        }

        Articulo a = pedido.getArticulo();
        a.reducirStock(pedido.getUnidades());

        ArticuloDAO articuloDAO = DAOFactory.getFactory(DAOFactory.JPA).getArticuloDAO();
        articuloDAO.actualizarStock(a.getId(), a.getStockDisponible() - pedido.getUnidades());
        pedidoDAO.insertar(pedido);
    }

    public List<Pedido> getPedidos() {
        return pedidoDAO.obtenerTodos();
    }

    public Pedido buscarPedidoPorNumero(int numero) throws PedidoNoEncontradoException {
        Pedido p = pedidoDAO.buscarPorId(numero);

        if (p == null) {
            throw new PedidoNoEncontradoException("Pedido con número '" + numero + "' no encontrado.");
        }

        return p;
    }

    public void actualizarEstadosEnvio() {
        for (Pedido p : pedidoDAO.obtenerTodos()) {
            if (p.getEstado() == EstadoEnvio.PENDIENTE && !p.esCancelable()) {
                p.actualizarEstado();
            }
        }
    }

    public void eliminarPedido(Pedido pedido) throws PedidoNoEliminableException {

        if (!pedido.puedeEliminarse()) {
            throw new PedidoNoEliminableException(
                    "No se puede eliminar el pedido número " + pedido.getNumeroPedido());
        }

        pedido.getArticulo().aumentarStock(pedido.getUnidades());
    }

    public void mostrarPedidosPendientes(Cliente cliente) {
        for (Pedido p : pedidoDAO.obtenerTodos()) {
            if (p.getCliente().equals(cliente) && p.estaPendiente()) {
                System.out.println(p);
            }
        }
    }

    public void mostrarPedidosEnviados(Cliente cliente) {
        for (Pedido p : pedidoDAO.obtenerTodos()) {
            if (p.getCliente().equals(cliente) && p.estaEnviado()) {
                System.out.println(p);
            }
        }
    }
}