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

    @Before
    public void setUp() {
        aut = new ArrayList<>();
        aut.add(new Autore("George", "Orwell"));

        l = new Libro("1984", aut, 1949, "123456789X", 2);
    }

    @Test
    public void testCostruzioneLibro() {
        assertNotNull(l);
        assertEquals("1984", l.getTitolo());
        assertEquals(aut, l.getAutori());
        assertEquals(1949, l.getAnnoPubblicazione());
        assertEquals(new ISBN("123456789X"), l.getISBN());
        assertEquals(2, l.getCopieDisponibili());
    }

 
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

    @Test
    public void testSetTitolo() {
        String titolo = "Animal Farm" ;
        assertEquals("Animal Farm", l.getTitolo());
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
    public void testSetAnnoPubblicazione() {
        int old = l.getAnnoPubblicazione();
        l.setAnnoPubblicazione(1950);
        assertNotEquals(l.getAnnoPubblicazione(), old);
    }

    @Test
    public void testSetCopieDisponibili() {
        int old = l.getCopieDisponibili();
        l.setCopieDisponibili(3);
        assertNotEquals(l.getCopieDisponibili(), old);
    }

    @Test
    public void testAggiungiAutore() {
            ///?????????
    }

    @Test
    public void testRimuoviAutore() {
        //l.rimuoviAutore(new Autore("George", "Orwell"));
        //assertEquals(l.getAutori(), )
    }
    
    

    /* ----------------------------------------------------------------------
    *  COMPARE TO
    * ----------------------------------------------------------------------
    */
    @Test
    public void testCompareToFunzionaCorrettamenteTitolo() {
        Libro l2 = new Libro("I promessi sposi", aut, 1827, "987654321X", 2);

        assertTrue(l2.compareTo(l) < 0);
        assertTrue(l.compareTo(l2) > 0);
    }
    
    @Test
    public void testCompareToFunzionaCorrettamenteISBN() {
        Libro l2 = new Libro("1984", aut, 1949, "853456789X", 2);

        assertTrue(l.compareTo(l2) < 0);
        assertTrue(l2.compareTo(l) > 0);
    }

    
    @Test
    public void testCompareToRiconosceLibriUguali() {
        Libro l2 = new Libro("1984", aut, 1949, "123456789X", 2);
        assertEquals(l, l2);
        assertEquals(l.hashCode(), l2.hashCode());
    }

    @Test
    public void testCompareToDistingueISBNDiversi() {
        Libro l2 = new Libro("1984", aut, 1949, "987654321X", 2);
        assertNotEquals(l, l2);
    }
}
