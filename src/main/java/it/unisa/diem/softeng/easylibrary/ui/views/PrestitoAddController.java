/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author marco
 */
class PrestitoAddController extends DataAddController<PrestitoAddForm> implements Initializable {
    
    private Archiviabile<Prestito> prestiti;

    public PrestitoAddController(Archiviabile<Prestito> prestiti, Indicizzabile<Matricola, Utente> utenti, Indicizzabile<ISBN, Libro> libri){
        super("Prestito", new PrestitoAddForm(), "/res/AddPrestitiForm.fxml");
        
        this.prestiti = prestiti;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        inserimentoValido.bind(this.formController.matricolaField.valueProperty().isNull().not().and(
                        this.formController.isbnField.valueProperty().isNull().not()
        ));
        
        this.formController.matricolaField.itemsProperty();
        this.formController.isbnField.itemsProperty();
    }
    
    @Override
    protected void confermaInserimento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
