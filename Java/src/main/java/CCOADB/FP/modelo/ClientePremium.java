package CCOADB.FP.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PREMIUM")

public class ClientePremium extends Cliente {

    @Column(name = "cuota_anual")
    private double cuotaAnual;

    @Column(name = "descuento_envio")
    private int descuentoEnvio;

    protected ClientePremium() {}

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
                "\nTipo: Cliente Premium" +
                "\nCuota anual: " + String.format("%.2f €", cuotaAnual).replace(".", ",") +
                "\nDescuento: " + descuentoEnvio + "%";
    }
}