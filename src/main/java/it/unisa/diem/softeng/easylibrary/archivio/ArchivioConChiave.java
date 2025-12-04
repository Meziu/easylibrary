package it.unisa.diem.softeng.easylibrary.archivio;

public interface ArchivioConChiave<K, V> {

    public V ottieni(K key);

    public boolean contiene(K key);
}
