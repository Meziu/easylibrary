package it.unisa.diem.softeng.easylibrary.eccezioni;

public class IndirizzoEmailInvalidoException extends RuntimeException {

    public IndirizzoEmailInvalidoException(String string) {
        super(string);
    }

    public IndirizzoEmailInvalidoException() {
    }
    
}
