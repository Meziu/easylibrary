package it.unisa.diem.softeng.easylibrary.dati;

import java.io.Serializable;

public abstract class Persona implements Comparable<Persona>, Serializable {

    private String nome;
    private String cognome;

    public Persona(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    protected void setNome(String nome) {
        this.nome = nome;
    }

    protected void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public int compareTo(Persona p) {
        int c = this.cognome.compareTo(p.cognome);

        if (c == 0) {
            return this.nome.compareTo(p.nome);
        }

        return c;
    }

}
