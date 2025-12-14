package it.unisa.diem.softeng.easylibrary.archivio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @brief Interfaccia generica per un archivio di elementi.
 * 
 * L'interfaccia definisce le operazioni di base su una collezione di elementi,
 * come registrazione, rimozione, modifica e filtraggio.
 * 
 * La lista di elementi e gli elementi al suo interno devono essere
 * modificati solo tramite l'uso di modifica().
 * 
 * @param <E> Tipo di elemento gestito dall'archivio
 */
public interface Archiviabile<E> extends Serializable {

    /**
     * @brief Restituisce la lista completa degli elementi nell'archivio.
     * @return Lista di elementi
     */
    List<E> getLista();

    /**
     * @brief Registra un nuovo elemento nell'archivio.
     * @param elemento Elemento da aggiungere
     * 
     * @throws ValoreGiaPresenteException se l'elemento è già presente
     */
    void registra(E elemento);

    /**
     * @brief Rimuove un elemento dall'archivio.
     * 
     * Segna come rimosso un elemento dall'archivio. Non è necessario
     * che l'elemento sia logicamente rimosso dalla lista. La rimozione può
     * trattarsi anche solo di una segnalazione sull'elemento.
     * 
     * @param elemento Elemento da rimuovere.
     * 
     * @throws ValoreNonPresenteException se l'elemento non è presente
     */
    void rimuovi(E elemento);
    
    
    /**
     * @brief Modifica un elemento dell'archivio applicando un'operazione.
     * 
     * @param elemento Elemento da modificare.
     * @param c Consumer che definisce come modificare l'elemento.
     * 
     * @throws ValoreNonPresenteException se l'elemento non è presente
     */
    void modifica(E elemento, Consumer<E> c);
    
    
    /**
     * @brief Filtra gli elementi dell'archivio secondo un criterio.
     * 
     * Applica il filtro `f` a tutti gli elementi e restituisce quelli che
     * soddisfano il criterio.
     * 
     * @param f Filtro da applicare.
     * @return Lista di elementi filtrati.
     */
    default List<E> filtra(Filtro<E> f) {
        List<E> listaFiltrata = new ArrayList<>();

        for (E el : getLista()) {
            if (f.controlla(el)) {
                listaFiltrata.add(el);
            }
        }
        return listaFiltrata;
    }
    
}
