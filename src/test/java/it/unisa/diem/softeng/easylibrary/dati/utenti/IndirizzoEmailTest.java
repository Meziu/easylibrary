package it.unisa.diem.softeng.easylibrary.dati.utenti;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class IndirizzoEmailTest {

    private IndirizzoEmail email;

    public IndirizzoEmailTest() {
    }

    @Before
    public void setUp() {
        email = new IndirizzoEmail("m.rossi@studenti.unisa.it");
    }

    /* 
    *  COSTRUTTORE
    *
     */
    @Test
    public void testCostruzioneIndirizzoEmailValido() {
        IndirizzoEmail e = new IndirizzoEmail("m.rossi@studenti.unisa.it");
        assertEquals("m.rossi@studenti.unisa.it", e.getIndirizzoEmail());
    }

    public void testCostruzioneEmailNull() {
        assertThrows(IndirizzoEmailInvalidoException.class, ()
                -> new IndirizzoEmail(null)
        );
    }

    @Test
    public void testCostruzioneEmailVuota() {
        assertThrows(IndirizzoEmailInvalidoException.class, ()
                -> new IndirizzoEmail("")
        );
    }

    /*
    *   VERIFICA
    * 
     */
    @Test
    public void testVerificaDominioValido() {
        assertTrue(IndirizzoEmail.verifica("m.rossi@studenti.unisa.it"));
    }

    @Test
    public void testVerificaDominioNonValido() {
        assertFalse(IndirizzoEmail.verifica("m.rossi@unisa.it"));
    }

    @Test
    public void testVerificaParteLocaleNonValida() {
        assertFalse(IndirizzoEmail.verifica("ros@&#si@studenti.unisa.it"));
    }

    @Test
    public void testVerificaParteLocaleVuota() {
        assertFalse(IndirizzoEmail.verifica("@studenti.unisa.it"));
    }

    /* 
    *   GET INDIRIZZO EMAIL
    * 
     */
    @Test
    public void testGetIndirizzoEmail() {
        assertEquals(email.getIndirizzoEmail(), "m.rossi@studenti.unisa.it");
    }

    /* 
    *   SET INDIRIZZO EMAIL
    * 
     */
    @Test
    public void testSetIndirizzoEmail() {
        email.setIndirizzoEmail("m.verdi@studenti.unisa.it");
        assertEquals("m.verdi@studenti.unisa.it", email.getIndirizzoEmail());
    }

    @Test
    public void testSetEmailNull() {
        assertThrows(IndirizzoEmailInvalidoException.class, ()
                -> email.setIndirizzoEmail(null)
        );
    }

    @Test
    public void testSetEmailVuota() {
        assertThrows(IndirizzoEmailInvalidoException.class, ()
                -> email.setIndirizzoEmail("")
        );
    }

}
