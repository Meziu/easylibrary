package it.unisa.diem.softeng.easylibrary.dati;

import it.unisa.diem.softeng.easylibrary.eccezioni.ISBNInvalidoException;

public class ISBN implements Comparable<ISBN>{

    private String isbn;

    public ISBN(String isbn) {
        if (verifica(isbn)) {
            this.isbn = isbn.replaceAll("-", ""); // rimuove eventuali trattini
        } else {
            throw new ISBNInvalidoException(); // TODO
        }
    }

    public String getISBN() {
        return isbn;
    }

    public static boolean verifica(String id) {
        switch (id.length()) {
            case 10:
                return verificaISBN10(id);
            case 13:
                return verificaISBN13(id);
            default:
                return false;
        }
    }

    // Verifica ISBN-10
    private static boolean verificaISBN10(String id) {
        if (id.length() != 10) return false;
        
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
        if (id.length() != 13) return false;
        
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
    
    @Override
    public int compareTo(ISBN id) {
        return this.isbn.compareTo(id.isbn);
    }
}
