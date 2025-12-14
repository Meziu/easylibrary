/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views.add.forms;

import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

/**
 *
 * @author marco
 */
public class PrestitoAddForm {
    @FXML
    public ComboBox<Utente> matricolaField;
    @FXML
    public ComboBox<Libro> isbnField;
    @FXML
    public DatePicker dataRestituzioneField;
}
