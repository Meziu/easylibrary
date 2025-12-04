package it.unisa.diem.softeng.easylibrary.dati;

import java.io.Serializable;

public abstract class Persona implements Comparable<Persona>, Serializable {

    protected String nome;
    protected String cognome;

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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String ncognome) {
        this.cognome = cognome;
    }

    @Override
    public int compareTo(Persona p) {
        int c = cognome.compareTo(p.cognome);

        if (c == 0) {
            return nome.compareTo(p.nome);
        }

        return c;
    }

}
