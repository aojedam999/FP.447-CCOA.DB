package CCOADB.FP.vista.main;

import CCOADB.FP.vista.layout.MainLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        MainLayout layout = new MainLayout();

        Scene scene = new Scene(layout, 1000, 650);

        stage.setTitle("Online Store");
        stage.setScene(scene);
        stage.show();

        scene.getStylesheets().add(
                getClass().getResource("/CCOADB/FP/vista/css/styles.css").toExternalForm()
        );
    }

    public static void main(String[] args) {
        launch();
    }
}