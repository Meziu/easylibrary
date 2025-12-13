/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;

/**
 *
 * @author marco
 */
class PrestitoAddController extends DataAddController<PrestitoAddForm> {
    
    private Archiviabile<Prestito> prestiti;

    public PrestitoAddController(Archiviabile<Prestito> prestiti){
        super("Prestito", new PrestitoAddForm(), "/res/AddPrestitiForm.fxml");
        
        this.prestiti = prestiti;
    }
    
    @Override
    protected void confermaInserimento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
