package it.unisa.diem.softeng.easylibrary.dati.utenti;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class MatricolaTest {
    private Matricola m;
    
    public MatricolaTest() {
    }
    
    @Before
    public void setUp() {
       m = new Matricola("0123456789");
    }

    /* 
    *  COSTRUTTORE
    *
    */
    @Test
    public void testCostruzioneMatricolaValida() {
        Matricola m = new Matricola("0123456789");
        assertEquals("0123456789", m.getMatricola());
    }

    @Test
    public void testCostruzioneMatricolaNull() {
        assertThrows(MatricolaInvalidaException.class, ()
                -> new Matricola(null)
        );
    }
    
    @Test
    public void testCostruzioneMatricolaVuotaLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class, ()
                -> new Matricola("")
        );
    }
    
    @Test
    public void testCostruzioneMatricolaCortaLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola("12345"));
    }

    @Test
    public void testCostruzioneMatricolaLungaLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola("1234567890123"));
    }

    @Test
    public void testCostruzioneMatricolaCaratteriNonNumericiLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola("12345A7890"));
    }

    @Test
    public void testCostruzioneMatricolaStringaNullLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola(null));
    }

    
    /* 
    *   GET MATRICOLA
    * 
    */
    @Test
    public void testGetMatricola() {
        assertEquals(m.getMatricola(), "0123456789");
    }
    
    
    
    /*
    *   VERIFICA
    * 
    */
    @Test
    public void testVerificaMatricolaValida() {
        assertTrue(Matricola.verifica("0123456789"));
    }

    @Test
    public void testVerificaLunghezzaErrata() {
        assertFalse(Matricola.verifica("1234"));
        assertFalse(Matricola.verifica("1234567890123"));
    }

    @Test
    public void testVerificaCaratteriNonNumerici() {
        assertFalse(Matricola.verifica("1234X67890"));
    }

    @Test
    public void testVerificaNull() {
        assertFalse(Matricola.verifica(null));
    }

    /*
    *  EQUALS
    */

    @Test
    public void testEqualsRiconosceMatricoleUguali() {
        Matricola m1 = new Matricola("0123456789");

        assertEquals(m, m1);
        assertEquals(m.hashCode(), m1.hashCode());
    }

    @Test
    public void testEqualsDistingueMatricoleDiverse() {
        Matricola m1 = new Matricola("9876543210");

        assertNotEquals(m, m1);
    }

    @Test
    public void testEqualsConOggettoDiClasseDiversa() {
        Matricola m = new Matricola("0123456789");
        assertNotEquals(m, "0123456789");
    }

    @Test
    public void testEqualsConNull() {
        Matricola m = new Matricola("0123456789");
        assertNotEquals(m, null);
    }

    /*
    *  COMPARE TO
    */
    @Test
    public void testCompareToFunzionaCorrettamente() {
        Matricola m1 = new Matricola("0000000001");
        Matricola m2 = new Matricola("0000000002");

        assertTrue(m1.compareTo(m2) < 0);
        assertTrue(m2.compareTo(m1) > 0);
    }

    @Test
    public void testCompareToMatricoleUguali() {
        Matricola m1 = new Matricola("0123456789");

        assertEquals(0, m.compareTo(m1));
    }

}