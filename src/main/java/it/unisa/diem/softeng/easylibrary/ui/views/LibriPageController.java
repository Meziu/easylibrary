package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;


public class LibriPageController extends DataPageController<Libro, RicercaLibroController> {

    private Indicizzabile<ISBN, Libro> libri;
    
    public LibriPageController(VisualizzatoreHomePage hp, Indicizzabile<ISBN, Libro> libri){
        super(hp, new RicercaLibroController(), "Libri", "/res/RicercaLibro.fxml");
        
        this.libri = libri;
    }
    
    
    @Override
    protected void initializeColonne() {

        TableColumn<Libro, String> titoloCol = new TableColumn<>("Titolo");
        titoloCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTitolo())
        );

        TableColumn<Libro, String> autoriCol = new TableColumn<>("Autori");
        autoriCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getAutori()
                        .stream()
                        .map(a -> a.getAnagrafica().getNome() + " " + a.getAnagrafica().getCognome())
                        .collect(Collectors.joining(", ")))
        );
        
        TableColumn<Libro, String> annoCol = new TableColumn<>("Anno di pubblicazione");
        annoCol.setCellValueFactory(c ->
                new SimpleStringProperty(String.valueOf(c.getValue().getAnnoPubblicazione()))
        );

        TableColumn<Libro, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getISBN().getISBN())
        );

        TableColumn<Libro, String> copieCol = new TableColumn<>("Copie disponibili");
        copieCol.setCellValueFactory(c ->
                new SimpleStringProperty(String.valueOf(c.getValue().getCopieDisponibili()))
        );
        

        
        
        table.getColumns().addAll(titoloCol, autoriCol, annoCol, isbnCol, copieCol);

        
        // RENDIAMO LE COLONNE MODIFICABILI
        titoloCol.setEditable(true);
        autoriCol.setEditable(true);
        annoCol.setEditable(true);
        copieCol.setEditable(true);
        
        
        // Carica i libri
        setItems(libri.getLista());
    }
    
    @Override
    public void add(ActionEvent event) {
        
    }

    @Override
    public void remove(ActionEvent event) {
        
    }
}
