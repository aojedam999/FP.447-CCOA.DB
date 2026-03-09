package CCOADB.FP.modelo;

public class ClienteEstandar extends Cliente {

    public ClienteEstandar(String email, String nombre, String domicilio, String NIFNIE) {
        super(email, nombre, domicilio, NIFNIE);
    }

    @Override
    public double calcularDescuentoEnvio() {
        return 0.0;
    }

    @Override
    public String toString() {
        return super.toString() + "\nTipo: Cliente Estándar";
    }

}
