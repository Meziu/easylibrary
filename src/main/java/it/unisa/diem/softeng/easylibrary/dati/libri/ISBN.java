package it.unisa.diem.softeng.easylibrary.dati.libri;

import java.io.Serializable;

/**
 *
 * @brief Classe che rappresenta l'identificativo di un libro.
 * 
 * Ogni \ref Libro è associato univocamente ad un codice ISBN. I codici ISBN sono composti da 10 o
 * 13 cifre numeriche, con un particolare criterio di validità (per standard
 * internazionale).
 *
 * @see Libro
 */
public final class ISBN implements Comparable<ISBN>, Serializable {

    private final String isbn;

    /**
     *
     * @brief Costruttore. 
     * Costruisce un nuovo oggetto ISBN a partire dalla
     * stringa fornita, verificandone la validità.
     * Rimuove eventuali trattini e verifica che il codice sia valido.
     *
     * @param\[in] isbn Stringa di caratteri del codice ISBN.
     *
     * @throws ISBNInvalidoException Se il codice inserito non è conforme allo
     * standard di validità.
     *
     * \see verifica()
     *
     */
    public ISBN(String isbn) {
        isbn = isbn.replaceAll("-", ""); // rimuove eventuali trattini
        
        if (verifica(isbn)) {
            this.isbn = isbn;
        } else {
            throw new ISBNInvalidoException();
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
     * @brief Verifica del codice ISBN. Verifica se la stringa passata come
     * argomento sia o meno un codice ISBN valido a seconda degli standard
     * definiti dal documento ISO 2108.
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

    /**
     * @brief Verifica se una stringa può essere considerata un ISBN-10.
     *
     * Questa versione controlla che:
     * - la lunghezza sia esattamente 10 caratteri
     * - i primi 9 caratteri siano numeri
     * - l'ultimo carattere sia un numero oppure 'X'/'x'
     *
     * @param\[in] id La stringa da verificare come ISBN-10
     * 
     * @return true se la stringa rispetta le regole sopra, false altrimenti
     */
    private static boolean verificaISBN10(String id) {
        if (id.length() != 10) {
            return false;
        }

        for (int i = 0; i < 9; i++) {
            char c = id.charAt(i);

            if (!Character.isDigit(c)) {
                return false;
            }
        }

        // Controllo ultima cifra
        char check = id.charAt(9);
        if (check == 'X' || check == 'x' || Character.isDigit(check)) {
            return true;
        }

        return false;
    }

     
    /**
     * @brief Verifica se una stringa può essere considerata un ISBN-13.
     *
     * Questa versione controlla che:
     * - la lunghezza sia esattamente 13 caratteri
     * - i 13 caratteri siano numeri
     *
     * @param\[in] id La stringa da verificare come ISBN-13
     * 
     * @return true se la stringa rispetta le regole sopra, false altrimenti
     */
    private static boolean verificaISBN13(String id) {
        if (id.length() != 13) {    // Deve essere lungo esattamente 13 caratteri
            return false;
        }

        for (int i = 0; i < 13; i++) {   // Analizza le cifre
            char c = id.charAt(i);

            if (!Character.isDigit(c)) {    // Se non è una cifra, non è valido
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @brief Comparazione con un altro ISBN. La comparazione è svolta aderendo
     * al contratto di Comparable, dove un ISBN è ordinato rispetto ad un altro
     * in ordine lessicografico della stringa di caratteri associata.
     *
     * @param\[in] i isbn da confrontare con l'istanza corrente.
     *
     * @return Valore negativo, zero o positivo se l'isbn corrente è
     * rispettivamente minore, uguale o maggiore dell'isbn passato come
     * parametro.
     */
    @Override
    public int compareTo(ISBN i) {
        return this.isbn.compareTo(i.isbn);
    }

    /**
     *
     * @brief HashCode associato all'ISBN. Il calcolo è svolto aderendo al
     * contratto di Object.hashCode().
     */
    @Override
    public int hashCode() {
        return this.isbn.hashCode();
    }

    /**
     *
     * @brief Criterio di uguaglianza.
     * Il criterio aderisce al contratto di Object.equals(),
     * dove un codice ISBN è considerato uguale ad un altro
     * solo se la stringa di caratteri associata è uguale.
     * 
     * @return true se i due ISBN hanno lo stesso codice, false altrimenti.
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
        return this.isbn.equals(other.isbn);
    }
}
