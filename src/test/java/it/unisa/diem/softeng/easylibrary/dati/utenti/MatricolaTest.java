package it.unisa.diem.softeng.easylibrary.dati.utenti;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatricolaTest {

    public MatricolaTest() {
    }
    
       // ----------------------------------------------------------------------
    //  COSTRUTTORE
    // ----------------------------------------------------------------------

    @Test
    public void costruzioneMatricolaValida() {
        Matricola m = new Matricola("0123456789");
        assertEquals("0123456789", m.getMatricola());
    }

    @Test
    public void costruzioneMatricolaTroppoCortaLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola("12345"));
    }

    @Test
    public void costruzioneMatricolaTroppoLungaLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola("1234567890123"));
    }

    @Test
    public void costruzioneMatricolaConCaratteriNonNumericiLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola("12345A7890"));
    }

    @Test
    public void costruzioneMatricolaConStringaNullLanciaEccezione() {
        assertThrows(MatricolaInvalidaException.class,
                () -> new Matricola(null));
    }

    // ----------------------------------------------------------------------
    //  METODO VERIFICA
    // ----------------------------------------------------------------------

    @Test
    public void verificaRitornaTruePerMatricolaValida() {
        assertTrue(Matricola.verifica("0123456789"));
    }

    @Test
    public void verificaRitornaFalsePerLunghezzaErrata() {
        assertFalse(Matricola.verifica("1234"));
        assertFalse(Matricola.verifica("1234567890123"));
    }

    @Test
    public void verificaRitornaFalsePerCaratteriNonNumerici() {
        assertFalse(Matricola.verifica("1234X67890"));
    }

    @Test
    public void verificaNullRitornaFalse() {
        assertFalse(Matricola.verifica(null));
    }

    // ----------------------------------------------------------------------
    //  EQUALS E HASHCODE
    // ----------------------------------------------------------------------

    @Test
    public void equalsRiconosceMatricoleUguali() {
        Matricola m1 = new Matricola("0123456789");
        Matricola m2 = new Matricola("0123456789");

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    public void equalsDistingueMatricoleDiverse() {
        Matricola m1 = new Matricola("0123456789");
        Matricola m2 = new Matricola("9876543210");

        assertNotEquals(m1, m2);
    }

    @Test
    public void equalsConOggettoDiClasseDiversaRitornaFalse() {
        Matricola m = new Matricola("0123456789");
        assertNotEquals(m, "0123456789");
    }

    @Test
    public void equalsConNullRitornaFalse() {
        Matricola m = new Matricola("0123456789");
        assertNotEquals(m, null);
    }

    // ----------------------------------------------------------------------
    //  COMPARETO
    // ----------------------------------------------------------------------
    @Test
    public void compareToFunzionaCorrettamente() {
        Matricola m1 = new Matricola("0000000001");
        Matricola m2 = new Matricola("0000000002");

        assertTrue(m1.compareTo(m2) < 0);
        assertTrue(m2.compareTo(m1) > 0);
    }

    @Test
    public void compareToMatricoleUgualiRitornaZero() {
        Matricola m1 = new Matricola("1111111111");
        Matricola m2 = new Matricola("1111111111");

        assertEquals(0, m1.compareTo(m2));
    }

    
    
    
    
    
    
    

    @Test
    public void testGetMatricola() {
    }

    @Test
    public void testVerifica() {
    }

    @Test
    public void testCompareTo() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void testEquals() {
    }

}