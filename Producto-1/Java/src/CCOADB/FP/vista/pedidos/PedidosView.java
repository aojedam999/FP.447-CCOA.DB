package CCOADB.FP.vista.pedidos;

import CCOADB.FP.controlador.Controlador;
import CCOADB.FP.modelo.Articulo;
import CCOADB.FP.modelo.Cliente;
import CCOADB.FP.modelo.EstadoEnvio;
import CCOADB.FP.modelo.Pedido;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PedidosView extends BorderPane {

    // MVC
    private final Controlador controlador;

    // Crear pedido
    private final ComboBox<Cliente> comboCliente;
    private final ComboBox<Articulo> comboArticulo;
    private final TextField txtUnidades;
    private final Button btnCrearPedido;

    // Filtros + tabla
    private final ComboBox<Cliente> comboFiltroCliente; // null = "Todos"
    private final RadioButton rbPendientes;
    private final RadioButton rbEnviados;
    private final Button btnRefrescar;
    private final TableView<Pedido> tablaPedidos;
    private final Button btnEliminarPedido;

    private final DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PedidosView() {

        this.controlador = new Controlador();

        Label title = new Label("PEDIDOS");
        title.getStyleClass().add("label-title");

        // --- Crear pedido ---
        comboCliente = new ComboBox<>();
        comboArticulo = new ComboBox<>();
        txtUnidades = new TextField();
        txtUnidades.setPromptText("Unidades");

        btnCrearPedido = new Button("Crear pedido");

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);

        form.add(new Label("Cliente:"), 0, 0);
        form.add(comboCliente, 1, 0);

        form.add(new Label("Artículo:"), 0, 1);
        form.add(comboArticulo, 1, 1);

        form.add(new Label("Unidades:"), 0, 2);
        form.add(txtUnidades, 1, 2);

        form.add(btnCrearPedido, 1, 3);

        // --- Filtros ---
        comboFiltroCliente = new ComboBox<>();
        comboFiltroCliente.setPrefWidth(260);

        ToggleGroup tg = new ToggleGroup();
        rbPendientes = new RadioButton("Pendientes");
        rbEnviados = new RadioButton("Enviados");
        rbPendientes.setToggleGroup(tg);
        rbEnviados.setToggleGroup(tg);
        rbPendientes.setSelected(true);

        btnRefrescar = new Button("Refrescar");

        HBox filtros = new HBox(
                15,
                rbPendientes,
                rbEnviados,
                new Label("Filtrar cliente:"),
                comboFiltroCliente,
                btnRefrescar
        );
        filtros.setAlignment(Pos.CENTER_LEFT);

        // --- Tabla ---
        tablaPedidos = new TableView<>();
        tablaPedidos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaPedidos.setPrefHeight(380);

        TableColumn<Pedido, Number> colId = new TableColumn<>("ID");
        colId.setMaxWidth(80);
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getNumeroPedido()));

        TableColumn<Pedido, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getCliente() != null ? c.getValue().getCliente().getEmail() : ""
                )
        );

        TableColumn<Pedido, String> colArticulo = new TableColumn<>("Artículo");
        colArticulo.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getArticulo() != null ? c.getValue().getArticulo().getCodigo() : ""
                )
        );

        TableColumn<Pedido, Number> colUnidades = new TableColumn<>("Unidades");
        colUnidades.setMaxWidth(90);
        colUnidades.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getUnidades()));

        TableColumn<Pedido, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getFechaHora() != null ? c.getValue().getFechaHora().format(fmtFecha) : ""
                )
        );

        TableColumn<Pedido, String> colEstado = new TableColumn<>("Estado");
        colEstado.setMaxWidth(110);
        colEstado.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getEstado() != null ? c.getValue().getEstado().name() : ""
                )
        );

        TableColumn<Pedido, String> colTotal = new TableColumn<>("Total");
        colTotal.setMaxWidth(120);
        colTotal.setCellValueFactory(c ->
                new SimpleStringProperty(
                        String.format("%.2f €", c.getValue().calcularTotal()).replace(".", ",")
                )
        );

        tablaPedidos.getColumns().addAll(colId, colCliente, colArticulo, colUnidades, colFecha, colEstado, colTotal);

        // --- Botón eliminar ---
        btnEliminarPedido = new Button("Eliminar pedido seleccionado");
        btnEliminarPedido.setDisable(true); // ✅ por defecto desactivado

        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(25));
        contenido.getChildren().addAll(form, filtros, tablaPedidos, btnEliminarPedido);

        setPadding(new Insets(15));
        setTop(title);
        setCenter(contenido);

        // Configuración de combos (cómo se muestran)
        configurarCombos();

        // Cargar datos reales
        cargarDatosIniciales();

        // Eventos filtros/refresco
        configurarEventosFiltros();

        // Evento crear pedido (Paso 3)
        configurarEventoCrearPedido();

        // Evento eliminar pedido (Paso 4)
        configurarEventoEliminarPedido();

        // Activar/desactivar botón eliminar según selección
        configurarHabilitacionBotonEliminar();
    }

    private void configurarCombos() {

        // Mostrar cliente en combos: "email - nombre"
        StringConverter<Cliente> convCliente = new StringConverter<>() {
            @Override
            public String toString(Cliente c) {
                if (c == null) return "Todos";
                String nombre = (c.getNombre() == null) ? "" : c.getNombre();
                return c.getEmail() + (nombre.isBlank() ? "" : " - " + nombre);
            }

            @Override
            public Cliente fromString(String string) { return null; }
        };

        comboCliente.setConverter(convCliente);
        comboFiltroCliente.setConverter(convCliente);

        // Mostrar artículo en combos: "codigo - descripcion"
        StringConverter<Articulo> convArticulo = new StringConverter<>() {
            @Override
            public String toString(Articulo a) {
                if (a == null) return "";
                String desc = (a.getDescripcion() == null) ? "" : a.getDescripcion();
                return a.getCodigo() + (desc.isBlank() ? "" : " - " + desc);
            }

            @Override
            public Articulo fromString(String string) { return null; }
        };

        comboArticulo.setConverter(convArticulo);
    }

    private void cargarDatosIniciales() {

        List<Cliente> clientes = controlador.getClientes();
        List<Articulo> articulos = controlador.getArticulos();

        //Para CREAR pedidos: solo clientes reales
        comboCliente.setItems(FXCollections.observableArrayList(clientes));
        comboArticulo.setItems(FXCollections.observableArrayList(articulos));

        //Para FILTRAR: añadimos "Todos" como null
        ObservableList<Cliente> filtro = FXCollections.observableArrayList();
        filtro.add(null);
        filtro.addAll(clientes);
        comboFiltroCliente.setItems(filtro);
        comboFiltroCliente.getSelectionModel().selectFirst();

        refrescarTabla();
    }

    private void configurarEventosFiltros() {
        btnRefrescar.setOnAction(e -> refrescarTabla());
        rbPendientes.setOnAction(e -> refrescarTabla());
        rbEnviados.setOnAction(e -> refrescarTabla());
        comboFiltroCliente.setOnAction(e -> refrescarTabla());
    }

    private void refrescarTabla() {

        // Actualiza estados de envío según regla de tiempo
        controlador.actualizarEstadosEnvio();

        EstadoEnvio estado = rbPendientes.isSelected() ? EstadoEnvio.PENDIENTE : EstadoEnvio.ENVIADO;

        Cliente filtroCliente = comboFiltroCliente.getValue(); // null = todos
        List<Pedido> pedidos = controlador.getPedidos();

        List<Pedido> filtrados = pedidos.stream()
                .filter(p -> p.getEstado() == estado)
                .filter(p -> filtroCliente == null || p.getCliente().equals(filtroCliente))
                .collect(Collectors.toList());

        tablaPedidos.setItems(FXCollections.observableArrayList(filtrados));

        actualizarEstadoBotonEliminar(tablaPedidos.getSelectionModel().getSelectedItem());
    }

    private void configurarEventoCrearPedido() {

        btnCrearPedido.setOnAction(e -> {

            Cliente cliente = comboCliente.getValue();
            Articulo articulo = comboArticulo.getValue();

            if (cliente == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Falta cliente", "Selecciona un cliente.");
                return;
            }
            if (articulo == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Falta artículo", "Selecciona un artículo.");
                return;
            }

            int unidades;
            try {
                unidades = Integer.parseInt(txtUnidades.getText().trim());
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.WARNING, "Unidades inválidas", "Introduce un número entero (1, 2, 3...).");
                return;
            }

            if (unidades <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Unidades inválidas", "Las unidades deben ser mayores que 0.");
                return;
            }

            Pedido nuevo = new Pedido(0, LocalDateTime.now(), unidades, cliente, articulo);

            try {
                controlador.addPedido(nuevo);

                txtUnidades.clear();

                // recargar artículos por si cambia stock
                comboArticulo.setItems(FXCollections.observableArrayList(controlador.getArticulos()));

                refrescarTabla();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Pedido creado", "El pedido se ha creado correctamente.");

            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al crear pedido", ex.getMessage());
            }
        });
    }

    private void configurarEventoEliminarPedido() {

        btnEliminarPedido.setOnAction(e -> {

            Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Selecciona un pedido de la tabla.");
                return;
            }

            if (!seleccionado.puedeEliminarse()) {
                String motivo = motivoNoEliminable(seleccionado);
                mostrarAlerta(Alert.AlertType.ERROR, "No se puede eliminar", motivo);
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Pedidos");
            confirm.setHeaderText("Confirmar eliminación");
            confirm.setContentText("¿Seguro que quieres eliminar el pedido ID " + seleccionado.getNumeroPedido() + "?");

            if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                return;
            }

            try {

                controlador.borrarPedido(seleccionado);

                refrescarTabla();
                tablaPedidos.getSelectionModel().clearSelection();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Pedido eliminado", "El pedido se ha eliminado correctamente.");

            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "No se puede eliminar", ex.getMessage());
            }
        });
    }

    //Activar / desactivar botón eliminar según selección
    private void configurarHabilitacionBotonEliminar() {
        tablaPedidos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            actualizarEstadoBotonEliminar(newSel);
        });
    }

    private void actualizarEstadoBotonEliminar(Pedido seleccionado) {
        if (seleccionado == null) {
            btnEliminarPedido.setDisable(true);
            return;
        }
        btnEliminarPedido.setDisable(!seleccionado.puedeEliminarse());
    }

    //Motivo detallado cuando no se puede eliminar
    private String motivoNoEliminable(Pedido p) {

        if (p.getEstado() != EstadoEnvio.PENDIENTE) {
            return "El pedido ya está ENVIADO, por lo que no se puede cancelar.";
        }

        if (p.getArticulo() == null || p.getFechaHora() == null) {
            return "El pedido no tiene datos suficientes para comprobar la cancelación.";
        }

        int prepMin = p.getArticulo().getTiempoPreparacionMin();
        long minsPasados = Duration.between(p.getFechaHora(), LocalDateTime.now()).toMinutes();

        if (minsPasados > prepMin) {
            return "Ya ha pasado el tiempo de preparación.\n\n" +
                    "Minutos transcurridos: " + minsPasados + " min\n" +
                    "Tiempo de preparación del artículo: " + prepMin + " min\n\n" +
                    "Por eso el pedido ya no es cancelable.";
        }

        // Si llega aquí pero aún así puedeEliminarse dio false por alguna razón
        return "No se puede eliminar este pedido según la regla del sistema.";
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Pedidos");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}