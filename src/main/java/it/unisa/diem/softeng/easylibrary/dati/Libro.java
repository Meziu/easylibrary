package it.unisa.diem.softeng.easylibrary.dati;

import java.util.List;


public class Libro implements Comparable<Libro> {

    private String titolo;
    private List<Autore> autori;
    private String annoPubblicazione;
    private ISBN isbn;
    private int copieDisponibili;

    public Libro(String titolo, List<Autore> autori, String annoPubblicazione, String isbn, int copieDisponibili) {
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

    public String getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public ISBN getISBN() {
        return isbn;
    }
    
    public int getCopieDisponibili(){
        return copieDisponibili;
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
    
    public void setCopieDisponibili(int copieDisponibili){
        this.copieDisponibili = copieDisponibili;
    }

    
    
    public void aggiungiAutore(Autore a) {
        autori.add(a);
    }

    public void rimuoviAutore(Autore a) {
        autori.remove(a);
    }
    
    
    
    @Override
    public int compareTo(Libro l) {
        return this.isbn.compareTo(l.isbn);
    }

}
