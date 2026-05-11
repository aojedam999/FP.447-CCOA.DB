package CCOADB.FP.vista.articulos;

import CCOADB.FP.controlador.Controlador;
import CCOADB.FP.modelo.Articulo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ArticulosView extends BorderPane {

    private final Controlador controlador;
    private final TableView<Articulo> tablaArticulos;

    private final TextField txtCodigo;
    private final TextField txtDescripcion;
    private final TextField txtPrecioVenta;
    private final TextField txtGastosEnvio;
    private final TextField txtTiempoPreparacion;
    private final TextField txtStock;

    public ArticulosView() {
        this.controlador = new Controlador();

        Label title = new Label("ARTÍCULOS");
        title.getStyleClass().add("label-title");

        txtCodigo = new TextField();
        txtDescripcion = new TextField();
        txtPrecioVenta = new TextField();
        txtGastosEnvio = new TextField();
        txtTiempoPreparacion = new TextField();
        txtStock = new TextField();

        tablaArticulos = crearTabla();

        Button btnGuardar = new Button("Añadir artículo");
        btnGuardar.setOnAction(e -> guardarArticulo());

        GridPane formulario = crearFormulario();
        formulario.add(btnGuardar, 1, 6);

        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        contenido.getChildren().addAll(formulario, tablaArticulos);

        setTop(title);
        setCenter(contenido);

        cargarArticulos();
    }

    private GridPane crearFormulario() {
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);

        formulario.add(new Label("Código:"), 0, 0);
        formulario.add(txtCodigo, 1, 0);

        formulario.add(new Label("Descripción:"), 0, 1);
        formulario.add(txtDescripcion, 1, 1);

        formulario.add(new Label("Precio venta:"), 0, 2);
        formulario.add(txtPrecioVenta, 1, 2);

        formulario.add(new Label("Gastos envío:"), 0, 3);
        formulario.add(txtGastosEnvio, 1, 3);

        formulario.add(new Label("Tiempo preparación (min):"), 0, 4);
        formulario.add(txtTiempoPreparacion, 1, 4);

        formulario.add(new Label("Stock disponible:"), 0, 5);
        formulario.add(txtStock, 1, 5);

        return formulario;
    }

    private TableView<Articulo> crearTabla() {
        TableView<Articulo> tabla = new TableView<>();

        TableColumn<Articulo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Articulo, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Articulo, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<Articulo, Double> colPrecio = new TableColumn<>("Precio venta");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        TableColumn<Articulo, Double> colGastos = new TableColumn<>("Gastos envío");
        colGastos.setCellValueFactory(new PropertyValueFactory<>("gastosEnvio"));

        TableColumn<Articulo, Integer> colTiempo = new TableColumn<>("Tiempo prep.");
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempoPreparacionMin"));

        TableColumn<Articulo, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockDisponible"));

        tabla.getColumns().addAll(
                colId,
                colCodigo,
                colDescripcion,
                colPrecio,
                colGastos,
                colTiempo,
                colStock
        );

        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tabla;
    }

    private void guardarArticulo() {
        try {
            validarCampos();

            String codigo = txtCodigo.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            double precioVenta = Double.parseDouble(txtPrecioVenta.getText().trim());
            double gastosEnvio = Double.parseDouble(txtGastosEnvio.getText().trim());
            int tiempoPreparacion = Integer.parseInt(txtTiempoPreparacion.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            Articulo articulo = new Articulo(
                    codigo,
                    descripcion,
                    precioVenta,
                    gastosEnvio,
                    tiempoPreparacion,
                    stock
            );

            controlador.addArticulo(articulo);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Artículo añadido correctamente");

            limpiarCampos();
            cargarArticulos();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Precio, gastos, tiempo y stock deben ser valores numéricos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al añadir artículo: " + e.getMessage());
        }
    }

    private void validarCampos() throws Exception {
        if (txtCodigo.getText().trim().isEmpty()
                || txtDescripcion.getText().trim().isEmpty()
                || txtPrecioVenta.getText().trim().isEmpty()
                || txtGastosEnvio.getText().trim().isEmpty()
                || txtTiempoPreparacion.getText().trim().isEmpty()
                || txtStock.getText().trim().isEmpty()) {
            throw new Exception("Todos los campos son obligatorios.");
        }
    }

    private void cargarArticulos() {
        ObservableList<Articulo> articulos =
                FXCollections.observableArrayList(controlador.getArticulos());

        tablaArticulos.setItems(articulos);
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtDescripcion.clear();
        txtPrecioVenta.clear();
        txtGastosEnvio.clear();
        txtTiempoPreparacion.clear();
        txtStock.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Artículos");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}