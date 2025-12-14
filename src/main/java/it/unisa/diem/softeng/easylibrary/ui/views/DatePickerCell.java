package it.unisa.diem.softeng.easylibrary.ui.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;

public class DatePickerCell<S, T extends LocalDate> extends TableCell<S, T> {

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
            commitEdit((T) datePicker.getValue());
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
