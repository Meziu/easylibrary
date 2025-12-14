package it.unisa.diem.softeng.easylibrary.ui.views.pages;

import it.unisa.diem.softeng.easylibrary.ui.views.add.UtenteAddController;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmailInvalidoException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import it.unisa.diem.softeng.easylibrary.ui.views.AlertGrande;
import it.unisa.diem.softeng.easylibrary.ui.views.pages.ricerca.RicercaUtenteController;
import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatorePagine;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;

public class UtentiPageController extends DataPageController<Utente, RicercaUtenteController, UtenteAddController> {

    private Indicizzabile<Matricola, Utente> utenti;
    private Indicizzabile<ISBN, Libro> libri;

    public UtentiPageController(VisualizzatorePagine vp, Indicizzabile<Matricola, Utente> utenti, Indicizzabile<ISBN, Libro> libri) {
        super(utenti, vp, new RicercaUtenteController(), "Utenti", "/res/RicercaUtente.fxml", new UtenteAddController(utenti));

        this.utenti = utenti;
        this.libri = libri;
    }

    @Override
    protected void initializeColonne() {

        TableColumn<Utente, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getAnagrafica().getNome())
        );

        TableColumn<Utente, String> cognomeCol = new TableColumn<>("Cognome");
        cognomeCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getAnagrafica().getCognome())
        );

        TableColumn<Utente, String> matrCol = new TableColumn<>("Matricola");
        matrCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getMatricola().getMatricola())
        );

        TableColumn<Utente, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getEmail().getIndirizzoEmail())
        );

        TableColumn<Utente, String> prestitiCol = new TableColumn<>("Libri in Prestito");
        prestitiCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getPrestitiAttivi()
                        .stream()
                        .map(a -> libri.ottieni(a.getISBN()))
                        .map(l -> l.toString())
                        .collect(Collectors.joining("\n"))
                )
        );

        table.getColumns().addAll(matrCol, nomeCol, cognomeCol, emailCol, prestitiCol);

        // RENDIAMO LE COLONNE MODIFICABILI
        nomeCol.setEditable(true);
        cognomeCol.setEditable(true);
        emailCol.setEditable(true);

        //colonna nome effettivamente modificabile
        nomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nomeCol.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();

            if (e.getNewValue().isEmpty()) {
                AlertGrande.mostraAlertErrore("Impossibile aggiungere un nome vuoto.");
            } else {
                utenti.modifica(u, utente -> {
                    utente.getAnagrafica().setNome(e.getNewValue());;
                });
            }

            setItems(utenti.getLista());
            table.refresh();
        });

        //colonna cognome effettivamente modificabile
        cognomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cognomeCol.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();

            if (e.getNewValue().isEmpty()) {
                AlertGrande.mostraAlertErrore("Impossibile aggiungere un cognome vuoto.");
            } else {
                utenti.modifica(u, utente -> {
                    utente.getAnagrafica().setCognome(e.getNewValue());;
                });
            }

            setItems(utenti.getLista());
            table.refresh();
        });

        //modifica l'email
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit((TableColumn.CellEditEvent<Utente, String> e) -> {
            Utente u = e.getRowValue();

            try {
                IndirizzoEmail nuovaEmail = new IndirizzoEmail(e.getNewValue());
                utenti.modifica(u, utente -> {
                    utente.setEmail(nuovaEmail);
                });
            } catch (IndirizzoEmailInvalidoException ex) {
                AlertGrande.mostraAlertErrore("Nuovo indirizzo email non valido");
            }
            setItems(utenti.getLista());
            table.refresh();
        });

        // Carica gli utenti
        setItems(utenti.getLista());
    }

    @Override
    protected void initializeFiltro() {
        this.table.itemsProperty().bind(Bindings.createObjectBinding(() -> {
            return FXCollections.observableList(utenti.filtra(ricercaController.new FiltroUtenti()));
        }, ricercaController.ricercaCognomeField.textProperty(), ricercaController.ricercaMatricolaField.textProperty()));
    }
}
