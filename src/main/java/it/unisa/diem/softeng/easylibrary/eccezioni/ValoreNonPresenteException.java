package it.unisa.diem.softeng.easylibrary.eccezioni;

/**
 *
 * @author serenagiannitti
 */
public class ValoreNonPresenteException extends RuntimeException {

    public ValoreNonPresenteException() {
    }

    public ValoreNonPresenteException(String string) {
        super(string);
    }

}
