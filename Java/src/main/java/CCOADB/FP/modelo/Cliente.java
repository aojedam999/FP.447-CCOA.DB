package CCOADB.FP.modelo;
import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cliente")

public abstract class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int id;
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "domicilio", nullable = false, length = 150)
    private String domicilio;

    @Column(name = "nif_nie", nullable = false, length = 20, unique = true)
    private String NIFNIE;

    // Constructor vacío obligatorio para JPA
    protected Cliente() {}

    public Cliente(int id, String email, String nombre, String domicilio, String NIFNIE) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.NIFNIE = NIFNIE;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Cliente\n" +
                "----------------------------------\n" +
                "Nombre: " + nombre + "\n" +
                "Email: " + email + "\n" +
                "NIF/NIE: " + NIFNIE + "\n" +
                "Domicilio: " + domicilio;
    }

    public abstract double calcularDescuentoEnvio();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente cliente = (Cliente) o;

        return email.equals(cliente.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

}