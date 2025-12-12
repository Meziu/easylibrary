package it.unisa.diem.softeng.easylibrary.ui;

import it.unisa.diem.softeng.easylibrary.Biblioteca;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import it.unisa.diem.softeng.easylibrary.ui.views.HomePageController;
import it.unisa.diem.softeng.easylibrary.ui.views.LibriPageController;
import it.unisa.diem.softeng.easylibrary.ui.views.PrestitiPageController;
import it.unisa.diem.softeng.easylibrary.ui.views.UtentiPageController;
import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatoreHomePage;
import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatorePagine;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;


public class AppController implements Initializable, VisualizzatorePagine, VisualizzatoreHomePage {
    private Biblioteca biblioteca;
    
    @FXML
    private BorderPane appContent;
    
    public AppController(String biblioFile){
        biblioteca = Biblioteca.caricaFile(biblioFile);
        
        if (biblioteca == null){
            biblioteca = new Biblioteca();
            biblioteca.salvaFile(biblioFile);
        }
        
        biblioteca.getArchivioUtenti().registra(new Utente("Andrea", "Ciliberti", new Matricola("0612709671"), new IndirizzoEmail("bro@studenti.unisa.it")));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        visualizzaHome();
    }    

    @FXML
    private void salvaSuFile(ActionEvent event) {
    }

    @FXML
    private void salvaConNome(ActionEvent event) {
    }

    @FXML
    private void caricaDaFile(ActionEvent event) {
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
