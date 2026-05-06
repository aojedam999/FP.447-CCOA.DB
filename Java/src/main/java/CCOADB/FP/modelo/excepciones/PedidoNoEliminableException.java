package CCOADB.FP.modelo.excepciones;

/**
 * Se lanza cuando se intenta eliminar un pedido que no puede
 * ser eliminado.
 *
 * Esto puede ocurrir cuando el pedido ya ha sido enviado o
 * procesado y el sistema no permite su eliminación.
 */
public class PedidoNoEliminableException extends ExcepcionTienda {

    public PedidoNoEliminableException(String mensaje) {
        super(mensaje);
    }
}