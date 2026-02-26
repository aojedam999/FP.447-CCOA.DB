package fp447.onlinestore.modelo;

public class ClientePremium extends Cliente {

    private double cuotaAnual;

    public ClientePremium(String email, String nombre, String domicilio, String NIFNIE, double cuotaAnual) {
        super(email, nombre, domicilio, NIFNIE);
        this.cuotaAnual = cuotaAnual;
    }

    public double getCuotaAnual() {return cuotaAnual;}

    public void setCuotaAnual(double cuotaAnual) {this.cuotaAnual = cuotaAnual;}

    @Override
    public boolean esPremium() {
        return true;
    }

    @Override
    public double calcularDescuentoEnvio() {
        return 0.15; // ejemplo de descuento (15%)
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nTipo      : Premium" +
                "\nCuota     : " + cuotaAnual;
    }
}
