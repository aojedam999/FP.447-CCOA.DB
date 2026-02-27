package fp447.onlinestore.modelo;

import java.time.LocalDateTime;

public class Pedido {

    private int numeroPedido;
    private LocalDateTime fechaHora;
    private int cantidadUnidades;
    private EstadoEnvio estado;

    private Cliente cliente;
    private Articulo articulo;

    public Pedido(int numeroPedido, LocalDateTime fechaHora, int cantidadUnidades,
                  Cliente cliente, Articulo articulo) {

        this.numeroPedido = numeroPedido;
        this.fechaHora = fechaHora;
        this.cantidadUnidades = cantidadUnidades;
        this.cliente = cliente;
        this.articulo = articulo;
        this.estado = EstadoEnvio.PENDIENTE; // por defecto
    }

    public int getNumeroPedido() {return numeroPedido;}

    public LocalDateTime getFechaHora() {return fechaHora;}

    public int getCantidadUnidades() {return cantidadUnidades;}

    public void setCantidadUnidades(int cantidadUnidades) {this.cantidadUnidades = cantidadUnidades;}

    public EstadoEnvio getEstado() {return estado;}

    public void setEstado(EstadoEnvio estado) {this.estado = estado;}

    public Cliente getCliente() {return cliente;}

    public Articulo getArticulo() {return articulo;}

    public double calcularTotal() {

        double totalBase = (articulo.getPrecioVenta() * cantidadUnidades)
                + articulo.getGastosEnvio();

        double descuento = cliente.calcularDescuentoEnvio();

        return totalBase - (totalBase * descuento);
    }

    public boolean puedeEliminarse() {
        return estado == EstadoEnvio.PENDIENTE;
    }

    public void actualizarEstado() {
        this.estado = EstadoEnvio.ENVIADO;
    }

    @Override
    public String toString() {
        return "Pedido\n" +
                "----------------------------------\n" +
                "Número de pedido     : " + numeroPedido + "\n" +
                "Fecha/Hora        : " + fechaHora + "\n" +
                "Cliente           : " + cliente.getNombre() + "\n" +
                "Artículo          : " + articulo.getDescripcion() + "\n" +
                "Cantidad unidades : " + cantidadUnidades + "\n" +
                "Estado            : " + estado + "\n" +
                "Total             : " + calcularTotal();
    }
}