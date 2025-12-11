package it.unisa.diem.softeng.easylibrary.dati.libri;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AutoreTest {
    private Autore a;

    public AutoreTest() {
    }

    @Before
    public void setUp() {
       a = new Autore("Mario", "Rossi");
    }
    @Test
    public void testCostruzioneAutoreValida() {
        assertNotNull(a);                                           // l'oggetto è creato
        assertEquals("Mario", a.getAnagrafica().getNome());          // nome corretto
        assertEquals("Rossi", a.getAnagrafica().getCognome());       // cognome corretto
    }
    
    @Test
    public void testGetAnagrafica() {
        assertEquals(a.getAnagrafica().getNome(), "Mario");
        assertEquals(a.getAnagrafica().getCognome(), "Rossi");
    }
}