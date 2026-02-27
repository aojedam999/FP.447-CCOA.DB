package fp447.onlinestore.modelo;

public class ClientePremium extends Cliente {

    private double cuotaAnual;
    private int descuentoEnvio;

    public ClientePremium(String email, String nombre, String domicilio, String NIFNIE,
                          double cuotaAnual, int descuentoEnvio) {
        super(email, nombre, domicilio, NIFNIE);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
    }

    public double getCuotaAnual() { return cuotaAnual; }

    public void setCuotaAnual(double cuotaAnual) { this.cuotaAnual = cuotaAnual; }

    public int getDescuentoEnvio() { return descuentoEnvio; }

    public void setDescuentoEnvio(int descuentoEnvio) { this.descuentoEnvio = descuentoEnvio; }

    @Override
    public double calcularDescuentoEnvio() {
        return descuentoEnvio / 100.0;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nTipo      : Premium" +
                "\nCuota     : " + cuotaAnual +
                "\nDescuento : " + descuentoEnvio + "%";
    }
}