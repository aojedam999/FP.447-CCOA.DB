package CCOADB.FP.vista.clientes;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ClientesView extends BorderPane {

    public ClientesView() {

        Label title = new Label("CLIENTES");
        title.getStyleClass().add("label-title");

        Label placeholder = new Label("Pantalla de clientes en construcción");

        setTop(title);
        setCenter(placeholder);
    }
}