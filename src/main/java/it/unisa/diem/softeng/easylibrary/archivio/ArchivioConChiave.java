package it.unisa.diem.softeng.easylibrary.archivio;


/**
 * @brief Interfaccia generica per un archivio indicizzato tramite chiave.
 * 
 * Estende \ref Archivio aggiungendo operazioni per accedere agli elementi
 * tramite una chiave univoca.
 * 
 * @param <K> Tipo della chiave
 * @param <V> Tipo dell'elemento gestito dall'archivio
 * 
 * @see Archivio
 */
public interface ArchivioConChiave<K, V> extends Archivio<V> {

    /**
     * @brief Restituisce l'elemento associato ad una chiave.
     * 
     * @param key Chiave dell'elemento da ottenere
     * @return Elemento associato alla chiave, oppure null se non presente
     */
    V ottieni(K key);

    /**
     * @brief Controlla se un elemento con la chiave specificata esiste nell'archivio.
     * 
     * @param key Chiave da verificare
     * @return true se esiste un elemento con la chiave, false altrimenti
     */
    boolean contiene(K key);

}
