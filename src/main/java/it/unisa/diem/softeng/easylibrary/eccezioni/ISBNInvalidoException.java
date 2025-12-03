package it.unisa.diem.softeng.easylibrary.eccezioni;

public class ISBNInvalidoException extends RuntimeException {

    public ISBNInvalidoException() {
    }

    public ISBNInvalidoException(String string) {
        super(string);
    }

    
}
