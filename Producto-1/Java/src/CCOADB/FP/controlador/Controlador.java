package CCOADB.FP.controlador;

import CCOADB.FP.modelo.*;
import CCOADB.FP.modelo.excepciones.*;

import CCOADB.FP.dao.ClienteDAO;
import CCOADB.FP.dao.ClienteDAOImpl;

import java.util.List;

public class Controlador {

    private Empresa empresa;

    // DAO de clientes (ya conectado a BD)
    private ClienteDAO clienteDAO;

    // DAOs para otras entidades (NO IMPLEMENTAR, solo preparar)
    // private ArticuloDAO articuloDAO;
    // private PedidoDAO pedidoDAO;

    public Controlador() {
        empresa = new Empresa("Online Store");

        // Inicializamos DAO de clientes: ya trabaja contra BD
        clienteDAO = new ClienteDAOImpl();

        // FUTURO (cuando Ona lo implemente)
        // articuloDAO = new ArticuloDAOImpl();
        // pedidoDAO = new PedidoDAOImpl();
    }

    // =========================
    // CLIENTES (YA CON BD)
    // =========================

    public void addCliente(Cliente cliente) throws Exception {

        try {
            clienteDAO.insertar(cliente);
        } catch (Exception e) {
            throw new Exception("Error al añadir cliente: posible duplicado o fallo en BD");
        }
    }

    public List<Cliente> getClientes() {
        // Antes: empresa.getClientes();
        // Ahora: datos vienen de la BD
        return clienteDAO.obtenerTodos();
    }

    public void mostrarClientesEstandar() {

        // Filtrado en memoria de datos obtenidos de BD
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

        // Búsqueda directa en BD
        Cliente cliente = clienteDAO.buscarPorEmail(email);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente con email '" + email + "' no encontrado.");
        }

        return cliente;
    }

    // =========================
    // ARTÍCULOS (AÚN CON ARRAYLIST)
    // =========================

    public void addArticulo(Articulo articulo) {

        // AÚN usa ArrayList: pendiente de DAO (Ona)
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

    // =========================
    // PEDIDOS (AÚN CON ARRAYLIST)
    // =========================

    public void addPedido(Pedido pedido) throws StockInsuficienteException {

        if (!pedido.getArticulo().hayStock(pedido.getUnidades())) {
            throw new StockInsuficienteException("No hay suficiente stock para el artículo: "
                    + pedido.getArticulo().getDescripcion());
        }

        pedido.getArticulo().reducirStock(pedido.getUnidades());

        // AÚN usa ArrayList: pendiente de DAO
        empresa.añadirPedido(pedido);
    }

    public List<Pedido> getPedidos() {
        return empresa.getPedidos();
    }

    public Pedido buscarPedidoPorNumero(int numero) throws PedidoNoEncontradoException {
        for (Pedido p : empresa.getPedidos()) {
            if (p.getNumeroPedido() == numero) {
                return p;
            }
        }
        throw new PedidoNoEncontradoException("Pedido con número '" + numero + "' no encontrado.");
    }

    public void actualizarEstadosEnvio() {

        for (Pedido p : empresa.getPedidos()) {

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

        // AÚN ArrayList: pendiente de DAO
        empresa.eliminarPedido(pedido);
    }

    public void mostrarPedidosPendientes(Cliente cliente) {
        empresa.mostrarPedidosPendientes(cliente);
    }

    public void mostrarPedidosEnviados(Cliente cliente) {
        empresa.mostrarPedidosEnviados(cliente);
    }
}