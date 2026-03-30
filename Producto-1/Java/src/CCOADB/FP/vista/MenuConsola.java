package CCOADB.FP.vista;

import CCOADB.FP.controlador.Controlador;
import CCOADB.FP.modelo.*;
import CCOADB.FP.modelo.excepciones.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MenuConsola {

    private final Controlador controlador;

    private final Scanner sc;

    public MenuConsola(Controlador controlador) {
        this.controlador = controlador;
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            controlador.actualizarEstadosEnvio(); // para que "pendiente/enviado" tenga sentido

            System.out.println("\n=== ONLINE STORE ===");
            System.out.println("1. Gestión Artículos");
            System.out.println("2. Gestión Clientes");
            System.out.println("3. Gestión Pedidos");
            System.out.println("0. Salir");

            opcion = leerInt("Opción: ", 0, 3);

            switch (opcion) {
                case 1 -> menuArticulos();
                case 2 -> menuClientes();
                case 3 -> menuPedidos();
                case 0 -> System.out.println("Saliendo...");
            }
        } while (opcion != 0);

        sc.close();
    }

    // ---------------- MENÚ ARTÍCULOS ----------------
    private void menuArticulos() {
        int op;
        do {
            System.out.println("\n--- Gestión Artículos ---");
            System.out.println("1. Añadir Artículo");
            System.out.println("2. Mostrar Artículos");
            System.out.println("0. Volver");

            op = leerInt("Opción: ", 0, 2);

            switch (op) {
                case 1 -> anadirArticulo();
                case 2 -> mostrarArticulos();
            }
        } while (op != 0);
    }

    private void anadirArticulo() {
        System.out.println("\n[Alta Artículo]");
        String codigo = leerTexto("Código (alfanumérico): ");

        try {
            controlador.buscarArticuloPorCodigo(codigo);
            System.out.println("⚠️ Ya existe un artículo con ese código.");
            return;
        } catch (ArticuloNoEncontradoException ignored) {
            // No existe así que seguimos
        }

        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ", 0);
        double gastosEnvio = leerDouble("Gastos de envío: ", 0);
        int tiempoPrep = leerInt("Tiempo de preparación (min): ", 0, Integer.MAX_VALUE);

        int stock = leerInt("Stock disponible: ", 0, Integer.MAX_VALUE);

        Articulo articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPrep, stock);
        controlador.addArticulo(articulo);

        System.out.println("✅ Artículo añadido.");
    }

    private void mostrarArticulos() {
        System.out.println("\n[Listado Artículos]");
        if (controlador.getArticulos().isEmpty()) {
            System.out.println("No hay artículos.");
            return;
        }
        for (Articulo a : controlador.getArticulos()) {
            System.out.println(a);
        }
    }

    // ---------------- MENÚ CLIENTES ----------------
    private void menuClientes() {
        int op;
        do {
            System.out.println("\n--- Gestión Clientes ---");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Mostrar Clientes Estándar");
            System.out.println("4. Mostrar Clientes Premium");
            System.out.println("0. Volver");

            op = leerInt("Opción: ", 0, 4);

            switch (op) {
                case 1 -> anadirCliente();
                case 2 -> mostrarClientes();
                case 3 -> mostrarClientesEstandar();
                case 4 -> mostrarClientesPremium();
            }
        } while (op != 0);
    }

    private void anadirCliente() {
        System.out.println("\n[Alta Cliente]");
        String email = leerTexto("Email (identificador): ");

        try {
            controlador.buscarClientePorEmail(email);
            System.out.println("⚠️ Ya existe un cliente con ese email.");
            return;
        } catch (ClienteNoEncontradoException ignored) {
            // No existe así que seguimos
        }

        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nifNie = leerTexto("NIF/NIE: ");

        int tipo = leerInt("Tipo (1 = Estándar, 2 = Premium): ", 1, 2);

        Cliente cliente;
        if (tipo == 1) {
            cliente = new ClienteEstandar(email, nombre, domicilio, nifNie);
        } else {
            // Enunciado: cuota 30€ y descuento envío 20%
            cliente = new ClientePremium(email, nombre, domicilio, nifNie, 30.0, 20);
        }

        try {
            controlador.addCliente(cliente);
            System.out.println("✅ Cliente añadido.");
        } catch (Exception e) {
            System.out.println("❌ Error al añadir cliente: " + e.getMessage());
        }
    }

    private void mostrarClientes() {
        System.out.println("\n[Listado Clientes]");
        if (controlador.getClientes().isEmpty()) {
            System.out.println("No hay clientes.");
            return;
        }
        for (Cliente c : controlador.getClientes()) {
            System.out.println(c);
        }
    }

    private void mostrarClientesEstandar() {
        System.out.println("\n[Clientes Estándar]");

        if (!hayClientesDelTipo(ClienteEstandar.class)) {
            preguntarAltaClientePorTipo("Estándar");
            return;
        }

        controlador.mostrarClientesEstandar();
    }

    private void mostrarClientesPremium() {
        System.out.println("\n[Clientes Premium]");

        if (!hayClientesDelTipo(ClientePremium.class)) {
            preguntarAltaClientePorTipo("Premium");
            return;
        }

        controlador.mostrarClientesPremium();
    }

    private boolean hayClientesDelTipo(Class<?> tipoCliente) {
        for (Cliente c : controlador.getClientes()) {
            if (tipoCliente.isInstance(c)) {
                return true;
            }
        }
        return false;
    }

    private void preguntarAltaClientePorTipo(String tipoCliente) {
        System.out.println("Ahora mismo no hay ningún Cliente " + tipoCliente + ".");
        String resp = leerTextoOpcional("¿Quiere añadir un Cliente " + tipoCliente + "? (s/n): ")
                .trim()
                .toLowerCase();

        if (resp.equals("s")) {
            if (tipoCliente.equals("Estándar")) {
                anadirClienteEstandar();
            } else if (tipoCliente.equals("Premium")) {
                anadirClientePremium();
            }
        }
    }

    private void anadirClienteEstandar() {
        System.out.println("\n[Alta Cliente Estándar]");
        String email = leerTexto("Email (identificador): ");

        try {
            controlador.buscarClientePorEmail(email);
            System.out.println("⚠️ Ya existe un cliente con ese email.");
            return;
        } catch (ClienteNoEncontradoException ignored) {
            // No existe así que seguimos
        }

        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nifNie = leerTexto("NIF/NIE: ");

        Cliente cliente = new ClienteEstandar(email, nombre, domicilio, nifNie);
        try {
            controlador.addCliente(cliente);
            System.out.println("✅ Cliente Estándar añadido.");
        } catch (Exception e) {
            System.out.println("❌ Error al añadir cliente estándar: " + e.getMessage());
        }
    }

    private void anadirClientePremium() {
        System.out.println("\n[Alta Cliente Premium]");
        String email = leerTexto("Email (identificador): ");

        try {
            controlador.buscarClientePorEmail(email);
            System.out.println("⚠️ Ya existe un cliente con ese email.");
            return;
        } catch (ClienteNoEncontradoException ignored) {
            // No existe así que seguimos
        }

        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nifNie = leerTexto("NIF/NIE: ");

        Cliente cliente = new ClientePremium(email, nombre, domicilio, nifNie, 30.0, 20);
        try {
            controlador.addCliente(cliente);
            System.out.println("✅ Cliente Premium añadido.");
        } catch (Exception e) {
            System.out.println("❌ Error al añadir cliente premium: " + e.getMessage());
        }
    }

    // ---------------- MENÚ PEDIDOS ----------------
    private void menuPedidos() {
        int op;
        do {
            controlador.actualizarEstadosEnvio();

            System.out.println("\n--- Gestión Pedidos ---");
            System.out.println("1. Añadir Pedido");
            System.out.println("2. Eliminar Pedido (si se puede)");
            System.out.println("3. Mostrar pedidos pendientes (filtrar por cliente)");
            System.out.println("4. Mostrar pedidos enviados (filtrar por cliente)");
            System.out.println("0. Volver");

            op = leerInt("Opción: ", 0, 4);

            switch (op) {
                case 1 -> anadirPedido();
                case 2 -> eliminarPedido();
                case 3 -> mostrarPedidosPorEstado(EstadoEnvio.PENDIENTE);
                case 4 -> mostrarPedidosPorEstado(EstadoEnvio.ENVIADO);
            }
        } while (op != 0);
    }

    private void anadirPedido() {
        System.out.println("\n[Alta Pedido]");

        if (controlador.getArticulos().isEmpty()) {
            System.out.println("⚠️ No puedes crear pedidos si no hay artículos.");

            String resp = leerTextoOpcional("¿Quieres crear un artículo? (s/n): ")
                    .trim()
                    .toLowerCase();

            if (resp.equals("s")) {
                anadirArticulo();
            }

            return;
        }

        int numPedido = leerInt("Número de pedido: ", 1, Integer.MAX_VALUE);

        try {
            controlador.buscarPedidoPorNumero(numPedido);
            System.out.println("⚠️ Ya existe un pedido con ese número.");
            return;
        } catch (PedidoNoEncontradoException ignored) {
            // No existe así que seguimos
        }

        // Cliente: si no existe, se da de alta (como pide el enunciado)
        String email = leerTexto("Email cliente: ");
        System.out.println("-----------------------------------------------------");

        Cliente cliente;
        try {
            cliente = controlador.buscarClientePorEmail(email);
        } catch (ClienteNoEncontradoException e) {
            System.out.println("Cliente no existe. Vamos a darlo de alta:");
            cliente = altaRapidaClienteConEmail(email);
            try {
                controlador.addCliente(cliente);
                System.out.println("✅ Cliente creado.");
            } catch (Exception e1) {
                System.out.println("❌ Error al crear cliente: " + e.getMessage());
            }
            System.out.println();
        }

        // Artículo debe existir
        String codigo = leerTexto("Código artículo (debe existir): ");
        Articulo articulo;
        try {
            articulo = controlador.buscarArticuloPorCodigo(codigo);
        } catch (ArticuloNoEncontradoException e) {
            System.out.println("❌ El artículo no existe. Crea primero el artículo.");
            return;
        }

        int unidades = leerInt("Unidades: ", 1, Integer.MAX_VALUE);

        Pedido pedido = new Pedido(numPedido, LocalDateTime.now(), unidades, cliente, articulo);

        try {
            controlador.addPedido(pedido);
            System.out.println("✅ Pedido añadido.");
            System.out.printf("Total del pedido: %.2f €%n", pedido.calcularTotal());
            System.out.println();
        } catch (StockInsuficienteException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private Cliente altaRapidaClienteConEmail(String email) {
        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nifNie = leerTexto("NIF/NIE: ");
        int tipo = leerInt("Tipo (1 = Estándar, 2 = Premium): ", 1, 2);

        if (tipo == 1) {
            return new ClienteEstandar(email, nombre, domicilio, nifNie);
        } else {
            return new ClientePremium(email, nombre, domicilio, nifNie, 30.0, 20);
        }
    }

    private void eliminarPedido() {
        System.out.println("\n[Eliminar Pedido]");
        if (controlador.getPedidos().isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }

        int num = leerInt("Número de pedido a eliminar: ", 1, Integer.MAX_VALUE);
        Pedido pedido;
        try {
            pedido = controlador.buscarPedidoPorNumero(num);
        } catch (PedidoNoEncontradoException e) {
            System.out.println("❌ " + e.getMessage());
            return;
        }

        try {
            controlador.eliminarPedido(pedido);
            System.out.println("✅ Pedido eliminado.");
        } catch (PedidoNoEliminableException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void mostrarPedidosPorEstado(EstadoEnvio estado) {
        System.out.println("\n[Mostrar pedidos " + estado + "]");

        if (controlador.getPedidos().isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }

        String email = leerTextoOpcional("Email para filtrar (ENTER = sin filtro): ").trim();
        System.out.println();

        if (email.isEmpty()) {
            // Sin filtro: mostramos todos por estado
            boolean alguno = false;
            for (Pedido p : controlador.getPedidos()) {
                if (p.getEstado() == estado) {
                    System.out.println(p);
                    System.out.println();
                    alguno = true;
                }
            }
            if (!alguno) System.out.println("No hay pedidos en estado " + estado + ".");
            return;
        }


        Cliente cliente;
        try {
            cliente = controlador.buscarClientePorEmail(email);
        } catch (ClienteNoEncontradoException e) {
            System.out.println("❌ " + e.getMessage());
            return;
        }


        if (estado == EstadoEnvio.PENDIENTE) {
            controlador.mostrarPedidosPendientes(cliente);
        } else {
            controlador.mostrarPedidosEnviados(cliente);
        }
    }


    // ---------------- LECTURAS SEGURAS ----------------
    private int leerInt(String msg, int min, int max) {
        while (true) {
            System.out.print(msg);
            String entrada = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor < min || valor > max) {
                    System.out.println("Introduce un número entre " + min + " y " + max + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Escribe un número.");
            }
        }
    }

    private double leerDouble(String msg, double min) {
        while (true) {
            System.out.print(msg);
            String entrada = sc.nextLine().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(entrada);
                if (valor < min) {
                    System.out.println("Introduce un número mayor o igual que " + min + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Escribe un número (ej: 12.50).");
            }
        }
    }

    private String leerTexto(String msg) {
        while (true) {
            System.out.print(msg);
            String txt = sc.nextLine().trim();
            if (!txt.isEmpty()) return txt;
            System.out.println("No puede estar vacío.");
        }
    }

    private String leerTextoOpcional(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
}