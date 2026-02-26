package fp447.onlinestore.modelo;

public class Articulo {

    private String codigo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacionMin;

    public Articulo(String codigo, String descripcion, double precioVenta,
                    double gastosEnvio, int tiempoPreparacionMin) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    public String getCodigo() {return codigo;}

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public double getPrecioVenta() {return precioVenta;}

    public void setPrecioVenta(double precioVenta) {this.precioVenta = precioVenta;}

    public double getGastosEnvio() {return gastosEnvio;}

    public void setGastosEnvio(double gastosEnvio) {this.gastosEnvio = gastosEnvio;}

    public int getTiempoPreparacionMin() {return tiempoPreparacionMin;}

    public void setTiempoPreparacionMin(int tiempoPreparacionMin) {
        this.tiempoPreparacionMin = tiempoPreparacionMin;
    }

    @Override
    public String toString() {
        return "Artículo\n" +
                "----------------------------------\n" +
                "Código               : " + codigo + "\n" +
                "Descripción          : " + descripcion + "\n" +
                "Precio de venta         : " + precioVenta + "\n" +
                "Gastos de envío         : " + gastosEnvio + "\n" +
                "Tiempo de preparación   : " + tiempoPreparacionMin + " min";
    }
}