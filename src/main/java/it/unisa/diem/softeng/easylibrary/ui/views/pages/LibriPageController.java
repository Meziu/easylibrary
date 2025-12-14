package it.unisa.diem.softeng.easylibrary.ui.views.pages;

import it.unisa.diem.softeng.easylibrary.ui.views.dataadd.LibroAddController;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.ui.views.AutoriPageController;
import it.unisa.diem.softeng.easylibrary.ui.views.pages.ricerca.RicercaLibroController;
import it.unisa.diem.softeng.easylibrary.ui.views.pages.ricerca.RicercaLibroController.FiltroLibri;
import it.unisa.diem.softeng.easylibrary.ui.views.VisualizzatorePagine;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class LibriPageController extends DataPageController<Libro, RicercaLibroController, LibroAddController> {

    private Indicizzabile<ISBN, Libro> libri;

    public LibriPageController(VisualizzatorePagine vp, Indicizzabile<ISBN, Libro> libri) {
        super(libri, vp, new RicercaLibroController(), "Libri", "/res/RicercaLibro.fxml", new LibroAddController(libri));

        this.libri = libri;
    }

    @Override
    protected void initializeColonne() {

        TableColumn<Libro, String> titoloCol = new TableColumn<>("Titolo");
        titoloCol.setCellValueFactory(c
                -> new SimpleStringProperty(c.getValue().getTitolo())
        );

        TableColumn<Libro, Object> autoriCol = new TableColumn<>("Autori");
        autoriCol.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
        autoriCol.setCellFactory(col -> new TableCell<Libro, Object>() {

            final Button btn = new Button("Visualizza autori");

            @Override
            public void updateItem(Object o, boolean empty) {
                super.updateItem(o, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btn.setOnAction(event -> {
                        Libro l = getTableView().getItems().get(getIndex());

                        AutoriPageController.mostraPerLista(btn.getScene().getWindow(), l.getAutori());
                    });
                    setGraphic(btn);
                }
            }
        });

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

            libri.modifica(l, (libroTrovatoNellArchivio) -> {
                libroTrovatoNellArchivio.setTitolo(e.getNewValue());
            });
            
            setItems(libri.getLista());
            table.refresh();
        });

        // autori
        annoCol.setCellFactory(TextFieldTableCell.forTableColumn());
        annoCol.setOnEditCommit((TableColumn.CellEditEvent<Libro, String> e) -> {
            Libro l = e.getRowValue();

            libri.modifica(l, (libro) -> {
                libro.setAnnoPubblicazione(Integer.parseInt(e.getNewValue()));
            });
            
            setItems(libri.getLista());  
            table.refresh();
        });

        copieCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        copieCol.setOnEditCommit((TableColumn.CellEditEvent<Libro, Integer> e) -> {
            Libro l = e.getRowValue();

            try {
                int newValue = e.getNewValue();

                if (newValue < 0) {
                    throw new NumberFormatException();
                }

                libri.modifica(l, (libro) -> {
                    libro.setCopieDisponibili(newValue);
                });

            } catch (NumberFormatException ex) {
                table.refresh();
                libri.modifica(l, (libro) -> {
                    libro.setCopieDisponibili(e.getOldValue());
                });

                new Alert(Alert.AlertType.ERROR, "Il numero di copie deve essere un intero positivo o zero.").show();
            }
        });

        // Carica i libri
        setItems(libri.getLista());        
    }
    
    @Override
    protected void initializeFiltro() {
        // Filtro
        this.table.itemsProperty().bind(Bindings.createObjectBinding(() -> {
            return FXCollections.observableList(libri.filtra(ricercaController.new FiltroLibri()));
        }, ricercaController.ricercaTitoloField.textProperty(), ricercaController.ricercaAutoreField.textProperty(), ricercaController.ricercaISBNField.textProperty()));
    }
}
