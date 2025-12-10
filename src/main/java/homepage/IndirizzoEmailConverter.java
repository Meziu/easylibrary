/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

/**
 *
 * @author marco
 */
// Esempio di IndirizzoEmailConverter.java (da creare)
package homepage; // o nel tuo pacchetto di utilità

import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmailInvalidoException;
import javafx.util.StringConverter;

public class IndirizzoEmailConverter extends StringConverter<IndirizzoEmail> {

    @Override
    public String toString(IndirizzoEmail email) {
        // Converte l'oggetto del modello (IndirizzoEmail) in Stringa per la visualizzazione
        return (email == null) ? "" : email.toString();
    }

    @Override
    public IndirizzoEmail fromString(String string) {
        // Converte la Stringa digitata dall'utente in un oggetto IndirizzoEmail
        if (string == null || string.trim().isEmpty()) {
            return null; 
        }
        
        try {
            // Qui assumiamo che IndirizzoEmail(string) faccia la validazione
            return new IndirizzoEmail(string);
        } catch (IndirizzoEmailInvalidoException e) {
            // Se la stringa non è un'email valida, il convertitore restituisce null
            // o un valore che segnala l'errore. Restituire null è tipico.
            return null; 
        }
    }
}
