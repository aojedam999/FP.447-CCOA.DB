package CCOADB.FP.modelo;

public abstract class Cliente {

    private String email;
    private String nombre;
    private String domicilio;
    private String NIFNIE;

    public Cliente(String email, String nombre, String domicilio, String NIFNIE) {
        this.email = email;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.NIFNIE = NIFNIE;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getDomicilio() {return domicilio;}

    public void setDomicilio(String domicilio) {this.domicilio = domicilio;}

    public String getNIFNIE() {return NIFNIE;}

    public void setNIFNIE(String NIFNIE) {this.NIFNIE = NIFNIE;}

    @Override
    public String toString() {
        return "Cliente\n" +
                "----------------------------------\n" +
                "Nombre     : " + nombre + "\n" +
                "Email    : " + email + "\n" +
                "NIF/NIE : " + NIFNIE + "\n" +
                "Domicilio   : " + domicilio;
    }

    public abstract double calcularDescuentoEnvio();
}