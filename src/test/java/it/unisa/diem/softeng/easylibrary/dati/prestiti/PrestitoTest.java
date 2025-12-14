package it.unisa.diem.softeng.easylibrary.dati.prestiti;

import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import java.time.LocalDate;
import java.time.Month;
import org.junit.*;
import static org.junit.Assert.*;

public class PrestitoTest {

    private Prestito p;
    private Matricola m;
    private ISBN isbn;

    public PrestitoTest() {
    }

    @Before
    public void setUp() {
        m = new Matricola("0612709400");
        isbn = new ISBN("123456789X");

        p = new Prestito(m, isbn, StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));
    }

    @Test
    public void testCostruzionePrestitoValida() {
        Prestito p = new Prestito(new Matricola("0612709671"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));
        assertNotNull(p);                                           // l'oggetto è creato
        assertEquals("0612709671", p.getMatricola().getMatricola());
        assertEquals("0133943038", p.getISBN().getISBN());
        assertEquals(StatoPrestito.ATTIVO, p.getStato());
        assertEquals(LocalDate.now().plusDays(30), p.getDataDiScadenza());
    }

    @Test
    public void testCostruzioneStatoPrestitoNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Prestito(m, isbn, null, LocalDate.now().plusDays(30))
        );
    }

    @Test
    public void testCostruzioneDataScadenzaPassata() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Prestito(m, isbn, StatoPrestito.ATTIVO, LocalDate.of(2005, Month.MAY, 30))
        );
    }

    @Test
    public void testGetMatricola() {
        assertEquals(p.getMatricola(), m);
    }

    @Test
    public void testGetISBN() {
        assertEquals(p.getISBN(), isbn);
    }

    @Test
    public void testGetStato() {
        assertEquals(p.getStato(), StatoPrestito.ATTIVO);
    }

    @Test
    public void testGetDataDiScadenza() {
        assertEquals(p.getDataDiScadenza(), LocalDate.now().plusDays(30));
    }

    @Test
    public void testSetStato() {
        p.setStato(StatoPrestito.RESTITUITO);
        assertEquals(p.getStato(), StatoPrestito.RESTITUITO);

        p.setStato(StatoPrestito.ATTIVO);
        assertEquals(p.getStato(), StatoPrestito.ATTIVO);

        p.setStato(StatoPrestito.ATTIVO);
        assertEquals(p.getStato(), StatoPrestito.ATTIVO);
    }

    @Test
    public void testSetStatoNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> p.setStato(null)
        );
    }

    @Test
    public void testSetDataDiScadenza() {
        LocalDate old = p.getDataDiScadenza();
        p.setDataDiScadenza(LocalDate.now().plusDays(40));
        assertNotEquals(p.getDataDiScadenza(), old);
    }

    @Test
    public void testSetDataScadenzaPassata() {
        assertThrows(IllegalArgumentException.class, ()
                -> p.setDataDiScadenza(LocalDate.of(2005, Month.MAY, 30))
        );
    }

    /*
    *  COMPARE TO
    * 
     */
    @Test
    public void testCompareToFunzionaCorrettamenteData() {
        Prestito p1 = new Prestito(m, isbn, StatoPrestito.ATTIVO, LocalDate.now().plusDays(20));

        assertTrue(p.compareTo(p1) > 0);
        assertTrue(p1.compareTo(p) < 0);

        Prestito p2 = new Prestito(m, isbn, StatoPrestito.ATTIVO, LocalDate.now().plusDays(40));

        assertTrue(p.compareTo(p2) < 0);
        assertTrue(p2.compareTo(p) > 0);
    }

    @Test
    public void testCompareToFunzionaCorrettamenteMatricola() {
        Prestito p1 = new Prestito(new Matricola("0612709300"), isbn, StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));

        assertTrue(p.compareTo(p1) > 0);
        assertTrue(p1.compareTo(p) < 0);

        Prestito p2 = new Prestito(new Matricola("0612709742"), isbn, StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));

        assertTrue(p.compareTo(p2) < 0);
        assertTrue(p2.compareTo(p) > 0);
    }

    @Test
    public void testCompareToFunzionaCorrettamenteISBN() {
        Prestito p1 = new Prestito(m, new ISBN("023456789X"), StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));

        assertTrue(p.compareTo(p1) > 0);
        assertTrue(p1.compareTo(p) < 0);

        Prestito p2 = new Prestito(m, new ISBN("9133953422"), StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));

        assertTrue(p.compareTo(p2) < 0);
        assertTrue(p2.compareTo(p) > 0);
    }

    @Test
    public void testCompareToRiconoscePrestitiUguali() {
        Prestito p2 = new Prestito(m, isbn, StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));
        assertEquals(0, p.compareTo(p2));

        assertEquals(p.compareTo(p), 0);
    }

    @Test
    public void testCompareToOrdinaPerStato() {
        Prestito restituito = new Prestito(
                p.getMatricola(),
                p.getISBN(),
                StatoPrestito.RESTITUITO,
                p.getDataDiScadenza()
        );

        assertTrue(p.compareTo(restituito) < 0);   // ATTIVO prima
        assertTrue(restituito.compareTo(p) > 0);
    }

    @Test
    public void testEqualsPrestitiUguali() {
        Prestito p2 = new Prestito(m, isbn, StatoPrestito.ATTIVO, LocalDate.now().plusDays(30));

        assertEquals(p, p2);
        assertEquals(p2, p);
    }

    @Test
    public void testEqualsPrestitiDiversi() {
        Prestito p2 = new Prestito(
                new Matricola("0612709671"),
                new ISBN("0133943038"),
                StatoPrestito.RESTITUITO,
                LocalDate.now().plusDays(20)
        );
        assertNotEquals(p, p2);
    }

    @Test
    public void testEqualsConNull() {
        assertNotEquals(p, null);
    }

    @Test
    public void testEqualsConTipoDiverso() {
        assertNotEquals(p, "prestito");
    }

}
