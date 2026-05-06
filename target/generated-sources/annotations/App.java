package CCOADB.FP.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/CCOADB/FP/vista/MainView.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 600);

        stage.setTitle("Online Store");
        stage.setScene(scene);
        stage.show();

        // CSS opcional
        var css = getClass().getResource("/CCOADB/FP/vista/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}