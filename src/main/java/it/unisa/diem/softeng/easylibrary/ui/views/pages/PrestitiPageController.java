package it.unisa.diem.softeng.easylibrary.ui.views.pages;

import it.unisa.diem.softeng.easylibrary.ui.views.add.PrestitoAddController;
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

    @SuppressWarnings("unchecked") // lint per operazioni di casting sulle colonne
    @Override
    protected void initializeColonne() {

        TableColumn<Prestito, String> utenteCol = new TableColumn<>("Utente");
        utenteCol.setCellValueFactory(c -> {
            Utente u = utenti.ottieni(c.getValue().getMatricola());

            // Mostra nome, cognome e codice della matricola
            if (u != null) {
                return new SimpleStringProperty(u.toString());
            } else { // Se l'utente non è presente, mostra solo la matricola
                return new SimpleStringProperty(c.getValue().getMatricola().getMatricola());
            }
        }
        );

        TableColumn<Prestito, String> libroCol = new TableColumn<>("Libro");
        libroCol.setCellValueFactory(c -> {
            Libro l = libri.ottieni(c.getValue().getISBN());

            // Mostra titolo e codice del libro
            if (l != null) {
                return new SimpleStringProperty(l.toString());
            } else { // Se il libro non è presente, mostra solo l'ISBN
                return new SimpleStringProperty(c.getValue().getISBN().getISBN());
            }
        }
        );

        TableColumn<Prestito, String> statoCol = new TableColumn<>("Stato prestito");
        statoCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getStato().toString())
        );

        TableColumn<Prestito, LocalDate> scadenzaCol = new TableColumn<>("Data di scadenza");
        scadenzaCol.setCellValueFactory(c
                -> new SimpleObjectProperty<LocalDate>(c.getValue().getDataDiScadenza())
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

                setItems(prestiti.getLista());
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
