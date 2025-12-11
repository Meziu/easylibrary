package it.unisa.diem.softeng.easylibrary.dati.libri;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ISBNTest {
    private ISBN isbn;

    public ISBNTest() {
    }

    /* ----------------------------------------------------------------------
    *  COSTRUTTORE
    * ----------------------------------------------------------------------
    */
    @Test
    public void testCostruzioneISBNValidoSenzaTrattini() {
        ISBN isbn = new ISBN("123456789X");
        assertEquals("123456789X", isbn.getISBN());
    }

    @Test
    public void testCostruzioneISBNValidoConTrattini() {
        ISBN isbn = new ISBN("123-4-567-89X");
        assertEquals("123456789X", isbn.getISBN());   // Trattini rimossi
    }

    @Test
    public void testCostruzioneISBNInvalidoLanciaEccezione() {
        assertThrows(ISBNInvalidoException.class,
                () -> new ISBN("ABCDE12345")); // Non numerico
    }

    @Test
    public void testCostruzioneISBNLunghezzaErrataLanciaEccezione() {
        assertThrows(ISBNInvalidoException.class,
                () -> new ISBN("12345"));  // Troppo corto
    }
    
    
    /* ----------------------------------------------------------------------
    *   GET ISBN
    * ----------------------------------------------------------------------
    */
    @Test
    public void testGetISBN() {
        assertEquals(new ISBN("123456789X").getISBN(), "123456789X");
    }
    
    
    /* ----------------------------------------------------------------------
    *   VERIFICA ISBN-10
    * ----------------------------------------------------------------------
    */
    @Test
    public void testVerificaISBN10Valido() {
        assertTrue(ISBN.verifica("123456789X"));
    }

    @Test
    public void testVerificaISBN10ConUltimaCifraNumerica() {
        assertTrue(ISBN.verifica("0439420897"));
    }

    @Test
    public void testVerificaISBN10NonNumerico() {
        assertFalse(ISBN.verifica("12345A789X")); // A non ammessa nei primi 9
    }

    @Test
    public void testVerificaISBN10LunghezzaErrata() {
        assertFalse(ISBN.verifica("123456789")); // 9 caratteri = falso
    }

    @Test
    public void testVerificaISBN10UltimoCarattereNonValido() {
        assertFalse(ISBN.verifica("123456789Z")); // Z non ammessa
    }
    

    
    /* ----------------------------------------------------------------------
    *  VERIFICA ISBN-13
    * ----------------------------------------------------------------------
    */
    @Test
    public void testVerificaISBN13Valido() {
        assertTrue(ISBN.verifica("9780306406157"));
    }

    @Test
    public void testVerificaISBN13NonNumerico() {
        assertFalse(ISBN.verifica("97803064061A7")); // A non ammessa
    }

    @Test
    public void testVerificaISBN13LunghezzaErrata() {
        assertFalse(ISBN.verifica("9780306406")); // Troppo corto
    }
    


    
    
    /* ----------------------------------------------------------------------
    *  COMPARE TO
    * ----------------------------------------------------------------------
    */
    @Test
    public void testCompareToFunzionaCorrettamente() {
        ISBN i1 = new ISBN("0000000001");
        ISBN i2 = new ISBN("0000000002");

        assertTrue(i1.compareTo(i2) < 0);
        assertTrue(i2.compareTo(i1) > 0);
    }

    @Test
    public void testCompareToISBNUguali() {
        ISBN i1 = new ISBN("123456789X");
        ISBN i2 = new ISBN("123456789X");

        assertEquals(0, i1.compareTo(i2));
    }
    
    
  
    @Test
    public void testCompareToRiconosceISBNUguali() {
        ISBN i1 = new ISBN("123456789X");
        ISBN i2 = new ISBN("123456789X");

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    public void testCompareToDistingueISBNDiversi() {
        ISBN i1 = new ISBN("123456789X");
        ISBN i2 = new ISBN("1234567890");

        assertNotEquals(i1, i2);
    }
}