package it.unisa.diem.softeng.easylibrary.ui;

import it.unisa.diem.softeng.easylibrary.Biblioteca;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import it.unisa.diem.softeng.easylibrary.ui.views.HomePageController;
import it.unisa.diem.softeng.easylibrary.ui.views.LibriPageController;
import it.unisa.diem.softeng.easylibrary.ui.views.PrestitiPageController;
import it.unisa.diem.softeng.easylibrary.ui.views.UtentiPageController;
import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatorePagine;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;


public class AppController implements Initializable, VisualizzatorePagine {
    private Biblioteca biblioteca;
    private String fileArchivio;

    @FXML
    private BorderPane appContent;

    public AppController(String fileArchivio){
        caricaDaFile(fileArchivio);

        if (biblioteca == null) {
            biblioteca = new Biblioteca();
            salvaSuFile();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visualizzaHome();
    }

    @FXML
    private void salvaSuFile(ActionEvent event) {
        salvaSuFile();
    }

    // Interazione con l'opzione della menu bar.
    @FXML
    private void salvaConNome(ActionEvent event) {
        // Ottieni finestra dal contenuto dell'app.
        Window w = appContent.getScene().getWindow();
        FileChooser fc = new FileChooser();

        try {
            File f = fc.showSaveDialog(w);

            // Annullamento dell'operazione
            if (f == null) {
                return;
            }

            String filePath = f.getCanonicalPath();

            salvaConNome(filePath);
        } catch (IOException e) {
            String error = "Errore nella ricerca del file di salvataggio: " + e.toString();
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
        }
    }

    // Interazione con l'opzione della menu bar.
    @FXML
    private void caricaDaFile(ActionEvent event) {
        // Ottieni finestra dal contenuto dell'app.
        Window w = appContent.getScene().getWindow();
        FileChooser fc = new FileChooser();

        try {
            File f = fc.showOpenDialog(w);

            // Annullamento dell'operazione
            if (f == null) {
                return;
            }

            String filePath = f.getCanonicalPath();

            caricaDaFile(filePath);
        } catch (IOException e) {
            String error = "Errore nella ricerca del file di salvataggio: " + e.toString();
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
            return;
        }

        visualizzaHome();
    }

    @FXML
    private void visualizzaLibri(ActionEvent event) {
        visualizzaLibri();
    }

    @FXML
    private void visualizzaUtenti(ActionEvent event) {
        visualizzaUtenti();
    }

    @FXML
    private void visualizzaPrestiti(ActionEvent event) {
        visualizzaPrestiti();
    }

    public void salvaSuFile() {
        salvaConNome(this.fileArchivio);
    }

    public void salvaConNome(String fileArchivio) {
        this.fileArchivio = fileArchivio;

        try {
            biblioteca.salvaFile(this.fileArchivio);
        } catch (IOException e) {
            biblioteca = null;

            String error = "Errore nel salvataggio del file \"" + this.fileArchivio + "\" di archivio: " + e.toString();
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
        }
    }

    public void caricaDaFile(String fileArchivio) {
        this.fileArchivio = fileArchivio;

        try {
            biblioteca = Biblioteca.caricaFile(this.fileArchivio);
        } catch (IOException e) {
            biblioteca = null;

            String error = "Errore nel caricamento del file \"" + this.fileArchivio + "\" di archivio: " + e.toString();
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
        }
    }

    @Override
    public void visualizzaLibri() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/DataPageView.fxml"));
        loader.setController(new LibriPageController(this, biblioteca.getArchivioLibri()));

        try {
            appContent.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void visualizzaUtenti() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/DataPageView.fxml"));
        loader.setController(new UtentiPageController(this, biblioteca.getArchivioUtenti(), biblioteca.getArchivioLibri()));

        try {
            appContent.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void visualizzaPrestiti() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/DataPageView.fxml"));
        loader.setController(new PrestitiPageController(this, biblioteca.getArchivioPrestiti()));

        try {
            appContent.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void visualizzaHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/res/HomePageView.fxml"));
        loader.setController(new HomePageController(this));

        try {
            appContent.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
