package it.unisa.diem.softeng.easylibrary.archivio;

public interface Filtro<T> {

    public boolean controlla(T elemento);
}
