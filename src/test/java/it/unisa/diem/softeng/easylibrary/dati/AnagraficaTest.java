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
    public void testCostruzioneAnagrafica() {
        Anagrafica an = new Anagrafica("Mario", "Rossi");
        assertNotNull(an);
        assertEquals("Mario", an.getNome());
        assertEquals("Rossi", an.getCognome());
    }
    
    @Test
    public void testCostruzioneAnagraficaNomeVuoto() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Anagrafica("", "Rossi")
        );
    }
    
    @Test
    public void testCostruzioneAnagraficaNomenull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Anagrafica(null, "Rossi")
        );
    }
    
    @Test
    public void testCostruzioneAnagraficaCognomeVuoto() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Anagrafica("Mario", "")
        );
    }
    
    @Test
    public void testCostruzioneAnagraficaCognomenull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Anagrafica("Mario", null)
        );
    }
    
    @Test
    public void testCostruzioneAnagraficaNomeCognomeVuoti() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Anagrafica("", "")
        );
    }
    
    @Test
    public void testCostruzioneAnagraficaNomeCognomeNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Anagrafica(null, null)
        );
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
    public void testSetNomeNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> a.setNome(null)
        );
    }
    
    @Test
    public void testSetNomeVuoto() {
        assertThrows(IllegalArgumentException.class, ()
                -> a.setNome("")
        );
    }

    @Test
    public void testSetCognome() {
        a.setCognome("Verdi");
        assertEquals("Verdi", a.getCognome());
    }

    @Test
    public void testSetCognomeNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> a.setCognome(null)
        );
    }
    
    @Test
    public void testSetCognomeVuoto() {
        assertThrows(IllegalArgumentException.class, ()
                -> a.setCognome("")
        );
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