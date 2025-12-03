package it.unisa.diem.softeng.easylibrary.dati;

import it.unisa.diem.softeng.easylibrary.dati.ISBN;
import java.util.ArrayList;
import java.util.List;

public class Libro {

    private String titolo;
    private List<Autore> autori;
    private String annoPubblicazione;
    private ISBN isbn;

    public Libro(String titolo, List<Autore> autori, String annoPubblicazione, String isbn) {
        this.titolo = titolo;
        this.autori = autori;
        this.annoPubblicazione = annoPubblicazione;
        this.isbn = new ISBN(isbn);
    }

    public String getTitolo() {
        return titolo;
    }

    public List<Autore> getAutori() {
        return autori;
    }

    public String getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public ISBN getISBN() {
        return isbn;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutori(List<Autore> autori) {
        this.autori = autori;
    }

    public void setAnnoPubblicazione(String annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

    public void registra(Autore a) {
        autori.add(a);
    }

    public void rimuovi(Autore a) {
        autori.remove(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Libro)) {
            return false;
        }
        Libro l = (Libro) o;
        return isbn.equals(l.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

}
