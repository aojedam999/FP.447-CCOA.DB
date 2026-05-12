package CCOADB.FP.vista.clientes;

import CCOADB.FP.controlador.Controlador;
import CCOADB.FP.modelo.Cliente;
import CCOADB.FP.modelo.ClienteEstandar;
import CCOADB.FP.modelo.ClientePremium;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientesView extends BorderPane {

    private final Controlador controlador;
    private final TableView<Cliente> tablaClientes;

    private final TextField txtNombre;
    private final TextField txtEmail;
    private final TextField txtDomicilio;
    private final TextField txtNifNie;

    private final TextField txtCuota;
    private final TextField txtDescuento;

    private final ComboBox<String> comboTipo;

    private final Label lblCuota;
    private final Label lblDescuento;

    private final Button btnTodos;
    private final Button btnEstandar;
    private final Button btnPremium;

    public ClientesView() {

        controlador = new Controlador();

        Label title = new Label("CLIENTES");
        title.getStyleClass().add("label-title");

        txtNombre = new TextField();
        txtEmail = new TextField();
        txtDomicilio = new TextField();
        txtNifNie = new TextField();

        txtCuota = new TextField();
        txtDescuento = new TextField();

        txtNombre.setPromptText("Nombre del cliente");
        txtEmail.setPromptText("correo@email.com");
        txtDomicilio.setPromptText("Dirección");
        txtNifNie.setPromptText("12345678A");

        txtCuota.setPromptText("Ej: 50");
        txtDescuento.setPromptText("Ej: 15");

        lblCuota = new Label("Cuota anual:");
        lblDescuento = new Label("Descuento envío:");

        comboTipo = new ComboBox<>();

        comboTipo.getItems().addAll(
                "Estándar",
                "Premium"
        );

        comboTipo.setValue("Estándar");

        comboTipo.setPrefWidth(220);

        comboTipo.setOnAction(e -> actualizarCamposPremium());

        tablaClientes = crearTabla();

        btnTodos = new Button("Todos");
        btnEstandar = new Button("Estándar");
        btnPremium = new Button("Premium");

        btnTodos.setPrefWidth(140);
        btnEstandar.setPrefWidth(140);
        btnPremium.setPrefWidth(140);

        btnTodos.setOnAction(e -> cargarClientes());

        btnEstandar.setOnAction(e -> cargarClientesEstandar());

        btnPremium.setOnAction(e -> cargarClientesPremium());

        Button btnGuardar = new Button("Añadir cliente");

        btnGuardar.setPrefWidth(220);

        btnGuardar.setOnAction(e -> guardarCliente());

        GridPane formulario = crearFormulario();

        formulario.add(btnGuardar, 1, 7);

        HBox filtros = new HBox(15);

        filtros.setAlignment(Pos.CENTER_LEFT);

        filtros.getChildren().addAll(
                btnTodos,
                btnEstandar,
                btnPremium
        );

        VBox contenido = new VBox(25);

        contenido.setPadding(new Insets(30));

        contenido.getChildren().addAll(
                formulario,
                filtros,
                tablaClientes
        );

        setPadding(new Insets(15));

        setTop(title);
        setCenter(contenido);

        actualizarCamposPremium();

        cargarClientes();
    }

    private GridPane crearFormulario() {

        GridPane formulario = new GridPane();

        formulario.setHgap(15);
        formulario.setVgap(15);

        formulario.add(new Label("Tipo cliente:"), 0, 0);
        formulario.add(comboTipo, 1, 0);

        formulario.add(new Label("Nombre:"), 0, 1);
        formulario.add(txtNombre, 1, 1);

        formulario.add(new Label("Email:"), 0, 2);
        formulario.add(txtEmail, 1, 2);

        formulario.add(new Label("Domicilio:"), 0, 3);
        formulario.add(txtDomicilio, 1, 3);

        formulario.add(new Label("NIF/NIE:"), 0, 4);
        formulario.add(txtNifNie, 1, 4);

        formulario.add(lblCuota, 0, 5);
        formulario.add(txtCuota, 1, 5);

        formulario.add(lblDescuento, 0, 6);
        formulario.add(txtDescuento, 1, 6);

        return formulario;
    }

    private TableView<Cliente> crearTabla() {

        TableView<Cliente> tabla = new TableView<>();

        TableColumn<Cliente, Integer> colId =
                new TableColumn<>("ID");

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colId.setMaxWidth(70);

        TableColumn<Cliente, String> colNombre =
                new TableColumn<>("Nombre");

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre"));

        TableColumn<Cliente, String> colEmail =
                new TableColumn<>("Email");

        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        TableColumn<Cliente, String> colDomicilio =
                new TableColumn<>("Domicilio");

        colDomicilio.setCellValueFactory(
                new PropertyValueFactory<>("domicilio"));

        TableColumn<Cliente, String> colNif =
                new TableColumn<>("NIF/NIE");

        colNif.setCellValueFactory(
                new PropertyValueFactory<>("NIFNIE"));

        TableColumn<Cliente, String> colTipo =
                new TableColumn<>("Tipo");

        colTipo.setCellValueFactory(cellData -> {

            Cliente cliente = cellData.getValue();

            if (cliente instanceof ClientePremium) {
                return new SimpleStringProperty("Premium");
            }

            return new SimpleStringProperty("Estándar");
        });

        TableColumn<Cliente, String> colCuota =
                new TableColumn<>("Cuota");

        colCuota.setCellValueFactory(cellData -> {

            Cliente cliente = cellData.getValue();

            if (cliente instanceof ClientePremium premium) {

                return new SimpleStringProperty(
                        premium.getCuotaAnual() + " €");
            }

            return new SimpleStringProperty("-");
        });

        TableColumn<Cliente, String> colDescuento =
                new TableColumn<>("Descuento");

        colDescuento.setCellValueFactory(cellData -> {

            Cliente cliente = cellData.getValue();

            if (cliente instanceof ClientePremium premium) {

                return new SimpleStringProperty(
                        premium.getDescuentoEnvio() + "%");
            }

            return new SimpleStringProperty("-");
        });

        tabla.getColumns().addAll(
                colId,
                colNombre,
                colEmail,
                colDomicilio,
                colNif,
                colTipo,
                colCuota,
                colDescuento
        );

        tabla.setPrefHeight(400);

        tabla.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY);

        return tabla;
    }

    private void guardarCliente() {

        try {

            validarCampos();

            Cliente cliente;

            if (comboTipo.getValue().equals("Premium")) {

                double cuota =
                        Double.parseDouble(
                                txtCuota.getText().trim());

                int descuento =
                        Integer.parseInt(
                                txtDescuento.getText().trim());

                cliente = new ClientePremium(
                        txtEmail.getText().trim(),
                        txtNombre.getText().trim(),
                        txtDomicilio.getText().trim(),
                        txtNifNie.getText().trim(),
                        cuota,
                        descuento
                );

            } else {

                cliente = new ClienteEstandar(
                        txtEmail.getText().trim(),
                        txtNombre.getText().trim(),
                        txtDomicilio.getText().trim(),
                        txtNifNie.getText().trim()
                );
            }

            controlador.addCliente(cliente);

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Cliente añadido correctamente"
            );

            limpiarCampos();

            cargarClientes();

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Cuota y descuento deben ser numéricos."
            );

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    e.getMessage()
            );
        }
    }

    private void validarCampos() throws Exception {

        if (txtNombre.getText().trim().isEmpty()
                || txtEmail.getText().trim().isEmpty()
                || txtDomicilio.getText().trim().isEmpty()
                || txtNifNie.getText().trim().isEmpty()) {

            throw new Exception("Todos los campos son obligatorios.");
        }

        String email = txtEmail.getText().trim();

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

            throw new Exception(
                    "Formato de email inválido.");
        }

        String nif = txtNifNie.getText().trim();

        if (nif.length() < 8) {

            throw new Exception(
                    "NIF/NIE inválido.");
        }

        if (comboTipo.getValue().equals("Premium")) {

            if (txtCuota.getText().trim().isEmpty()
                    || txtDescuento.getText().trim().isEmpty()) {

                throw new Exception(
                        "Los campos premium son obligatorios.");
            }

            double cuota =
                    Double.parseDouble(
                            txtCuota.getText().trim());

            int descuento =
                    Integer.parseInt(
                            txtDescuento.getText().trim());

            if (cuota <= 0) {

                throw new Exception(
                        "La cuota anual debe ser mayor que 0.");
            }

            if (descuento < 0 || descuento > 100) {

                throw new Exception(
                        "El descuento debe estar entre 0 y 100.");
            }
        }
    }

    private void actualizarCamposPremium() {

        boolean premium =
                comboTipo.getValue().equals("Premium");

        lblCuota.setVisible(premium);
        txtCuota.setVisible(premium);

        lblDescuento.setVisible(premium);
        txtDescuento.setVisible(premium);
    }

    private void cargarClientes() {

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList(
                        controlador.getClientes()
                );

        tablaClientes.setItems(clientes);
    }

    private void cargarClientesEstandar() {

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList();

        for (Cliente c : controlador.getClientes()) {

            if (c instanceof ClienteEstandar) {
                clientes.add(c);
            }
        }

        tablaClientes.setItems(clientes);
    }

    private void cargarClientesPremium() {

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList();

        for (Cliente c : controlador.getClientes()) {

            if (c instanceof ClientePremium) {
                clientes.add(c);
            }
        }

        tablaClientes.setItems(clientes);
    }

    private void limpiarCampos() {

        txtNombre.clear();
        txtEmail.clear();
        txtDomicilio.clear();
        txtNifNie.clear();

        txtCuota.clear();
        txtDescuento.clear();

        comboTipo.setValue("Estándar");

        actualizarCamposPremium();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle("Clientes");

        alerta.setHeaderText(null);

        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}