package fp447.onlinestore.modelo;

public class ClienteEstandar extends Cliente {

    public ClienteEstandar(String email, String nombre, String domicilio, String NIFNIE) {
        super(email, nombre, domicilio, NIFNIE);
    }

    @Override
    public boolean esPremium() {
        return false;
    }

    @Override
    public double calcularDescuentoEnvio() {
        return 0.0;
    }
}
