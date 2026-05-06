package CCOADB.FP.vista.pedidos;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PedidosView extends BorderPane {

    public PedidosView() {

        Label title = new Label("PEDIDOS");
        title.getStyleClass().add("label-title");

        Label placeholder = new Label("Pantalla de pedidos en construcción");

        setTop(title);
        setCenter(placeholder);
    }
}