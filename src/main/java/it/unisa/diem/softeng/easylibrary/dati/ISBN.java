package ClassiPrincipali;

import Interfacce.Filtro;

public class ISBN implements Filtro{
    private String isbn;
    
    public ISBN (String isbn){
        if (controlla())
            this.isbn = isbn.replaceAll("-", ""); // rimuove eventuali trattini
        
        ////throw new 
    }
    
    public String getISBN() {
        return isbn;
    }
    
    @Override
    public boolean controlla() {
        if (isbn.length() == 10) {
            return verificaISBN10();
        } else if (isbn.length() == 13) {
            return verificaISBN13();
        } else {
            return false;
        }
    }

    // Verifica ISBN-10
    private boolean verificaISBN10() {
        int somma = 0;
        for (int i = 0; i < 9; i++) {
            char c = isbn.charAt(i);
            if (!Character.isDigit(c)) return false;
            somma += (c - '0') * (10 - i);
        }

        // Controllo cifra di controllo
        char check = isbn.charAt(9);
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
    private boolean verificaISBN13() {
        int somma = 0;
        for (int i = 0; i < 12; i++) {
            char c = isbn.charAt(i);
            if (!Character.isDigit(c)) return false;
            int cifra = c - '0';
            somma += (i % 2 == 0) ? cifra : cifra * 3;
        }

        int checkCalcolato = (10 - (somma % 10)) % 10;
        char ultimo = isbn.charAt(12);
        if (!Character.isDigit(ultimo)) return false;
        int checkReale = ultimo - '0';

        return checkCalcolato == checkReale;
    }
}
