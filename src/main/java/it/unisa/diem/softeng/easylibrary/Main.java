package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.ui.AppController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {

    Biblioteca b;

    public static void main(String[] args) {
        // Lancia il runtime di JavaFX
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        b = new Biblioteca();
        
        // Avvio App
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/AppView.fxml"));
        loader.setController(new AppController("biblioteca.bin"));
        
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("EasyLibrary");
        stage.show();
    }
}
