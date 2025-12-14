package it.unisa.diem.softeng.easylibrary.dati.libri;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ISBNTest {
    private ISBN isbn;

    public ISBNTest() {
    }

    @Before
    public void setUp() {
       isbn = new ISBN("123456789X");
    }
    
    /* 
    *  COSTRUTTORE
    *
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
    
    @Test
    public void testCostruzioneISBNNullo() {
        assertThrows(ISBNInvalidoException.class, ()
                -> new ISBN(null)
        );
    }
    
    @Test
    public void testCostruzioneISBNVuoto() {
        assertThrows(ISBNInvalidoException.class, ()
                -> new ISBN("")
        );
    }
    
    /* 
    *   GET ISBN
    * 
    */
    @Test
    public void testGetISBN() {
        assertEquals(isbn.getISBN(), "123456789X");
    }
    
    
    /*
    *   VERIFICA ISBN-10
    * 
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
    

    
    /* 
    *  VERIFICA ISBN-13
    *
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
        assertFalse(ISBN.verifica("123456789")); // 9 caratteri = falso
    }
    
        
    /*
    *   EQUALS
    *
    */
    @Test
    public void equalsRiconosceDueISBNUguali() {
        ISBN i = new ISBN("123456789X");

        assertEquals(isbn, i);
        assertEquals(i, isbn); // simmetria
    }

    @Test
    public void equalsDistingueISBNDiversi() {
        ISBN i = new ISBN("883456789X");

        assertNotEquals(isbn, i);
    }
    
    @Test
    public void equalsConNull() {
        assertNotEquals(isbn, null);
    }

    @Test
    public void equalsConOggettoDiClasseDiversa() {
        assertNotEquals(isbn, 5);  // tipo diverso
    }
    
    
    /* 
    *  COMPARE TO
    * 
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

        assertEquals(0, isbn.compareTo(i1));
    }
    
  
    @Test
    public void testCompareToRiconosceISBNUguali() {
        ISBN i1 = new ISBN("123456789X");

        assertEquals(isbn, i1);
        assertEquals(isbn.hashCode(), i1.hashCode());
    }

    @Test
    public void testCompareToDistingueISBNDiversi() {
        ISBN i1 = new ISBN("1234567890");

        assertNotEquals(isbn, i1);
    }
}