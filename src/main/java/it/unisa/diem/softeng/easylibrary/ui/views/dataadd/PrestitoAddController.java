/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views.dataadd;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.NessunaCopiaDisponibileException;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.LimitePrestitiSuperatoException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 *
 * @author marco
 */
public class PrestitoAddController extends DataAddController<PrestitoAddForm> implements Initializable {

    private Archiviabile<Prestito> prestiti;
    private Indicizzabile<Matricola, Utente> utenti;
    private Indicizzabile<ISBN, Libro> libri;

    public PrestitoAddController(Archiviabile<Prestito> prestiti, Indicizzabile<Matricola, Utente> utenti, Indicizzabile<ISBN, Libro> libri) {
        super("Prestito", new PrestitoAddForm(), "/res/AddPrestitiForm.fxml");

        this.prestiti = prestiti;
        this.utenti = utenti;
        this.libri = libri;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        inserimentoValido.bind(Bindings.createBooleanBinding(() -> {
            return this.formController.matricolaField.getValue() != null
                    && this.formController.isbnField.getValue() != null
                    && this.formController.dataRestituzioneField.getValue() != null
                    && this.formController.dataRestituzioneField.getValue().isAfter(LocalDate.now());
        },
                this.formController.matricolaField.valueProperty(),
                this.formController.isbnField.valueProperty(),
                this.formController.dataRestituzioneField.valueProperty()
        ));

        SimpleListProperty<Utente> utentiList = new SimpleListProperty<>(FXCollections.observableList(this.utenti.getLista()));
        SimpleListProperty<Libro> libriList = new SimpleListProperty<>(FXCollections.observableList(this.libri.getLista()));

        this.formController.matricolaField.itemsProperty().bindBidirectional(utentiList);
        this.formController.isbnField.itemsProperty().bindBidirectional(libriList);
    }

    @Override
    protected void confermaInserimento() {
        Prestito p = new Prestito(this.formController.matricolaField.getValue().getMatricola(), this.formController.isbnField.getValue().getISBN(), StatoPrestito.ATTIVO, this.formController.dataRestituzioneField.getValue());

        try {
            this.prestiti.registra(p);
            chiudiFinestra();
        } catch (LimitePrestitiSuperatoException e) {
            String error = "Utente con Matricola \"" + this.formController.matricolaField.getValue().getMatricola().getMatricola() + "\" ha raggiunto il limite di prestiti";
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
        } catch (NessunaCopiaDisponibileException e) {
            String error = "Libro con ISBN \"" + this.formController.isbnField.getValue().getISBN().getISBN() + "\" non possiede copie disponibili";
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
        }
    }

}
