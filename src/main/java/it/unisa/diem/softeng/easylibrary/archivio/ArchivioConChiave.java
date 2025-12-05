package it.unisa.diem.softeng.easylibrary.archivio;

import java.util.function.Consumer;

public interface ArchivioConChiave<K, V> extends Archivio<V> {

    V ottieni(K key);

    boolean contiene(K key);

}
