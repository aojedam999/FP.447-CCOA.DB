package CCOADB.FP.vista.controller;

import CCOADB.FP.controlador.Controlador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class MainController {

    @FXML
    private StackPane contenido;
    private Controlador controlador = new Controlador();

    private void cargarVista(String fxml) {
        try {
            Node vista = FXMLLoader.load(getClass().getResource("/CCOADB/FP/vista/views/" + fxml));
            contenido.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irArticulos() {
        cargarVista("ArticulosView.fxml");
    }

    @FXML
    public void irClientes() {
        cargarVista("ClientesView.fxml");
    }

    @FXML
    public void irPedidos() {
        cargarVista("PedidosView.fxml");
    }
}