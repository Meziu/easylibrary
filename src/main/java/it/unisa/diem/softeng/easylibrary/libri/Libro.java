package it.unisa.diem.softeng.easylibrary.libri;

import java.io.Serializable;
import java.util.List;

public class Libro implements Comparable<Libro>, Serializable {

    private String titolo;
    private List<Autore> autori;
    private int annoPubblicazione;
    private final ISBN isbn;
    private int copieDisponibili;

    public Libro(String titolo, List<Autore> autori, int annoPubblicazione, String isbn, int copieDisponibili) {
        this.titolo = titolo;
        this.autori = autori;
        this.annoPubblicazione = annoPubblicazione;
        this.isbn = new ISBN(isbn);
        this.copieDisponibili = copieDisponibili;
    }

    public String getTitolo() {
        return titolo;
    }

    public List<Autore> getAutori() {
        return autori;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public ISBN getISBN() {
        return isbn;
    }

    public int getCopieDisponibili() {
        return copieDisponibili;
    }
    
    class Modificabile {
        public void setTitolo(String titolo) {
            Libro.this.titolo = titolo;
        }

        public void setAutori(List<Autore> autori) {
            Libro.this.autori = autori;
        }

        public void setAnnoPubblicazione(int annoPubblicazione) {
            Libro.this.annoPubblicazione = annoPubblicazione;
        }

        public void setCopieDisponibili(int copieDisponibili) {
            Libro.this.copieDisponibili = copieDisponibili;
        }

        public void aggiungiAutore(Autore a) {
            Libro.this.autori.add(a);
        }

        public void rimuoviAutore(Autore a) {
            Libro.this.autori.remove(a);
        }
    }
    
    Modificabile getModificabile() {
        return new Modificabile();
    }

    @Override
    public int compareTo(Libro l) {
        return this.isbn.compareTo(l.isbn);
    }

}
