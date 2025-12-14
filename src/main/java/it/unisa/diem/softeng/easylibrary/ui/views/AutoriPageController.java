/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    
    public static void mostraPerLista(Window owner, List<Autore> list) {
        FXMLLoader loader = new FXMLLoader(AutoriPageController.class.getResource("/res/EditorAutoriView.fxml"));
        loader.setController(new AutoriPageController(list));

        try {
            Stage stage = new Stage();
            stage.initOwner(owner);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoriTable.getItems().setAll(this.list);
        
        aggiungiButton.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return nomeField.getText().trim().isEmpty() || cognomeField.getText().trim().isEmpty();
        }, nomeField.textProperty(), cognomeField.textProperty()));
        
        rimuoviButton.disableProperty().bind(autoriTable.getSelectionModel().selectedItemProperty().isNull());
        
        nomeColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getNome()));
        cognomeColumn.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getAnagrafica().getCognome()));
        
        nomeColumn.setEditable(true);
        cognomeColumn.setEditable(true);
        
        nomeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cognomeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nomeColumn.setOnEditCommit(e -> {
            Autore a = e.getRowValue();
            
            if (e.getNewValue().trim().isEmpty()) {
                AlertGrande.mostraAlertErrore("Impossibile aggiungere un nome vuoto.");
            } else {
                a.getAnagrafica().setNome(e.getNewValue());
            }
            autoriTable.refresh();
        });
        cognomeColumn.setOnEditCommit(e -> {
            Autore a = e.getRowValue();
            
            if (e.getNewValue().trim().isEmpty()) {
                AlertGrande.mostraAlertErrore("Impossibile aggiungere un cognome vuoto.");
            } else {
                a.getAnagrafica().setCognome(e.getNewValue());
            }
            autoriTable.refresh();
        });
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
