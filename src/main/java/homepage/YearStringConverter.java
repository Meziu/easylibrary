/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBNInvalidoException;
import java.time.DateTimeException;
import java.time.Year;
import javafx.util.StringConverter;

/**
 *
 * @author marco
 */
public class YearStringConverter extends StringConverter<Year>{
    
    @Override
    public String toString(Year year) {
        // Converte l'oggetto Year nel formato stringa per la visualizzazione
        if (year == null) {
            return "";
        }
        return String.valueOf(year.getValue()); // Es: Year.of(2024) -> "2024"
    }

    @Override
    public Year fromString(String string) {
        // Converte la stringa di input dell'utente (dall'editor) in un oggetto Year
        if (string == null || string.trim().isEmpty()) {
            return null; // Tratta i campi vuoti come null o fallimento
        }
        
        try {
            int annoInt = Integer.parseInt(string.trim());

            // 1. Verifica che l'anno sia positivo
            if (annoInt <= 0) {
                 return null; // Fallimento della validazione
            }
            
            // 2. Crea l'oggetto Year (qui possono verificarsi DateTimeException se l'anno è non valido)
            Year anno = Year.of(annoInt);
            
            // 3. Verifica che l'anno non sia nel futuro
            if (anno.isAfter(Year.now())) {
                return null; // Fallimento della validazione
            }

            return anno; // Successo

        } catch (NumberFormatException | DateTimeException e) {
            // Se l'input non è un numero valido o non è un anno valido
            return null; 
        }
    }
    
}
