package CCOADB.FP.vista.util;

import javafx.scene.layout.BorderPane;

public class SceneManager {

    private static BorderPane root;

    public static void setRoot(BorderPane pane) {
        root = pane;
    }

    public static void setView(javafx.scene.Node node) {
        root.setCenter(node);
    }
}