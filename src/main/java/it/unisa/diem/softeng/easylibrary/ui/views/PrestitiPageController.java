package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;


public class PrestitiPageController extends DataPageController<Prestito, RicercaPrestitoController, PrestitoAddController> {
    private Archiviabile<Prestito> prestiti;
    private Indicizzabile<Matricola, Utente> utenti;
    private Indicizzabile<ISBN, Libro> libri;

    public PrestitiPageController(VisualizzatorePagine vp, Archiviabile<Prestito> prestiti, Indicizzabile<Matricola, Utente> utenti, Indicizzabile<ISBN, Libro> libri) {
        super(prestiti, vp, new RicercaPrestitoController(), "Prestiti", "/res/RicercaPrestiti.fxml", new PrestitoAddController(prestiti, utenti, libri));
        
        this.prestiti = prestiti;
        this.utenti = utenti;
        this.libri = libri;
    }

    @Override
    protected void initializeColonne() {

        TableColumn<Prestito, String> utenteCol = new TableColumn<>("Matricola utente");
        utenteCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getMatricola().getMatricola())
        );

        TableColumn<Prestito, String> libroCol = new TableColumn<>("ISBN");
        libroCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getISBN().getISBN())
        );

        TableColumn<Prestito, String> statoCol = new TableColumn<>("Stato prestito");
        statoCol.setCellValueFactory(c ->
                new SimpleStringProperty( c.getValue().getStato().toString())
        );

        TableColumn<Prestito, String> scadenzaCol = new TableColumn<>("Scadenza");
        scadenzaCol.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDataDiScadenza().toString())
        );


        table.getColumns().addAll(utenteCol, libroCol, statoCol, scadenzaCol);


        // RENDIAMO LE COLONNE MODIFICABILI
        utenteCol.setEditable(true);
        libroCol.setEditable(true);
        statoCol.setEditable(true);
        scadenzaCol.setEditable(true);


        // Carica i libri
        setItems(prestiti.getLista());
    }
}
