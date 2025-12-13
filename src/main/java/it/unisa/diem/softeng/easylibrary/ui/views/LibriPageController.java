package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.ui.views.RicercaLibroController.FiltroLibri;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class LibriPageController extends DataPageController<Libro, RicercaLibroController, LibroAddController> {

    private Indicizzabile<ISBN, Libro> libri;


    public LibriPageController(VisualizzatorePagine vp, Indicizzabile<ISBN, Libro> libri){
        super(libri, vp, new RicercaLibroController(), "Libri", "/res/RicercaLibro.fxml", new LibroAddController(libri));

        this.libri = libri;
    }

    @Override
    protected void initializeColonne() {

        TableColumn<Libro, String> titoloCol = new TableColumn<>("Titolo");
        titoloCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getTitolo())
        );

        TableColumn<Libro, String> autoriCol = new TableColumn<>("Autori");
        autoriCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getAutori()
                        .stream()
                        .map(a -> a.getAnagrafica().getNome() + " " + a.getAnagrafica().getCognome())
                        .collect(Collectors.joining(", ")))
        );

        TableColumn<Libro, String> annoCol = new TableColumn<>("Anno di pubblicazione");
        annoCol.setCellValueFactory(c
                -> new SimpleStringProperty(String.valueOf(c.getValue().getAnnoPubblicazione()))
        );

        TableColumn<Libro, String> isbnCol = new TableColumn<>("ISBN");
        isbnCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getISBN().getISBN())
        );

        TableColumn<Libro, Integer> copieCol = new TableColumn<>("Copie disponibili");
        copieCol.setCellValueFactory(c
                -> new SimpleObjectProperty<Integer>(c.getValue().getCopieDisponibili())
        );

        table.getColumns().addAll(isbnCol, titoloCol, autoriCol, annoCol, copieCol);

        // RENDIAMO LE COLONNE MODIFICABILI
        titoloCol.setEditable(true);
        autoriCol.setEditable(true);
        annoCol.setEditable(true);
        copieCol.setEditable(true);

        titoloCol.setCellFactory(TextFieldTableCell.forTableColumn());
        titoloCol.setOnEditCommit((TableColumn.CellEditEvent<Libro, String> e) -> {
            Libro l = e.getRowValue();

            libri.modifica(l, (libroTrovatoNellArchivio) -> { libroTrovatoNellArchivio.setTitolo(e.getNewValue()); });
        });

        // autori
        annoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        annoCol.setOnEditCommit((TableColumn.CellEditEvent<Libro, String> e) -> {
            Libro l = e.getRowValue();

            libri.modifica(l, (libro) -> { libro.setAnnoPubblicazione(Integer.parseInt(e.getNewValue())); });
        });

        copieCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        copieCol.setOnEditCommit((TableColumn.CellEditEvent<Libro, Integer> e) -> {
            Libro l = e.getRowValue();

            try {
                int newValue = e.getNewValue();

                if (newValue < 0) {
                    throw new NumberFormatException();
                }

                libri.modifica(l, (libro) -> { libro.setCopieDisponibili(newValue); });

            } catch (NumberFormatException ex) {
                libri.modifica(l, (libro) -> { libro.setCopieDisponibili(e.getOldValue()); });

                new Alert(Alert.AlertType.ERROR, "Il numero di copie deve essere un intero positivo o zero.").show();
            }
        });
        

        /*rc.ricercaTitoloField.textProperty().addListener((obs, oldV, newV) -> filtro());
        rc.ricercaAutoreField.textProperty().addListener((obs, oldV, newV) -> filtro());
        rc.ricercaISBNField.textProperty().addListener((obs, oldV, newV) -> filtro());*/

        // Carica i libri
        setItems(libri.getLista());

    }

    @Override
    public void add(ActionEvent event) {
        super.add(event);
        
        setItems(libri.getLista());
    }

    public void filtro() {
        FiltroLibri filtro = ricercaController.new FiltroLibri();
        setItems(libri.filtra(filtro));
    }
}
