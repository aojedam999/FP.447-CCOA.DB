package CCOADB.FP.modelo.excepciones;

/**
 * Excepción base del sistema.
 *
 * Todas las excepciones personalizadas del proyecto heredan de esta clase.
 * Se utiliza para representar errores de lógica de negocio dentro del modelo
 * de la aplicación (clientes inexistentes, artículos no encontrados, etc.).
 *
 * Al centralizar las excepciones en una jerarquía común podemos capturarlas
 * de forma genérica usando ExcepcionTienda si es necesario.
 */
public class ExcepcionTienda extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje Mensaje descriptivo del error ocurrido.
     */
    public ExcepcionTienda(String mensaje) {
        super(mensaje);
    }
}