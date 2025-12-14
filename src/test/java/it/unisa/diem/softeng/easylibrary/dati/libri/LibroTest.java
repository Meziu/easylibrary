package it.unisa.diem.softeng.easylibrary.dati.libri;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LibroTest {

    private List<Autore> aut;
    private Libro l;
    private ISBN isbn;

    @Before
    public void setUp() {
        aut = new ArrayList<>();
        aut.add(new Autore("George", "Orwell"));

        isbn = new ISBN("123456789X");

        l = new Libro("1984", aut, 1949, isbn, 2);
    }

    /* 
    *  COSTRUTTORE
    *
     */
    @Test
    public void testCostruzioneLibroValida() {
        Libro l1 = new Libro("1984", aut, 1949, isbn, 2);
        assertNotNull(l1);
        assertEquals("1984", l1.getTitolo());
        assertEquals(aut, l1.getAutori());
        assertEquals(1949, l1.getAnnoPubblicazione());
        assertEquals(new ISBN("123456789X"), l1.getISBN());
        assertTrue(l1.getCopieDisponibili() >= 0);
        assertEquals(2, l1.getCopieDisponibili());
    }

    @Test
    public void testCostruttoreCopieNegative() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Libro("1984", aut, 1949, isbn, -1)
        );
    }

    @Test
    public void testCostruzioneLibroTitoloNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Libro(null, aut, 1949, isbn, -1)
        );
    }

    @Test
    public void testCostruzioneLibroTitoloVuoto() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Libro("", aut, 1949, isbn, -1)
        );
    }

    @Test
    public void testCostruzioneLibroAutoriVuoti() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Libro("1984", new ArrayList<>(), 1949, isbn, -1)
        );
    }

    @Test
    public void testCostruzioneLibroAutoriNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Libro("1984", null, 1949, isbn, -1)
        );
    }

    @Test
    public void testCostruttoreAnnoFuturo() {
        int futuro = Year.now().getValue() + 1;
        assertThrows(IllegalArgumentException.class, ()
                -> new Libro("1984", aut, futuro, isbn, 2)
        );
    }

    /* 
    *   GET
    * 
     */
    @Test
    public void testGetTitolo() {
        assertEquals(l.getTitolo(), "1984");
    }

    @Test
    public void testGetAutori() {
        assertEquals(l.getAutori(), aut);
    }

    @Test
    public void testGetAnnoPubblicazione() {
        assertEquals(l.getAnnoPubblicazione(), 1949);
    }

    @Test
    public void testGetISBN() {
        assertEquals(l.getISBN(), new ISBN("123456789X"));
    }

    @Test
    public void testGetCopieDisponibili() {
        assertEquals(l.getCopieDisponibili(), 2);
    }

    /* 
    *   SET
    * 
     */
    @Test
    public void testSetTitolo() {
        String titolo = "Animal Farm";
        l.setTitolo(titolo);
        assertEquals("Animal Farm", l.getTitolo());
    }

    @Test
    public void testSetTitoloVuoto() {
        assertThrows(IllegalArgumentException.class, ()
                -> l.setTitolo("")
        );
    }

    @Test
    public void testSetTitoloNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> l.setTitolo(null)
        );
    }

    @Test
    public void testSetAutori() {
        List<Autore> a = new ArrayList<>();
        a.add(new Autore("Alessandro", "Manzoni"));

        List<Autore> old = l.getAutori();
        l.setAutori(a);
        assertNotEquals(l.getAutori(), old);
    }

    @Test
    public void testSetAutoriNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> l.setAutori(null)
        );
    }

    @Test
    public void testSetAutoriVuoti() {
        assertThrows(IllegalArgumentException.class, ()
                -> l.setAutori(new ArrayList<>())
        );
    }

    @Test
    public void testSetAnnoPubblicazione() {
        int old = l.getAnnoPubblicazione();
        l.setAnnoPubblicazione(1950);
        assertNotEquals(l.getAnnoPubblicazione(), old);
    }

    @Test
    public void testAnnoPubblicazioneFuturo() {
        int futuro = Year.now().getValue() + 1;
        assertThrows(IllegalArgumentException.class, ()
                -> l.setAnnoPubblicazione(futuro)
        );
    }

    @Test
    public void testSetCopieDisponibili() {
        int old = l.getCopieDisponibili();
        l.setCopieDisponibili(3);
        assertNotEquals(l.getCopieDisponibili(), old);
    }

    @Test
    public void testSetCopieNegative() {
        assertThrows(IllegalArgumentException.class, ()
                -> l.setCopieDisponibili(-5)
        );
    }

    /* 
    *   LISTA AUTORI
    * 
     */
    @Test
    public void testAggiungiAutore() {
        Autore nuovo = new Autore("Luca", "Verdi");
        l.aggiungiAutore(nuovo);

        assertTrue(l.getAutori().contains(nuovo));
        assertEquals(2, l.getAutori().size());
    }

    @Test
    public void testAggiungiAutoreNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> l.aggiungiAutore(null)
        );
    }

    @Test
    public void testRimuoviAutore() {
        Autore rimuovere = new Autore("George", "Orwell");
        l.rimuoviAutore(rimuovere);

        assertFalse(l.getAutori().contains(rimuovere));
        assertEquals(0, l.getAutori().size());
    }

    /*
    *   EQUALS
    *
     */
    @Test
    public void equalsRiconosceDueLibriUguali() {
        Libro i = new Libro("1984", aut, 1949, new ISBN("123456789X"), 2);

        assertEquals(l, i);
        assertEquals(i, l); // simmetria
    }

    @Test
    public void equalsRiconosceLibriDiversi() {
        Libro i = new Libro("1950", aut, 1949, new ISBN("123456799X"), 2);

        assertNotEquals(l, i);
    }

    @Test
    public void equalsConNullRitornaFalse() {
        assertNotEquals(l, null);
    }

    @Test
    public void equalsConOggettoDiClasseDiversaRitornaFalse() {
        assertNotEquals(l, "Mario Rossi");  // tipo diverso
    }

    /*
    *  COMPARE TO
    * 
     */
    @Test
    public void testCompareToFunzionaCorrettamenteTitolo() {
        Libro l2 = new Libro("1880", aut, 1827, new ISBN("987654321X"), 2);

        assertTrue(l2.compareTo(l) < 0);
        assertTrue(l.compareTo(l2) > 0);
    }

    @Test
    public void testCompareToFunzionaCorrettamenteISBN() {
        Libro l2 = new Libro("1984", aut, 1949, new ISBN("853456789X"), 2);

        assertTrue(l.compareTo(l2) < 0);
        assertTrue(l2.compareTo(l) > 0);
    }

    @Test
    public void testCompareToRiconosceLibriUguali() {
        Libro l2 = new Libro("1984", aut, 1949, new ISBN("123456789X"), 2);
        assertEquals(0, l.compareTo(l2));
    }

    @Test
    public void testCompareToDistingueISBNDiversi() {
        Libro l2 = new Libro("1984", aut, 1949, new ISBN("987654321X"), 2);
        assertNotEquals(l, l2);
    }
}
