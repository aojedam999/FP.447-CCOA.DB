package CCOADB.FP.modelo;

import java.util.ArrayList;
import java.util.List;

// Esta clase ya no se usa para persistencia.
// Los datos ahora se gestionan en la base de datos mediante DAO.
// Se mantiene temporalmente para lógica de negocio.
public class Empresa {

    public Empresa() {

    }

    private String nombre;

    // DEPRECADO: ya no se usan como almacenamiento principal
    private List<Pedido> pedidos;
    private List<Cliente> clientes;
    private List<Articulo> articulos;

    public Empresa(String nombre) {
        this.nombre = nombre;

        // Inicialización temporal (ya no representa la fuente real de datos)
        this.pedidos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.articulos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    // DEPRECADO: ahora los pedidos vienen de BD
    public List<Pedido> getPedidos() { return pedidos; }

    // DEPRECADO: ahora los clientes vienen de BD
    public List<Cliente> getClientes() { return clientes; }

    // DEPRECADO: ahora los artículos vienen de BD
    public List<Articulo> getArticulos() { return articulos; }

    // DEPRECADO: ahora los clientes se guardan en BD
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

    // DEPRECADO: ahora los artículos se guardan en BD
    public void añadirArticulo(Articulo a) {
        articulos.add(a);
    }

    public void mostrarArticulo() {
        articulos.forEach(System.out::println);
    }

    // DEPRECADO: ahora los pedidos se guardan en BD
    public void añadirPedido(Pedido p) {
        pedidos.add(p);
    }

    public void eliminarPedido(Pedido p) {
        if (p.puedeEliminarse()) {
            pedidos.remove(p);
        }
    }

    // Lógica de negocio: SE MANTIENE
    public void mostrarPedidosPendientes(Cliente c) {
        pedidos.stream()
                .filter(p -> p.getCliente().equals(c))
                .filter(p -> p.getEstado() == EstadoEnvio.PENDIENTE)
                .forEach(System.out::println);
    }

    // Lógica de negocio: SE MANTIENE
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
                "Nombre: " + nombre + "\n" +
                "Clientes: " + clientes.size() + "\n" +
                "Artículos: " + articulos.size() + "\n" +
                "Pedidos: " + pedidos.size();
    }

    // DEPRECADO: ahora las búsquedas deben hacerse en BD (DAO)
    public Cliente buscarClientePorEmail(String email) {
        for (Cliente c : clientes) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    // DEPRECADO: ahora las búsquedas deben hacerse en BD (DAO)
    public Articulo buscarArticuloPorCodigo(String codigo) {
        for (Articulo a : articulos) {
            if (a.getCodigo().equals(codigo)) {
                return a;
            }
        }
        return null;
    }

    // DEPRECADO: ahora las búsquedas deben hacerse en BD (DAO)
    public Pedido buscarPedidoPorNumero(int numeroPedido) {
        for (Pedido p : pedidos) {
            if (p.getNumeroPedido() == numeroPedido) {
                return p;
            }
        }
        return null;
    }

}