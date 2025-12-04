package it.unisa.diem.softeng.easylibrary.archivio;

public interface ArchivioConChiave<K, V> {

    V ottieni(K key);

    boolean contiene(K key);

    void riassegna(K oldKey, K newKey);

}
