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

    /*
    *  COSTRUTTORE
    *
    */
    @Test
    public void testCostruzioneAutoreValida() {
        assertNotNull(a);                                           // l'oggetto è creato
        assertEquals("Mario", a.getAnagrafica().getNome());          // nome corretto
        assertEquals("Rossi", a.getAnagrafica().getCognome());       // cognome corretto
    }


    /*
    *   GET ANAGRAFICA
    *
    */
    @Test
    public void testGetAnagrafica() {
        assertEquals(a.getAnagrafica().getNome(), "Mario");
        assertEquals(a.getAnagrafica().getCognome(), "Rossi");
    }


    /*
    *   EQUALS
    *
    */
    @Test
    public void equalsRiconosceDueAutoriUguali() {
        Autore a1 = new Autore("Mario", "Rossi");

        assertEquals(a, a1);
        assertEquals(a1, a); // simmetria
    }

    @Test
    public void equalsRiconosceAutoriDiversiPerNome() {
        Autore a1 = new Autore("Luigi", "Rossi");

        assertNotEquals(a, a1);
    }

    @Test
    public void equalsRiconosceAutoriDiversiPerCognome() {
        Autore a1 = new Autore("Mario", "Verdi");

        assertNotEquals(a, a1);
    }

    @Test
    public void equalsConNullRitornaFalse() {
        assertNotEquals(a, null);
    }

    @Test
    public void equalsConOggettoDiClasseDiversaRitornaFalse() {
        assertNotEquals(a, "Mario Rossi");  // tipo diverso
    }
}
