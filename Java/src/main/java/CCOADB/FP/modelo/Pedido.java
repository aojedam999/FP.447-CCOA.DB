package CCOADB.FP.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int numeroPedido;

    //FK pedidos.id_cliente -> clientes.id_cliente
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    //FK pedidos.id_articulo -> articulos.id_articulo
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_articulo", nullable = false)
    private Articulo articulo;

    @Column(name = "unidades", nullable = false)
    private int unidades;

    // En BD se llama fecha_pedido
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEnvio estado;

    protected Pedido() {}

    public Pedido(int numeroPedido, LocalDateTime fechaHora, int unidades,
                  Cliente cliente, Articulo articulo) {

        this.numeroPedido = numeroPedido;
        this.fechaHora = fechaHora;
        this.unidades = unidades;
        this.cliente = cliente;
        this.articulo = articulo;
        this.estado = EstadoEnvio.PENDIENTE;
    }

    public int getNumeroPedido() { return numeroPedido; }

    public LocalDateTime getFechaHora() { return fechaHora; }

    public int getUnidades() { return unidades; }

    public void setUnidades(int unidades) { this.unidades = unidades; }

    public EstadoEnvio getEstado() { return estado; }

    public void setEstado(EstadoEnvio estado) { this.estado = estado; }

    public Cliente getCliente() { return cliente; }

    public Articulo getArticulo() { return articulo; }

    public double calcularTotal() {

        double totalProductos = articulo.getPrecioVenta() * unidades;
        double gastosEnvio = articulo.getGastosEnvio();
        double descuento = cliente.calcularDescuentoEnvio();

        double gastosConDescuento = gastosEnvio - (gastosEnvio * descuento);

        return totalProductos + gastosConDescuento;
    }

    public boolean puedeEliminarse() {
        return estado == EstadoEnvio.PENDIENTE && esCancelable();
    }

    public boolean esCancelable() {

        int prepMin = articulo.getTiempoPreparacionMin();
        long mins = Duration.between(fechaHora, LocalDateTime.now()).toMinutes();
        return mins <= prepMin;
    }

    public void actualizarEstado() {this.estado = EstadoEnvio.ENVIADO;}

    @Override
    public String toString() {
        return "Pedido\n" +
                "----------------------------------\n" +
                "Número de pedido: " + numeroPedido + "\n" +
                "Fecha y hora: " + fechaHora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                "Cliente: " + cliente.getNombre() + "\n" +
                "Artículo: " + articulo.getDescripcion() + "\n" +
                "Unidades: " + unidades + "\n" +
                "Estado: " + estado + "\n" +
                "Total: " + String.format("%.2f €", calcularTotal()).replace(".", ",");

    }

    public boolean estaPendiente() {return estado == EstadoEnvio.PENDIENTE;}

    public boolean estaEnviado() {return estado == EstadoEnvio.ENVIADO;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;

        Pedido pedido = (Pedido) o;

        return numeroPedido == pedido.numeroPedido;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numeroPedido);
    }

}