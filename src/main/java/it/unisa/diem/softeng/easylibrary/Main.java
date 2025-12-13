package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
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
        // Avvio App
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/AppView.fxml"));
        AppController app = new AppController("biblioteca.bin");
        loader.setController(app);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        
        // Salvataggio automatico alla chiusura della finestra
        stage.setOnCloseRequest(event -> {
            app.salvaSuFile();
        });

        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
        stage.setTitle("EasyLibrary");
        stage.show();
    }
}
