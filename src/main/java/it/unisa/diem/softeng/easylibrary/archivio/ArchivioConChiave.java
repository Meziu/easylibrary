package it.unisa.diem.softeng.easylibrary.archivio;

import java.util.function.Consumer;

public interface ArchivioConChiave<K, V> {

    V ottieni(K key);

    boolean contiene(K key);
    
    void modifica(K key, Consumer<V> c);

}
