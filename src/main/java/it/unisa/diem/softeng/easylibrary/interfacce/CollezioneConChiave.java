package it.unisa.diem.softeng.easylibrary.interfacce;

public interface CollezioneConChiave<K, V> {

    public V ottieni(K key);

    public boolean contiene(K key);
}
