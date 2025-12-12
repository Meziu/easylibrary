package homepage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

/**
 * Cella personalizzata che utilizza un DatePicker per l'editing di campi LocalDate.
 */
public class DatePickerTableCell<S, T extends LocalDate> extends TableCell<S, T> {

    private DatePicker datePicker;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DatePickerTableCell() {
        // Rende la cella focalizzabile e gestisce i doppi click
        getStyleClass().add("date-picker-table-cell");
    }

    // Metodo chiamato quando la cella entra in modalità di editing
    @Override
    public void startEdit() {
        if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();

        if (datePicker == null) {
            createDatePicker();
        }

        // Imposta il valore corrente della cella nel DatePicker
        datePicker.setValue(getItem());

        // Sostituisce la visualizzazione del testo con il DatePicker
        setText(null);
        setGraphic(datePicker);
        datePicker.requestFocus();
    }

    // Metodo chiamato quando la cella esce dalla modalità di editing
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        // Ripristina la visualizzazione del testo formattato
        setText(getString());
        setGraphic(null);
    }

    // Metodo chiamato per aggiornare la visualizzazione della cella
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getItem());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                // Visualizza il testo formattato
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker();
        
        // Imposta il convertitore di stringhe per la visualizzazione nel campo di testo
        datePicker.setConverter(new javafx.util.StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? FORMATTER.format(date) : "";
            }
            @Override
            public LocalDate fromString(String string) {
                // Non useremo l'input testuale, ma è richiesto
                return LocalDate.parse(string, FORMATTER);
            }
        });
        
        // Quando l'utente seleziona una data, committiamo il nuovo valore
        datePicker.setOnAction(event -> {
            commitEdit((T) datePicker.getValue());
        });

        // Quando il DatePicker perde il focus, annulla l'editing
        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                cancelEdit();
            }
        });
    }

    // Restituisce il testo formattato da mostrare nella cella
    private String getString() {
        LocalDate date = getItem();
        return (date == null) ? "" : FORMATTER.format(date);
    }
}