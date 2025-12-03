package Interfacce;

public interface CollezioneConChiave<K, V> {
    public V ottieni(K key);
    public boolean chiavePresente(K key);
}
