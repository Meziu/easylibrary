package it.unisa.diem.softeng.easylibrary.ui.views;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public abstract class DataPageController<T> {
    @FXML
    protected TableView<T> table;

    /** Implementato dalle sottoclassi */
    protected abstract void initializeColumns();

    @FXML
    public void initialize() {
        initializeColumns();
    }

    /** Per riempire la tabella */
    public void setItems(java.util.List<T> items) {
        table.getItems().setAll(items);
    }
}
