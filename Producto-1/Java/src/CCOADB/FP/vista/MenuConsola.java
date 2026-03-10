package CCOADB.FP.vista;

import CCOADB.FP.controlador.Controlador;
import CCOADB.FP.modelo.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {


    private final Controlador controlador;


    private final Empresa empresa;

    private final Scanner sc;

    public MenuConsola(Controlador controlador) {
        this.controlador = controlador;
        this.empresa = new Empresa("Online Store");
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            actualizarEstadosEnvio(); // para que "pendiente/enviado" tenga sentido

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

        if (buscarArticuloPorCodigo(codigo) != null) {
            System.out.println("⚠️ Ya existe un artículo con ese código.");
            return;
        }

        String descripcion = leerTexto("Descripción: ");
        double precioVenta = leerDouble("Precio de venta: ", 0);
        double gastosEnvio = leerDouble("Gastos de envío: ", 0);
        int tiempoPrep = leerInt("Tiempo preparación (min): ", 0, Integer.MAX_VALUE);

        int stock = leerInt("Stock disponible: ", 0, Integer.MAX_VALUE);

        Articulo articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPrep, stock);
        empresa.añadirArticulo(articulo);

        System.out.println("✅ Artículo añadido.");
    }

    private void mostrarArticulos() {
        System.out.println("\n[Listado Artículos]");
        if (empresa.getArticulos().isEmpty()) {
            System.out.println("No hay artículos.");
            return;
        }
        empresa.mostrarArticulo();
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

        if (buscarClientePorEmail(email) != null) {
            System.out.println("⚠️ Ya existe un cliente con ese email.");
            return;
        }

        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nifNie = leerTexto("NIF/NIE: ");

        int tipo = leerInt("Tipo (1=Estándar, 2=Premium): ", 1, 2);

        Cliente cliente;
        if (tipo == 1) {
            cliente = new ClienteEstandar(email, nombre, domicilio, nifNie);
        } else {
            // Enunciado: cuota 30€ y descuento envío 20%
            cliente = new ClientePremium(email, nombre, domicilio, nifNie, 30.0, 20);
        }

        empresa.añadirCliente(cliente);
        System.out.println("✅ Cliente añadido.");
    }

    private void mostrarClientes() {
        System.out.println("\n[Listado Clientes]");
        if (empresa.getClientes().isEmpty()) {
            System.out.println("No hay clientes.");
            return;
        }
        empresa.mostrarClientes();
    }

    private void mostrarClientesEstandar() {
        System.out.println("\n[Clientes Estándar]");
        empresa.mostrarClientesEstandar();
    }

    private void mostrarClientesPremium() {
        System.out.println("\n[Clientes Premium]");
        empresa.mostrarClientesPremium();
    }

    // ---------------- MENÚ PEDIDOS ----------------
    private void menuPedidos() {
        int op;
        do {
            actualizarEstadosEnvio();

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

        if (empresa.getArticulos().isEmpty()) {
            System.out.println("⚠️ No puedes crear pedidos si no hay artículos.");
            return;
        }

        int numPedido = leerInt("Número de pedido: ", 1, Integer.MAX_VALUE);

        if (buscarPedidoPorNumero(numPedido) != null) {
            System.out.println("⚠️ Ya existe un pedido con ese número.");
            return;
        }

        // Cliente: si no existe, se da de alta (como pide el enunciado)
        String email = leerTexto("Email cliente: ");
        Cliente cliente = buscarClientePorEmail(email);

        if (cliente == null) {
            System.out.println("Cliente no existe. Vamos a darlo de alta:");
            cliente = altaRapidaClienteConEmail(email);
            empresa.añadirCliente(cliente);
            System.out.println("✅ Cliente creado.");
        }

        // Artículo debe existir
        String codigo = leerTexto("Código artículo (debe existir): ");
        Articulo articulo = buscarArticuloPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("❌ El artículo no existe. Crea primero el artículo.");
            return;
        }

        int unidades = leerInt("Unidades: ", 1, Integer.MAX_VALUE);

        Pedido pedido = new Pedido(numPedido, LocalDateTime.now(), unidades, cliente, articulo);
        empresa.añadirPedido(pedido);

        System.out.println("✅ Pedido añadido.");
        System.out.println("Total pedido: " + pedido.calcularTotal());
    }

    private Cliente altaRapidaClienteConEmail(String email) {
        String nombre = leerTexto("Nombre: ");
        String domicilio = leerTexto("Domicilio: ");
        String nifNie = leerTexto("NIF/NIE: ");
        int tipo = leerInt("Tipo (1=Estándar, 2=Premium): ", 1, 2);

        if (tipo == 1) {
            return new ClienteEstandar(email, nombre, domicilio, nifNie);
        } else {
            return new ClientePremium(email, nombre, domicilio, nifNie, 30.0, 20);
        }
    }

    private void eliminarPedido() {
        System.out.println("\n[Eliminar Pedido]");
        if (empresa.getPedidos().isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }

        int num = leerInt("Número de pedido a eliminar: ", 1, Integer.MAX_VALUE);
        Pedido pedido = buscarPedidoPorNumero(num);

        if (pedido == null) {
            System.out.println("❌ Pedido no encontrado.");
            return;
        }


        if (!esCancelablePorTiempo(pedido)) {
            System.out.println("❌ No se puede eliminar: ha pasado el tiempo de preparación del artículo.");
            return;
        }


        if (pedido.getEstado() == EstadoEnvio.ENVIADO) {
            System.out.println("❌ No se puede eliminar: el pedido ya está ENVIADO.");
            return;
        }

        empresa.eliminarPedido(pedido);
        System.out.println("✅ Pedido eliminado.");
    }

    private void mostrarPedidosPorEstado(EstadoEnvio estado) {
        System.out.println("\n[Mostrar pedidos " + estado + "]");

        if (empresa.getPedidos().isEmpty()) {
            System.out.println("No hay pedidos.");
            return;
        }

        String email = leerTextoOpcional("Email para filtrar (ENTER = sin filtro): ").trim();

        if (email.isEmpty()) {
            // Sin filtro: mostramos todos por estado
            boolean alguno = false;
            for (Pedido p : empresa.getPedidos()) {
                if (p.getEstado() == estado) {
                    System.out.println(p);
                    System.out.println();
                    alguno = true;
                }
            }
            if (!alguno) System.out.println("No hay pedidos en estado " + estado + ".");
            return;
        }


        Cliente cliente = buscarClientePorEmail(email);
        if (cliente == null) {
            System.out.println("❌ No existe un cliente con ese email.");
            return;
        }

        if (estado == EstadoEnvio.PENDIENTE) {
            empresa.mostrarPedidosPendientes(cliente);
        } else {
            empresa.mostrarPedidosEnviados(cliente);
        }
    }

    // ---------------- HELPERS DE BÚSQUEDA ----------------
    private Cliente buscarClientePorEmail(String email) {
        for (Cliente c : empresa.getClientes()) {
            if (c.getEmail().equalsIgnoreCase(email)) return c;
        }
        return null;
    }

    private Articulo buscarArticuloPorCodigo(String codigo) {
        for (Articulo a : empresa.getArticulos()) {
            if (a.getCodigo().equalsIgnoreCase(codigo)) return a;
        }
        return null;
    }

    private Pedido buscarPedidoPorNumero(int numero) {
        for (Pedido p : empresa.getPedidos()) {
            if (p.getNumeroPedido() == numero) return p;
        }
        return null;
    }

    // ---------------- REGLAS DE TIEMPO / ESTADO ----------------
    private boolean esCancelablePorTiempo(Pedido p) {
        int prepMin = p.getArticulo().getTiempoPreparacionMin();
        long mins = Duration.between(p.getFechaHora(), LocalDateTime.now()).toMinutes();
        return mins <= prepMin;
    }

    private void actualizarEstadosEnvio() {
        List<Pedido> pedidos = empresa.getPedidos();
        for (Pedido p : pedidos) {
            if (p.getEstado() == EstadoEnvio.PENDIENTE && !esCancelablePorTiempo(p)) {
                // Si ya pasó el tiempo de preparación, lo consideramos ENVIADO
                p.actualizarEstado();
            }
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