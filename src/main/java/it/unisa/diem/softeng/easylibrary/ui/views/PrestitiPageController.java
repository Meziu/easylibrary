package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;


public class PrestitiPageController extends DataPageController<Prestito, RicercaPrestitoController, PrestitoAddController> {
    private Archiviabile<Prestito> prestiti;

    public PrestitiPageController(VisualizzatorePagine vp, Archiviabile<Prestito> prestiti) {
        super(vp, new RicercaPrestitoController(), "Prestiti", "/res/RicercaPrestiti.fxml", new PrestitoAddController(prestiti));
        this.prestiti = prestiti;
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

    @Override
    public void remove(ActionEvent event) {

    }
}
