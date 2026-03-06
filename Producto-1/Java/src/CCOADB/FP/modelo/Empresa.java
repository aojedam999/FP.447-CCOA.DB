package CCOADB.FP.modelo;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    public Empresa() {

    }

    private String nombre;
    private List<Pedido> pedidos;
    private List<Cliente> clientes;
    private List<Articulo> articulos;

    public Empresa(String nombre) {
        this.nombre = nombre;
        this.pedidos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.articulos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public List<Pedido> getPedidos() { return pedidos; }

    public List<Cliente> getClientes() { return clientes; }

    public List<Articulo> getArticulos() { return articulos; }

    public void añadirCliente(Cliente c) { clientes.add(c); }

    public void mostrarClientes() { clientes.forEach(System.out::println); }

    public void mostrarClientesEstandar() {
        clientes.stream()
                .filter(c -> c instanceof ClienteEstandar)
                .forEach(System.out::println);
    }

    public void mostrarClientesPremium() {
        clientes.stream()
                .filter(c -> c instanceof ClientePremium)
                .forEach(System.out::println);
    }

    public void añadirArticulo(Articulo a) {
        articulos.add(a);
    }

    public void mostrarArticulo() {
        articulos.forEach(System.out::println);
    }

    public void añadirPedido(Pedido p) {
        pedidos.add(p);
    }

    public void eliminarPedido(Pedido p) {
        if (p.puedeEliminarse()) {
            pedidos.remove(p);
        }
    }

    public void mostrarPedidosPendientes(Cliente c) {
        pedidos.stream()
                .filter(p -> p.getCliente().equals(c))
                .filter(p -> p.getEstado() == EstadoEnvio.PENDIENTE)
                .forEach(System.out::println);
    }

    public void mostrarPedidosEnviados(Cliente c) {
        pedidos.stream()
                .filter(p -> p.getCliente().equals(c))
                .filter(p -> p.getEstado() == EstadoEnvio.ENVIADO)
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Empresa\n" +
                "----------------------------------\n" +
                "Nombre     : " + nombre + "\n" +
                "Clientes   : " + clientes.size() + "\n" +
                "Artículos  : " + articulos.size() + "\n" +
                "Pedidos    : " + pedidos.size();
    }
}
