package fp447.onlinestore.modelo;

public class Articulo {

    private String codigo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacionMin;
    private int stockDisponible;

    public Articulo(String codigo, String descripcion, double precioVenta,
                    double gastosEnvio, int tiempoPreparacionMin, int stockDisponible) {

        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
        this.stockDisponible = stockDisponible;
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

    public int getStockDisponible() {return stockDisponible;}

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    @Override
    public String toString() {
        return "Artículo\n" +
                "----------------------------------\n" +
                "Código               : " + codigo + "\n" +
                "Descripción          : " + descripcion + "\n" +
                "Precio de venta         : " + precioVenta + "\n" +
                "Gastos de envío         : " + gastosEnvio + "\n" +
                "Tiempo de preparación   : " + tiempoPreparacionMin + " min\n" +
                "Stock disponible        : " + stockDisponible;
    }
}