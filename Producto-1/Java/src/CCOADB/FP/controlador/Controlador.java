package CCOADB.FP.controlador;

import CCOADB.FP.modelo.*;
import CCOADB.FP.modelo.excepciones.*;

import java.util.List;

public class Controlador {

    private Empresa empresa;

    public Controlador() {
        empresa = new Empresa("Online Store");
    }


    // Métodos Cliente
    public void addCliente(Cliente cliente) {
        empresa.añadirCliente(cliente);
    }

    public List<Cliente> getClientes() {
        return empresa.getClientes();
    }

    public void mostrarClientesEstandar() {
        empresa.mostrarClientesEstandar();
    }

    public void mostrarClientesPremium() {
        empresa.mostrarClientesPremium();
    }

    public Cliente buscarClientePorEmail(String email) throws ClienteNoEncontradoException {
        for (Cliente c : empresa.getClientes()) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        throw new ClienteNoEncontradoException("Cliente con email '" + email + "' no encontrado.");
    }


    // Métodos Articulo
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


    // Métodos Pedido
    public void addPedido(Pedido pedido) throws StockInsuficienteException {

        if (!pedido.getArticulo().hayStock(pedido.getUnidades())) {
            throw new StockInsuficienteException("No hay suficiente stock para el artículo: "
                    + pedido.getArticulo().getDescripcion());
        }

        pedido.getArticulo().reducirStock(pedido.getUnidades());
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
        empresa.eliminarPedido(pedido);
    }

    public void mostrarPedidosPendientes(Cliente cliente) {
        empresa.mostrarPedidosPendientes(cliente);
    }

    public void mostrarPedidosEnviados(Cliente cliente) {
        empresa.mostrarPedidosEnviados(cliente);
    }
}