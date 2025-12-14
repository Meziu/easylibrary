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
 * È generalmente richiesto lista di elementi e gli elementi al suo interno siano
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
     * 
     * @param elemento Elemento da aggiungere.
     * 
     * @post L'elemento è inserito nell'archivio.
     * @post L'elemento è presente nella lista restituita da getLista()
     * ed è disponibile per modifiche tramite modifica().
     * 
     * @throws ValoreGiaPresenteException se l'elemento è già presente, in
     * archivi senza duplicati.
     */
    void registra(E elemento);

    /**
     * @brief Rimuove un elemento dall'archivio.
     * 
     * Segna come rimosso un elemento dall'archivio. Non è richiesto
     * che l'elemento sia strutturalemente rimosso dalla collezione. La
     * rimozione può trattarsi anche solo di una segnalazione sull'elemento.
     * 
     * @param elemento Elemento da rimuovere.
     * 
     * @throws ValoreNonPresenteException se l'elemento non è presente.
     * 
     * @pre L'elemento è presente nell'archivio (inserito con registra()).
     * @post L'elemento è segnato come rimosso.
     */
    void rimuovi(E elemento);
    
    
    /**
     * @brief Modifica un elemento dell'archivio applicando un'operazione.
     * 
     * @param elemento Elemento da modificare.
     * @param c Consumer che definisce come modificare l'elemento.
     * 
     * @throws ValoreNonPresenteException se l'elemento non è presente.
     * 
     * @pre L'elemento è presente nell'archivio (inserito con registra()).
     * @post L'elemento è modificato per le operazioni specificate nel Consumer.
     */
    void modifica(E elemento, Consumer<E> c);
    
    
    /**
     * @brief Filtra gli elementi dell'archivio secondo un criterio.
     * 
     * Applica il filtro `f` a tutti gli elementi registrati e restituisce una
     * una lista contenenti quelli che soddisfano il criterio.
     * 
     * @param f Filtro da applicare.
     * @return Lista di elementi filtrati.
     * 
     * @post La lista di elementi filtrati contiene solo elementi per cui
     * è asserito il metodo Filtro.controlla() di `f`.
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
