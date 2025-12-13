/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author marco
 */
public class AutoriPageController implements Initializable {
    @FXML
    private TableView<Autore> autoriTable;
    @FXML
    private TableColumn<Autore, String> nomeColumn;
    @FXML
    private TableColumn<Autore, String> cognomeColumn;
    
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private Button aggiungiButton;
    @FXML
    private Button rimuoviButton;
    
    // Lista in input ed output
    private List<Autore> list;
    
    public AutoriPageController(List<Autore> list) {
        this.list = list;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoriTable.getItems().setAll(this.list);
        
        aggiungiButton.disableProperty().bind(nomeField.textProperty().isEmpty().or(cognomeField.textProperty().isEmpty()));
        rimuoviButton.disableProperty().bind(autoriTable.getSelectionModel().selectedItemProperty().isNull());
        
        nomeColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getNome()));
        cognomeColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getCognome()));
    }
    
    @FXML
    public void aggiungiAutore(ActionEvent event) {
        autoriTable.getItems().add(new Autore(nomeField.getText(), cognomeField.getText()));
        
        nomeField.setText("");
        cognomeField.setText("");
    }
    
    @FXML
    public void rimuoviAutore(ActionEvent event) {
        autoriTable.getItems().remove(autoriTable.getSelectionModel().getSelectedItem());
    }
    
    @FXML
    public void conferma(ActionEvent event) {
        this.list.clear();
        this.list.addAll(autoriTable.getItems());
        
        Stage s = (Stage) autoriTable.getScene().getWindow();
        s.close();
    }
    
    @FXML
    public void annulla(ActionEvent event) {
        Stage s = (Stage) autoriTable.getScene().getWindow();
        s.close();
    }
}
