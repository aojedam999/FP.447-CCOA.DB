package CCOADB.FP.modelo.excepciones;

/**
 * Se lanza cuando se intenta acceder a un artículo que no existe
 * en el catálogo del sistema.
 *
 * Puede ocurrir al:
 * - buscar un artículo por código
 * - intentar añadir un artículo inexistente a un pedido
 */
public class ArticuloNoEncontradoException extends ExcepcionTienda {

    public ArticuloNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}