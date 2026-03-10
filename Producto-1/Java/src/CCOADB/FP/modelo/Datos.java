package CCOADB.FP.modelo;

import java.util.ArrayList;

public class Datos {

    private ArrayList<Cliente> clientes;
    private ArrayList<Articulo> articulos;
    private ArrayList<Pedido> pedidos;

    public Datos() {
        clientes = new ArrayList<>();
        articulos = new ArrayList<>();
        pedidos = new ArrayList<>();
    }

    public void addCliente(Cliente cliente) {clientes.add(cliente);}

    public void addArticulo(Articulo articulo) {articulos.add(articulo);}

    public void addPedido(Pedido pedido) {pedidos.add(pedido);}

    public ArrayList<Cliente> getClientes() {return clientes;}

    public ArrayList<Articulo> getArticulos() {return articulos;}

    public ArrayList<Pedido> getPedidos() {return pedidos;}

    public Cliente buscarClientePorEmail(String email) {
        for (Cliente c : clientes) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    public Articulo buscarArticuloPorCodigo(String codigo) {
        for (Articulo a : articulos) {
            if (a.getCodigo().equals(codigo)) {
                return a;
            }
        }
        return null;
    }

    public Pedido buscarPedidoPorNumero(int numeroPedido) {
        for (Pedido p : pedidos) {
            if (p.getNumeroPedido() == numeroPedido) {
                return p;
            }
        }
        return null;
    }

    public boolean eliminarPedido(int numeroPedido) {
        Pedido p = buscarPedidoPorNumero(numeroPedido);
        if (p != null && p.puedeEliminarse()) {
            pedidos.remove(p);
            return true;
        }
        return false;
    }

    public void mostrarClientes() {clientes.forEach(System.out::println);}

    public void mostrarArticulos() {articulos.forEach(System.out::println);}

    public void mostrarPedidos() {pedidos.forEach(System.out::println);}

}