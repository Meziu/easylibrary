package it.unisa.diem.softeng.easylibrary.libri;

public final class ISBN implements Comparable<ISBN> {

    private String isbn;

    public ISBN(String isbn) {
        setISBN(isbn);
    }

    public String getISBN() {
        return isbn;
    }

    protected void setISBN(String nuovoISBN) {
        if (verifica(nuovoISBN)) {
            this.isbn = nuovoISBN.replaceAll("-", ""); // rimuove eventuali trattini
        } else {
            throw new ISBNInvalidoException(); // TODO
        }
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

    @Override
    public int compareTo(ISBN i) {
        return this.isbn.compareTo(i.isbn);
    }

    @Override
    public int hashCode() {
        return this.isbn.hashCode();
    }

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
