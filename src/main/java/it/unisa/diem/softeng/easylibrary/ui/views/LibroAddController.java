/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;

/**
 *
 * @author marco
 */
public class LibroAddController extends DataAddController<LibroAddForm>{
    private Archiviabile<Libro> libri;

    public LibroAddController(Archiviabile<Libro> libri) {
        super("Libro", new LibroAddForm(), "/res/AddLibriForm.fxml");

        this.libri = libri;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        inserimentoValido.bind(Bindings.createBooleanBinding(
            () -> {
                return !this.formController.titoloField.getText().isEmpty() &&
                        ISBN.verifica(this.formController.isbnField.getText()) &&
                        !this.formController.listaAutori.isEmpty() &&
                        this.formController.annoField.getValue() != null &&
                        this.formController.copieField.getValue() != null &&
                        this.formController.copieField.getValue() >= 0;
            },
            this.formController.titoloField.textProperty(),
            this.formController.isbnField.textProperty(),
            this.formController.listaAutori,
            this.formController.annoField.valueProperty(),
            this.formController.copieField.valueProperty()
        ));
    }
    
    @Override
    protected void confermaInserimento() {
        Libro newLibro = new Libro(this.formController.titoloField.getText(),
                                        this.formController.listaAutori,
                                        this.formController.annoField.getValue(),
                                        new ISBN(this.formController.isbnField.getText()),
                                        this.formController.copieField.getValue()
        );
        
        try {
            libri.registra(newLibro);
        } catch (ValoreGiàPresenteException e) {
            String error = "Libro con ISBN \"" + newLibro.getISBN().getISBN() + "\" già presente nell'archivio";
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
        }
        
        this.formController.listaAutori.clear();
        
        chiudiFinestra();
    }
    
}
