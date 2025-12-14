package it.unisa.diem.softeng.easylibrary.dati.utenti;

import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class UtenteTest {
    private Utente u;
    private Matricola m;
    private IndirizzoEmail e;
    private Prestito nuovo;

    
    public UtenteTest() {
    }

    @Before
    public void setUp() {
        m = new Matricola("0123456789");
        e = new IndirizzoEmail("m.rossi@studenti.unisa.it");
        u = new Utente("Mario", "Rossi", m, e);
        
        nuovo = new Prestito(m, new ISBN("123456789X"), StatoPrestito.ATTIVO, LocalDate.now().plusDays(2));
    }
    
    
    /* 
    *  COSTRUTTORE
    *
    */
    @Test
    public void testCostruzioneUtente() {
        Utente ut = new Utente("Mario", "Rossi", m, e);
        assertNotNull(ut);
        assertEquals("Mario", ut.getAnagrafica().getNome());
        assertEquals("Rossi", ut.getAnagrafica().getCognome());
        assertEquals(m, ut.getMatricola());
        assertEquals(e, ut.getEmail());
        assertTrue(ut.getPrestitiAttivi().isEmpty());
    }
    
    @Test
    public void testCostruzioneUtenteMatricolaNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Utente("Mario", "Rossi", null, e)
        );
    }
    
    @Test
    public void testCostruzioneUtenteEmailNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Utente("Mario", "Rossi", m, null)
        );
    }


    /* 
    *   GET
    * 
    */
    @Test
    public void testGetAnagrafica() {
        assertEquals(u.getAnagrafica().getNome(), "Mario");
        assertEquals(u.getAnagrafica().getCognome(), "Rossi");
    }

    @Test
    public void testGetMatricola() {
        assertEquals(u.getMatricola(), m);
    }

    @Test
    public void testGetEmail() {
        assertEquals(u.getEmail(), e);
    }

    @Test
    public void testGetPrestitiAttivi() {
        Prestito p = new Prestito(m, new ISBN("123456789X"), StatoPrestito.ATTIVO, LocalDate.now().plusDays(2));
                
        u.registraPrestito(nuovo);
        u.registraPrestito(p);
        
        List<Prestito> prestiti = u.getPrestitiAttivi();
        assertEquals(2, prestiti.size());
        assertTrue(prestiti.contains(nuovo));
        assertTrue(prestiti.contains(p));
    }
    
    
    @Test
    public void testGetPrestitiAttiviNonModificabile() {
        assertThrows(UnsupportedOperationException.class, () -> {
            u.getPrestitiAttivi().add(nuovo);
        });
    }

    
    /* 
    *   SET
    * 
    */
    @Test
    public void testSetEmail() {
        IndirizzoEmail nuova = new IndirizzoEmail("email@studenti.unisa.it");
        u.setEmail(nuova);
        assertEquals(nuova, u.getEmail());
    }

    @Test
    public void testSetEmailNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> u.setEmail(null)
        );
    }
    
    /* 
    *   LISTA PRESTITI
    * 
    */
    @Test
    public void testRegistraPrestito() {
        u.registraPrestito(nuovo);

        assertTrue(u.getPrestitiAttivi().contains(nuovo));
        assertEquals(1, u.getPrestitiAttivi().size());
    }
    
    @Test
    public void testRegistraPrestitoNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> u.registraPrestito(null)
        );
    }
    
    @Test
    public void testRegistraPrestitoFinoAlLimite() {
        u.registraPrestito(nuovo);
        u.registraPrestito(nuovo);
        u.registraPrestito(nuovo);

        assertEquals(3, u.getPrestitiAttivi().size());
    }

    @Test
    public void testRegistraPrestitoOltreLimiteLanciaEccezione() {
        u.registraPrestito(nuovo);
        u.registraPrestito(nuovo);
        u.registraPrestito(nuovo);

        //il quarto deve fallire
        assertThrows(LimitePrestitiSuperatoException.class,
            () -> u.registraPrestito(nuovo));
    }

    @Test
    public void testRimuoviPrestitoUnicoElemento() {
        u.registraPrestito(nuovo);
        int prevSize = u.getPrestitiAttivi().size();
        
        u.rimuoviPrestito(nuovo);
        int newSize = u.getPrestitiAttivi().size();

        assertFalse(u.getPrestitiAttivi().contains(nuovo));
        assertEquals(prevSize - 1, u.getPrestitiAttivi().size());
    }

    @Test
    public void testRimuoviPrestitoNonPresente() {
        u.rimuoviPrestito(nuovo);

        assertFalse(u.getPrestitiAttivi().contains(nuovo));
    }
    
    /*
    *   EQUALS
    *
    */
    @Test
    public void equalsRiconosceDueUtentiUguali() {
        Utente u1 = new Utente("Mario", "Rossi", new Matricola("0123456789"), new IndirizzoEmail("m.rossi@studenti.unisa.it"));

        assertEquals(u, u1);
        assertEquals(u1, u); // simmetria
    }

    @Test
    public void equalsRiconosceUtentiDiversiPerNome() {
        Utente u1 = new Utente("Francesco", "Rossi", new Matricola("0123456789"), new IndirizzoEmail("m.rossi@studenti.unisa.it"));

        assertNotEquals(u, u1);
    }

    @Test
    public void equalsRiconosceUtentiDiversiPerCognome() {
        Utente u1 = new Utente("Mario", "Verdi", new Matricola("0123456789"), new IndirizzoEmail("m.rossi@studenti.unisa.it"));

        assertNotEquals(u, u1);
    }
    
    @Test
    public void equalsConNullRitornaFalse() {
        assertNotEquals(u, null);
    }

    @Test
    public void equalsConOggettoDiClasseDiversaRitornaFalse() {
        assertNotEquals(u, "Mario Rossi");  // tipo diverso
    }
    
    
    
    /*
    *  COMPARE TO
    * 
    */
    @Test
    public void testCompareToOrdinaPerNome() {
        Utente u2 = new Utente("Luca", "Rossi",
                new Matricola("1234567890"),
                new IndirizzoEmail("luca@studenti.unisa.it"));

        assertTrue(u2.compareTo(u) < 0);
        assertTrue(u.compareTo(u2) > 0); 
        
        
        Utente u3 = new Utente("Ugo", "Rossi",
                new Matricola("1564567890"),
                new IndirizzoEmail("ugo@studenti.unisa.it"));

        assertTrue(u.compareTo(u3) < 0);
        assertTrue(u3.compareTo(u) > 0); 
    }
    
    
    @Test
    public void testCompareToOrdinaPerCognome() {
        Utente u2 = new Utente("Mario", "Bianchi",
                new Matricola("1234567890"),
                new IndirizzoEmail("mario@studenti.unisa.it"));

        assertTrue(u2.compareTo(u) < 0);
        assertTrue(u.compareTo(u2) > 0);  
        
        
        Utente u3 = new Utente("Mario", "Verdi",
                new Matricola("1234567890"),
                new IndirizzoEmail("mario@studenti.unisa.it"));
        assertTrue(u.compareTo(u3) < 0);
        assertTrue(u3.compareTo(u) > 0);
    }

    @Test
    public void testCompareToOrdinaPerMatricola() {
        Utente u2 = new Utente("Mario", "Rossi",
                new Matricola("9999999999"),
                new IndirizzoEmail("mario@studenti.unisa.it"));

        assertTrue(u.compareTo(u2) < 0);
        assertTrue(u2.compareTo(u) > 0);
        
        
        Utente u3 = new Utente("Mario", "Rossi",
                new Matricola("0000000000"),
                new IndirizzoEmail("mario@studenti.unisa.it"));
        assertTrue(u.compareTo(u3) > 0);
        assertTrue(u3.compareTo(u) < 0);
    }
}