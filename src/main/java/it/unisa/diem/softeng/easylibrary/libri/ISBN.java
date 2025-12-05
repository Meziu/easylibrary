package it.unisa.diem.softeng.easylibrary.libri;

import java.io.Serializable;

/**
 * 
 * @brief Identificativo di un libro.
 * Ogni \ref Libro è associato univocamente ad un codice ISBN.
 * I codici ISBN sono composti da 10 o 13 cifre numeriche, con un particolare
 * criterio di validità (per standard internazionale).
 * 
 */
public final class ISBN implements Comparable<ISBN>, Serializable {

    private final String isbn;

    /**
     * 
     * @brief Costruttore.
     * Costruisce un nuovo oggetto ISBN a partire dalla
     * stringa di caratteri che ne compongono il codice, verificandone la validità.
     * 
     * @param\[in] isbn Stringa di caratteri del codice ISBN.
     * 
     * @throws ISBNInvalidoException Se il codice inserito non aderisce
     * allo standard di validità.
     * 
     * \see verifica()
     * 
     */
    public ISBN(String isbn) {
        if (verifica(isbn)) {
            this.isbn = isbn.replaceAll("-", ""); // rimuove eventuali trattini
        } else {
            throw new ISBNInvalidoException(); // TODO
        }
    }

    /**
     * @brief Getter della stringa del codice.
     * 
     * @return La stringa di caratteri che compongono il codice ISBN.
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * @brief Verifica del codice ISBN.
     * Verifica se la stringa passata come argomento sia o meno un codice ISBN
     * valido a seconda degli standard definiti dal documento ISO 2108.
     * 
     * @param\[in] isbn Stringa di caratteri da verificare.
     * @return true se la stringa è un codice ISBN valido, false altrimenti.
     */
    public static boolean verifica(String isbn) {
        switch (isbn.length()) {
            case 10:
                return verificaISBN10(isbn);
            case 13:
                return verificaISBN13(isbn);
            default:
                return false;
        }
    }

    // Verifica ISBN-10
    private static boolean verificaISBN10(String id) {
        if (id.length() != 10) {
            return false;
        }

        int somma = 0;
        for (int i = 0; i < 9; i++) {
            char c = id.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            somma += (c - '0') * (10 - i);
        }

        // Controllo cifra di controllo
        char check = id.charAt(9);
        int valoreCheck;
        if (check == 'X' || check == 'x') {
            valoreCheck = 10;
        } else if (Character.isDigit(check)) {
            valoreCheck = check - '0';
        } else {
            return false;
        }

        somma += valoreCheck;
        return somma % 11 == 0;
    }

    // Verifica ISBN-13
    private static boolean verificaISBN13(String id) {
        if (id.length() != 13) {
            return false;
        }

        int somma = 0;
        for (int i = 0; i < 12; i++) {
            char c = id.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            int cifra = c - '0';
            somma += (i % 2 == 0) ? cifra : cifra * 3;
        }

        int checkCalcolato = (10 - (somma % 10)) % 10;
        char ultimo = id.charAt(12);
        if (!Character.isDigit(ultimo)) {
            return false;
        }
        int checkReale = ultimo - '0';

        return checkCalcolato == checkReale;
    }

    /**
     * 
     * @brief Comparazione con un altro ISBN.
     * La comparazione è svolta aderendo al contratto di Comparable,
     * dove un ISBN è ordinato rispetto ad un altro in ordine
     * lessicografico della stringa di caratteri associata.
     */
    @Override
    public int compareTo(ISBN i) {
        return this.isbn.compareTo(i.isbn);
    }

    /**
     * 
     * @brief HashCode associato all'ISBN.
     * Il calcolo è svolto aderendo al contratto di Object.hashCode().
     */
    @Override
    public int hashCode() {
        return this.isbn.hashCode();
    }

    /**
     * 
     * @brief Criterio di uguaglianza.
     * Il criterio aderisce al contratto di Object.equals(),
     * dove un codice ISBN è considerato uguale ad un altro solo se
     * la stringa di caratteri associata è uguale.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ISBN other = (ISBN) obj;
        if (!this.isbn.equals(other.isbn)) {
            return false;
        }
        return true;
    }

}
