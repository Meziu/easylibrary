/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
