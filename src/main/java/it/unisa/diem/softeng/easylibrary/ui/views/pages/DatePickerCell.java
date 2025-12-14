package it.unisa.diem.softeng.easylibrary.ui.views.pages;

import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

public class DatePickerCell extends TableCell<Prestito, LocalDate> {

    private DatePicker datePicker;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DatePickerCell() {
        getStyleClass().add("date-picker-table-cell");
    }

    @Override
    public void startEdit() {
        if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();

        if (datePicker == null) {
            createDatePicker();
        }

        datePicker.setValue(getItem());

        setText(null);
        setGraphic(datePicker);
        datePicker.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        // Ripristina la visualizzazione del testo formattato
        setText(getString());
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
            setStyle(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getItem());
                }
                setText(null);
                setGraphic(datePicker);
                setStyle(null); // rimozione colore
            } else {
                // Visualizza il testo formattato
                setText(getString());
                setGraphic(null);
                
                // Colorazione date scadute.
                if (!item.isAfter(LocalDate.now()) &&
                        getTableRow() != null &&
                        getTableRow().getItem() != null &&
                        ((Prestito) getTableRow().getItem()).getStato().equals(StatoPrestito.ATTIVO)) {
                    setStyle(
                        "-fx-background-color: #ff6666;"
                    );
                } else {
                    setStyle(null);
                }
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker();

        datePicker.setConverter(new javafx.util.StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? FORMATTER.format(date) : "";
            }
            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string, FORMATTER);
            }
        });
        
        // Quando l'utente seleziona una data, committiamo il nuovo valore
        datePicker.setOnAction(event -> {
            commitEdit((LocalDate) datePicker.getValue());
        });

        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                cancelEdit();
            }
        });
    }
    private String getString() {
        LocalDate date = getItem();
        return (date == null) ? "" : FORMATTER.format(date);
    }
}
