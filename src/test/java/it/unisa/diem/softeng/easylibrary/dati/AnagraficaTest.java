package it.unisa.diem.softeng.easylibrary.dati;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class AnagraficaTest {
    private Anagrafica a;

    public AnagraficaTest() {
    }
    
    @Before
    public void setUp() {
        a = new Anagrafica("Mario", "Rossi");
    }

    /* 
    *  COSTRUTTORE
    *
    */
    @Test
    public void testCostruzioneUtente() {
        Anagrafica an = new Anagrafica("Mario", "Rossi");
        assertNotNull(an);
        assertEquals("Mario", an.getNome());
        assertEquals("Rossi", an.getCognome());
    }
    
    /* 
    *   GET
    * 
    */
    @Test
    public void testGetNome() {
        assertEquals(a.getNome(), "Mario");
    }

    @Test
    public void testGetCognome() {
        assertEquals(a.getCognome(), "Rossi");
    }

    
    /* 
    *   SET
    * 
    */
    @Test
    public void testSetNome() {
        a.setNome("Francesco");
        assertEquals("Francesco", a.getNome());
    }

    @Test
    public void testSetCognome() {
        a.setCognome("Verdi");
        assertEquals("Verdi", a.getCognome());
    }

    
    
    /*
    *   EQUALS
    *
    */
    @Test
    public void equalsRiconosceDueAnagraficheUguali() {
        Anagrafica a1 = new Anagrafica("Mario", "Rossi");

        assertEquals(a, a1);
        assertEquals(a1, a); // simmetria
    }

    @Test
    public void equalsRiconosceAnagraficheDiversePerNome() {
        Anagrafica a1 = new Anagrafica("Francesco", "Rossi");

        assertNotEquals(a, a1);
    }

    @Test
    public void equalsRiconosceAnagraficheDiversePerCognome() {
        Anagrafica a1 = new Anagrafica("Mario", "Verdi");

        assertNotEquals(a, a1);
    }
    
    @Test
    public void equalsConNullRitornaFalse() {
        assertNotEquals(a, null);
    }

    @Test
    public void equalsConOggettoDiClasseDiversaRitornaFalse() {
        assertNotEquals(a, 5);  // tipo diverso
    }
    
    
    
    /*
    *  COMPARE TO
    * 
    */
    @Test
    public void testCompareToOrdinaPerNome() {
        Anagrafica a1 = new Anagrafica("Luca", "Rossi");

        assertTrue(a1.compareTo(a) < 0);
        assertTrue(a.compareTo(a1) > 0); 
        
        Anagrafica a2 = new Anagrafica("Ugo", "Rossi");

        assertTrue(a.compareTo(a2) < 0);
        assertTrue(a2.compareTo(a) > 0); 
    }
    
    @Test
    public void testCompareToOrdinaPerCognome() {
        Anagrafica a1 = new Anagrafica("Mario", "Bianchi");
        
        assertTrue(a1.compareTo(a) < 0);
        assertTrue(a.compareTo(a1) > 0);  
        
        
        Anagrafica a2 = new Anagrafica("Mario", "Verdi");
        assertTrue(a.compareTo(a2) < 0);
        assertTrue(a2.compareTo(a) > 0);
    }
}