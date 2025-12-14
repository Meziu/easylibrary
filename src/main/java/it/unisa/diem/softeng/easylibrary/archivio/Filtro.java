package it.unisa.diem.softeng.easylibrary.archivio;

/**
 * @brief Interfaccia generica per definire un filtro.
 *
 * Viene utilizzata per filtrare elementi secondo una condizione specifica. La
 * condizione è definita implementando il metodo controlla().
 *
 * @param <T> Tipo dell'elemento da filtrare
 */
public interface Filtro<T> {

    /**
     * @brief Verifica se un elemento soddisfa il criterio del filtro.
     *
     * @param elemento Elemento da verificare
     * @return true se l'elemento soddisfa il filtro, false altrimenti
     */
    boolean controlla(T elemento);
}
