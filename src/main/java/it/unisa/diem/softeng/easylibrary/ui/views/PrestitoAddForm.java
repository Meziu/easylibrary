/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 *
 * @author marco
 */
public class PrestitoAddForm {
    @FXML
    public ComboBox<Matricola> matricolaField;
    @FXML
    public ComboBox<ISBN> isbnField;
}
