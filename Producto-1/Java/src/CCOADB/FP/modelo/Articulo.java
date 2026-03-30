package CCOADB.FP.modelo;

public class Articulo {
    private int id;
    private String codigo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacionMin;
    private int stockDisponible;

    public Articulo(int id, String codigo, String descripcion, double precioVenta,
                    double gastosEnvio, int tiempoPreparacionMin, int stockDisponible) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacionMin = tiempoPreparacionMin;
        this.stockDisponible = stockDisponible;
    }

    public Articulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacionMin, int stockDisponible) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Artículo\n" +
                "----------------------------------\n" +
                "Código: " + codigo + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Precio de venta: " + String.format("%.2f €", precioVenta).replace(".", ",") + "\n" +
                "Gastos de envío: " + String.format("%.2f €", gastosEnvio).replace(".", ",") + "\n" +
                "Tiempo de preparación: " + tiempoPreparacionMin + " min\n" +
                "Stock disponible: " + stockDisponible + " unidad/es";
    }

    public boolean hayStock(int unidades) {return stockDisponible >= unidades;}

    public void reducirStock(int unidades) {stockDisponible -= unidades;}

    public void aumentarStock(int unidades) {stockDisponible += unidades;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Articulo)) return false;

        Articulo articulo = (Articulo) o;

        return codigo.equals(articulo.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

}