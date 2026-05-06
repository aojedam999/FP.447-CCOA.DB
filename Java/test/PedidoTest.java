package CCOADB.FP.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias de la clase Pedido.
 *
 * Se comprueban dos métodos con lógica de negocio:
 * - calcularTotal()
 * - puedeEliminarse()
 */
public class PedidoTest {

    /**
     * Comprueba que el total del pedido se calcula correctamente
     * para un cliente estándar, sin descuento de envío.
     */
    @Test
    void calcularTotal_clienteEstandar_devuelveTotalCorrecto() {
        Cliente cliente = new ClienteEstandar(
                "ana@email.com",
                "Ana",
                "Calle Mayor 1",
                "12345678A"
        );

        Articulo articulo = new Articulo(
                1, "A1",
                "Teclado",
                20.0,
                5.0,
                30,
                10
        );

        Pedido pedido = new Pedido(
                1,
                LocalDateTime.now(),
                2,
                cliente,
                articulo
        );

        double total = pedido.calcularTotal();

        // 20 * 2 = 40
        // gastos de envío = 5
        // descuento = 0%
        // total esperado = 45
        assertEquals(45.0, total, 0.001);
    }

    /**
     * Comprueba que el total del pedido se calcula correctamente
     * para un cliente premium aplicando descuento sobre el envío.
     */
    @Test
    void calcularTotal_clientePremium_aplicaDescuentoEnvioCorrectamente() {
        Cliente cliente = new ClientePremium(
                "premium@email.com",
                "Carlos",
                "Avenida Central 10",
                "87654321B",
                30.0,
                20
        );

        Articulo articulo = new Articulo(
                2, "A2",
                "Ratón",
                15.0,
                10.0,
                30,
                10
        );

        Pedido pedido = new Pedido(
                2,
                LocalDateTime.now(),
                2,
                cliente,
                articulo
        );

        double total = pedido.calcularTotal();

        // productos = 15 * 2 = 30
        // envío = 10
        // descuento premium = 20% => envío final = 8
        // total esperado = 38
        assertEquals(38.0, total, 0.001);
    }

    /**
     * Comprueba que un pedido pendiente y todavía dentro del tiempo
     * de preparación puede eliminarse.
     */
    @Test
    void puedeEliminarse_pedidoRecientePendiente_devuelveTrue() {
        Cliente cliente = new ClienteEstandar(
                "cliente@email.com",
                "Lucia",
                "Calle Sol 4",
                "11111111C"
        );

        Articulo articulo = new Articulo(
                3, "A3",
                "Monitor",
                100.0,
                12.0,
                30,
                5
        );

        Pedido pedido = new Pedido(
                3,
                LocalDateTime.now().minusMinutes(10),
                1,
                cliente,
                articulo
        );

        assertTrue(pedido.puedeEliminarse());
    }

    /**
     * Comprueba que un pedido no puede eliminarse cuando ya ha superado
     * el tiempo de preparación del artículo.
     */
    @Test
    void puedeEliminarse_pedidoFueraDeTiempo_devuelveFalse() {
        Cliente cliente = new ClienteEstandar(
                "cliente2@email.com",
                "Mario",
                "Calle Luna 8",
                "22222222D"
        );

        Articulo articulo = new Articulo(
                4, "A4",
                "Portatil",
                900.0,
                15.0,
                30,
                3
        );

        Pedido pedido = new Pedido(
                4,
                LocalDateTime.now().minusMinutes(45),
                1,
                cliente,
                articulo
        );

        assertFalse(pedido.puedeEliminarse());
    }
}