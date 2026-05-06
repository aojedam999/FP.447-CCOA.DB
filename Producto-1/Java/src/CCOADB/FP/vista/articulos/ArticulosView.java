package CCOADB.FP.vista.articulos;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ArticulosView extends BorderPane {

    public ArticulosView() {

        Label title = new Label("ARTÍCULOS");
        title.getStyleClass().add("label-title");

        Label placeholder = new Label("Pantalla de artículos en construcción");

        setTop(title);
        setCenter(placeholder);
    }
}