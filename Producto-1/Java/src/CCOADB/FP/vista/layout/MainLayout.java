package CCOADB.FP.vista.layout;

import CCOADB.FP.vista.articulos.ArticulosView;
import CCOADB.FP.vista.clientes.ClientesView;
import CCOADB.FP.vista.pedidos.PedidosView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainLayout extends BorderPane {

    public MainLayout() {

        Label title = new Label("ONLINE STORE");
        setTop(title);

        HBox menu = new HBox(10);

        Button btnArticulos = new Button("Artículos");
        Button btnClientes = new Button("Clientes");
        Button btnPedidos = new Button("Pedidos");

        menu.getChildren().addAll(btnArticulos, btnClientes, btnPedidos);

        setTop(menu);

        setCenter(new Label("Bienvenido a Online Store"));

        btnArticulos.setOnAction(e ->
                setCenter(new ArticulosView())
        );

        btnClientes.setOnAction(e ->
                setCenter(new ClientesView())
        );

        btnPedidos.setOnAction(e ->
                setCenter(new PedidosView())
        );
    }
}