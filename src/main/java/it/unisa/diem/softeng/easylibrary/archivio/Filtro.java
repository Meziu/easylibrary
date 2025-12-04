package it.unisa.diem.softeng.easylibrary.archivio;

public interface Filtro<T> {

    boolean controlla(T elemento);
}
