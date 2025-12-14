package it.unisa.diem.softeng.easylibrary.ui.views.pages.ricerca;

import it.unisa.diem.softeng.easylibrary.archivio.Filtro;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RicercaUtenteController {

    @FXML
    public TextField ricercaCognomeField;
    @FXML
    public TextField ricercaMatricolaField;

    public class FiltroUtenti implements Filtro<Utente> {

        final String cognomeFilter = ricercaCognomeField.getText().trim().toLowerCase(Locale.ROOT);
        final String matricolaFilter = ricercaMatricolaField.getText().trim().toLowerCase(Locale.ROOT);

        @Override
        public boolean controlla(Utente utente) {
            if (cognomeFilter.isEmpty() && matricolaFilter.isEmpty()) {
                return true;
            }

            boolean matchesCognome = true;
            boolean matchesMatricola = true;

            if (!cognomeFilter.isEmpty()) {
                String cognome = utente.getAnagrafica().getCognome().toLowerCase(Locale.ROOT);

                matchesCognome = cognome.startsWith(cognomeFilter);
            }

            if (!matricolaFilter.isEmpty()) {
                String matricolaStr = utente.getMatricola().getMatricola().toLowerCase(Locale.ROOT);

                matchesMatricola = matricolaStr.startsWith(matricolaFilter);
            }

            return matchesCognome && matchesMatricola;
        }

    }

}
