package CCOADB.FP.modelo.excepciones;

/**
 * Se lanza cuando se intenta acceder a un pedido que no existe
 * en el sistema.
 *
 * Por ejemplo al buscar un pedido por su número identificador.
 */
public class PedidoNoEncontradoException extends ExcepcionTienda {

    public PedidoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}