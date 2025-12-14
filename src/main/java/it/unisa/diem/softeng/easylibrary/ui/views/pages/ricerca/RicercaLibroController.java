package it.unisa.diem.softeng.easylibrary.ui.views.pages.ricerca;

import it.unisa.diem.softeng.easylibrary.archivio.Filtro;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RicercaLibroController {

    @FXML
    public TextField ricercaTitoloField;
    @FXML
    public TextField ricercaAutoreField;
    @FXML
    public TextField ricercaISBNField;

    public class FiltroLibri implements Filtro<Libro> {

        final String titoloFilter = ricercaTitoloField.getText().trim().toLowerCase(Locale.ROOT);
        final String autoreFilter = ricercaAutoreField.getText().trim().toLowerCase(Locale.ROOT);
        final String isbnFilter = ricercaISBNField.getText().trim().toLowerCase(Locale.ROOT);

        @Override
        public boolean controlla(Libro libro) {
            if (titoloFilter.isEmpty() && autoreFilter.isEmpty() && isbnFilter.isEmpty()) {
                return true;
            }

            boolean matchesTitolo = true;
            boolean matchesAutore = true;
            boolean matchesISBN = true;

            if (!titoloFilter.isEmpty()) {
                String titolo = libro.getTitolo().toLowerCase(Locale.ROOT);
                matchesTitolo = titolo.startsWith(titoloFilter);
            }

            if (!autoreFilter.isEmpty()) {
                // Il filtro è soddisfatto se ALMENO UN autore soddisfa il criterio
                matchesAutore = libro.getAutori().stream().anyMatch(autore -> {
                    String nome = autore.getAnagrafica().getNome().toLowerCase(Locale.ROOT);
                    String cognome = autore.getAnagrafica().getCognome().toLowerCase(Locale.ROOT);

                    return nome.startsWith(autoreFilter) || cognome.startsWith(autoreFilter);
                });
            }

            if (!isbnFilter.isEmpty()) {
                String isbnStr = libro.getISBN().getISBN().toLowerCase(Locale.ROOT);
                matchesISBN = isbnStr.startsWith(isbnFilter);
            }

            return matchesTitolo && matchesAutore && matchesISBN;
        }

    }
}
