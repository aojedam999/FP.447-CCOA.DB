package CCOADB.FP.modelo.excepciones;

/**
 * Se lanza cuando se intenta realizar una operación que requiere
 * más unidades de un artículo de las disponibles en stock.
 *
 * Por ejemplo al intentar crear un pedido con una cantidad de
 * productos superior al stock disponible.
 */
public class StockInsuficienteException extends ExcepcionTienda {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}