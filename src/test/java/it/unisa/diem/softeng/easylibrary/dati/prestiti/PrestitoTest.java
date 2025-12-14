package it.unisa.diem.softeng.easylibrary.dati.prestiti;

import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import java.time.LocalDate;
import java.time.Month;
import org.junit.*;
import static org.junit.Assert.*;

public class PrestitoTest {
    private Prestito p;

    public PrestitoTest() {
    }
    
    @Test
    public void testCostruzionePrestitoValida() {
        Prestito p = new Prestito(new Matricola("0612709671"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.of(2005, Month.MAY, 30));
        assertNotNull(p);                                           // l'oggetto è creato
        assertEquals("0612709671", p.getMatricola().getMatricola());
        assertEquals("0133943038", p.getISBN().getISBN());
        assertEquals(StatoPrestito.ATTIVO, p.getStato());
        assertEquals(LocalDate.of(2005, Month.MAY, 30), p.getDataDiScadenza());
    }
    
    @Before
    public void setUp() {
        p = new Prestito(new Matricola("0612709671"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.of(2005, Month.MAY, 30));
    }

    @Test
    public void testGetMatricola() {
        assertEquals(p.getMatricola(), new Matricola("0612709671"));
    }

    @Test
    public void testGetISBN() {
        assertEquals(p.getISBN(), new ISBN("0133943038"));
    }

    @Test
    public void testGetStato() {
        assertEquals(p.getStato(), StatoPrestito.ATTIVO);
    }

    @Test
    public void testGetDataDiScadenza() {
        assertEquals(p.getDataDiScadenza(), LocalDate.of(2005, Month.MAY, 30));
    }

    @Test
    public void testIsScadutoTrue() {
        assertTrue(p.isScaduto());
    }
    
    @Test
    public void testIsScadutoFalse() {
        Prestito futuro = new Prestito(new Matricola("0612709671"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.now().plusDays(2));
        
        assertFalse(futuro.isScaduto());
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
    public void testSetDataDiScadenza() {
        LocalDate old = p.getDataDiScadenza();
        p.setDataDiScadenza(LocalDate.now());
        assertNotEquals(p.getDataDiScadenza(), old);
    }

    @Test
    public void testCompareTo() {
        assertEquals(p.compareTo(p), 0);
        
        Prestito p_lessData = new Prestito(new Matricola("0612709671"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.of(1925, Month.JANUARY, 1));
        Prestito p_lessMatricola = new Prestito(new Matricola("0612709400"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.of(2005, Month.MAY, 30));
        Prestito p_lessISBN = new Prestito(new Matricola("0612709671"), new ISBN("0133940000"), StatoPrestito.ATTIVO, LocalDate.of(2005, Month.MAY, 30));
        
        assertTrue(p.compareTo(p_lessData) > 0);
        assertTrue(p_lessData.compareTo(p) < 0);
        
        assertTrue(p.compareTo(p_lessMatricola) > 0);
        assertTrue(p_lessMatricola.compareTo(p) < 0);
        
        assertTrue(p.compareTo(p_lessISBN) > 0);
        assertTrue(p_lessISBN.compareTo(p) < 0);
        
        Prestito p_moreData = new Prestito(new Matricola("0612709671"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.of(2025, Month.JUNE, 12));
        Prestito p_moreMatricola = new Prestito(new Matricola("0612709742"), new ISBN("0133943038"), StatoPrestito.ATTIVO, LocalDate.of(2005, Month.MAY, 30));
        Prestito p_moreISBN = new Prestito(new Matricola("0612709671"), new ISBN("0133953422"), StatoPrestito.ATTIVO, LocalDate.of(2005, Month.MAY, 30));
        
        assertTrue(p.compareTo(p_moreData) < 0);
        assertTrue(p_moreData.compareTo(p) > 0);
        
        assertTrue(p.compareTo(p_moreMatricola) < 0);
        assertTrue(p_moreMatricola.compareTo(p) > 0);
        
        assertTrue(p.compareTo(p_moreISBN) < 0);
        assertTrue(p_moreISBN.compareTo(p) > 0);
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
        Prestito p2 = new Prestito(
                new Matricola("0612709671"),
                new ISBN("0133943038"),
                StatoPrestito.ATTIVO,
                LocalDate.of(2005, Month.MAY, 30)
        );

        assertEquals(p, p2);
        assertEquals(p2, p);
    }

    @Test
    public void testEqualsPrestitiDiversi() {
        Prestito p2 = new Prestito(
                new Matricola("0612709671"),
                new ISBN("0133943038"),
                StatoPrestito.RESTITUITO,
                LocalDate.of(2005, Month.MAY, 30)
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
