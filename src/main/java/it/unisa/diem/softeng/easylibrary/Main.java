package it.unisa.diem.softeng.easylibrary;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Main extends Application {
    public static void main(String[] args) {
        // Lancia il runtime di JavaFX
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label lbl = new Label("Ciao Mondo!");
        VBox root = new VBox();

        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lbl);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("EasyLibrary");
        stage.show();
    }
}
