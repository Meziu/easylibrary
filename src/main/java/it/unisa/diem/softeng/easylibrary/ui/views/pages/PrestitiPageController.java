package it.unisa.diem.softeng.easylibrary.ui.views.pages;

import it.unisa.diem.softeng.easylibrary.ui.views.dataadd.PrestitoAddController;
import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import it.unisa.diem.softeng.easylibrary.ui.views.AlertGrande;
import it.unisa.diem.softeng.easylibrary.ui.views.pages.ricerca.RicercaPrestitoController;
import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatorePagine;
import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

        TableColumn<Prestito, String> utenteCol = new TableColumn<>("Utente");
        utenteCol.setCellValueFactory(c ->
                new SimpleStringProperty(utenti.ottieni(c.getValue().getMatricola()).toString())
        );

        TableColumn<Prestito, String> libroCol = new TableColumn<>("Libro");
        libroCol.setCellValueFactory(c ->
                new SimpleStringProperty(libri.ottieni(c.getValue().getISBN()).toString())
        );

        TableColumn<Prestito, String> statoCol = new TableColumn<>("Stato prestito");
        statoCol.setCellValueFactory(c ->
                new SimpleStringProperty( c.getValue().getStato().toString())
        );

        TableColumn<Prestito, LocalDate> scadenzaCol = new TableColumn<>("Data di scadenza");
        scadenzaCol.setCellValueFactory(c ->
                new SimpleObjectProperty<LocalDate>(c.getValue().getDataDiScadenza())
        );
        scadenzaCol.setCellFactory(cl -> new DatePickerCell());
        scadenzaCol.setOnEditCommit(e -> {
            if (e.getNewValue().isBefore(LocalDate.now())) {
                // Refresh tabella per evitare sfarfallio
                table.refresh();
                AlertGrande.mostraAlertErrore("Data inserita precedente a quella attuale.");
            } else {
                prestiti.modifica(e.getRowValue(), p -> {
                    p.setDataDiScadenza(e.getNewValue());
                });
            }
        });


        table.getColumns().addAll(utenteCol, libroCol, statoCol, scadenzaCol);


        // RENDIAMO LE COLONNE MODIFICABILI
        utenteCol.setEditable(true);
        libroCol.setEditable(true);
        statoCol.setEditable(true);
        scadenzaCol.setEditable(true);


        // Carica i libri
        setItems(prestiti.getLista());
    }
    
    @Override
    protected void initializeFiltro() {
        this.table.itemsProperty().bind(Bindings.createObjectBinding(() -> {
            return FXCollections.observableList(prestiti.filtra(p -> {
                if (ricercaController.mostraPrestitiAttivi.isSelected()) {
                    return p.getStato() == StatoPrestito.ATTIVO;
                } else {
                    return true;
                }
            }));
        }, ricercaController.mostraPrestitiAttivi.selectedProperty()));
    }
}
