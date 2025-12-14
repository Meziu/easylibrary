/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views.add;

import it.unisa.diem.softeng.easylibrary.ui.views.add.forms.PrestitoAddForm;
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
import it.unisa.diem.softeng.easylibrary.ui.views.AlertGrande;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

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

        // Rendi le ComboBox editabili per permettere la ricerca
        this.formController.matricolaField.setEditable(true);
        this.formController.isbnField.setEditable(true);

        // Crea liste osservabili dalle liste originali
        ObservableList<Utente> utentiObservable = FXCollections.observableArrayList(utenti.getLista());
        ObservableList<Libro> libriObservable = FXCollections.observableArrayList(libri.getLista());

        // Imposta le liste iniziali
        this.formController.matricolaField.setItems(utentiObservable);
        this.formController.isbnField.setItems(libriObservable);

        // StringConverter per Utente
        this.formController.matricolaField.setConverter(new javafx.util.StringConverter<Utente>() {
            @Override
            public String toString(Utente utente) {
                if (utente == null) {
                    return "";
                }
                
                // Estrazione di matricola dalla stringa
                String str = utente.toString();
                int start = str.indexOf('<');
                int end = str.indexOf('>');
                return (start != -1 && end != -1) ? str.substring(start + 1, end) : str;
            }

            @Override
            public Utente fromString(String string) {
                try {
                    Matricola m = new Matricola(string);
                    return utenti.ottieni(m);
                } catch (Exception e) {
                    // Se la matricola non è completa o valida
                    return null;
                }
            }
        });

        // StringConverter per Libro
        this.formController.isbnField.setConverter(new javafx.util.StringConverter<Libro>() {
            @Override
            public String toString(Libro libro) {
                if (libro == null) {
                    return "";
                }
                
                // Estrazione di isbn dalla stringa
                String str = libro.toString();
                int start = str.indexOf('<');
                int end = str.indexOf('>');
                return (start != -1 && end != -1) ? str.substring(start + 1, end) : str;
            }

            @Override
            public Libro fromString(String string) {
                try {
                    ISBN i = new ISBN(string);
                    return libri.ottieni(i);
                } catch (Exception e) {
                    // Se la matricola non valida
                    return null;
                }
            }
        });

        // La lista mostra nome, cognome e matricola
        this.formController.matricolaField.setCellFactory(lv -> new javafx.scene.control.ListCell<Utente>() {
            @Override
            protected void updateItem(Utente item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        // La lista mostra titolo e isbn
        this.formController.isbnField.setCellFactory(lv -> new javafx.scene.control.ListCell<Libro>() {
            @Override
            protected void updateItem(Libro item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.toString());
            }
        });

        // Filtro per utenti basato
        this.formController.matricolaField.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            // Nascondi comboboxlista prima di aggiornare lista
            if (this.formController.matricolaField.isShowing()) {
                this.formController.matricolaField.hide();
            }

            ObservableList<Utente> filteredUtenti = FXCollections.observableArrayList(
                    utenti.filtra(u -> u.getMatricola().getMatricola().toLowerCase()
                    .startsWith(newValue.toLowerCase()))
            );

            this.formController.matricolaField.setItems(filteredUtenti);

            // Rimostra lista se ci sono risultati
            if (!filteredUtenti.isEmpty()) {
                this.formController.matricolaField.show();
            }
        });

        // Filtro per libri
        this.formController.isbnField.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (this.formController.isbnField.isShowing()) {
                this.formController.isbnField.hide();
            }

            ObservableList<Libro> filteredLibri = FXCollections.observableArrayList(
                    libri.filtra(l -> l.getISBN().getISBN().toLowerCase()
                    .startsWith(newValue.toLowerCase()))
            );

            this.formController.isbnField.setItems(filteredLibri);

            if (!filteredLibri.isEmpty()) {
                this.formController.isbnField.show();
            }
        });
    }

    @Override
    protected void confermaInserimento() {
        Prestito p = new Prestito(this.formController.matricolaField.getValue().getMatricola(), this.formController.isbnField.getValue().getISBN(), StatoPrestito.ATTIVO, this.formController.dataRestituzioneField.getValue());

        try {
            this.prestiti.registra(p);
            chiudiFinestra();
        } catch (LimitePrestitiSuperatoException e) {
            String error = "Utente con Matricola \"" + this.formController.matricolaField.getValue().getMatricola().getMatricola() + "\" ha raggiunto il limite di prestiti";
            AlertGrande.mostraAlertErrore(error);
        } catch (NessunaCopiaDisponibileException e) {
            String error = "Libro con ISBN \"" + this.formController.isbnField.getValue().getISBN().getISBN() + "\" non possiede copie disponibili";
            AlertGrande.mostraAlertErrore(error);
        }
    }

}
