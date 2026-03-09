package CCOADB.FP.modelo.excepciones;

/**
 * Se lanza cuando se intenta acceder a un cliente que no existe
 * en el sistema.
 *
 * Por ejemplo:
 * - buscar un cliente por email que no está registrado
 * - crear un pedido para un cliente inexistente
 */
public class ClienteNoEncontradoException extends ExcepcionTienda {

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}