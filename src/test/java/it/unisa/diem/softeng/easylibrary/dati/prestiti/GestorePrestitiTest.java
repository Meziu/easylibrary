package it.unisa.diem.softeng.easylibrary.dati.prestiti;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import it.unisa.diem.softeng.easylibrary.dati.libri.GestoreLibri;
import it.unisa.diem.softeng.easylibrary.dati.libri.GestoreLibriTest;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.utenti.GestoreUtenti;
import it.unisa.diem.softeng.easylibrary.dati.utenti.GestoreUtentiTest;
import it.unisa.diem.softeng.easylibrary.dati.utenti.LimitePrestitiSuperatoException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import org.junit.*;
import static org.junit.Assert.*;

public class GestorePrestitiTest {
    GestoreUtenti utenti;
    GestoreLibri libri;
    GestorePrestiti prestiti;
    LocalDate stamp;
    
    public GestorePrestitiTest() {
    }
    
    @Before
    public void setUp() {
        utenti = new GestoreUtenti();
        libri = new GestoreLibri();
        prestiti = new GestorePrestiti(utenti, libri);
        
        GestoreUtentiTest.aggiungiUtenti(utenti);
        GestoreLibriTest.aggiungiLibri(libri);
        
        stamp = LocalDate.now().plusDays(10);
        
        prestiti.registra(new Prestito(new Matricola("8482921412"), new ISBN("0261102389"), StatoPrestito.ATTIVO, stamp.plusDays(10)));
        prestiti.registra(new Prestito(new Matricola("1938274018"), new ISBN("0441013597"), StatoPrestito.ATTIVO, stamp.plusDays(-10)));
        prestiti.registra(new Prestito(new Matricola("4729831451"), new ISBN("1443434973"), StatoPrestito.ATTIVO, stamp.plusDays(15)));
        prestiti.registra(new Prestito(new Matricola("8482921412"), new ISBN("0156012197"), StatoPrestito.ATTIVO, stamp.plusDays(0)));
        prestiti.registra(new Prestito(new Matricola("8473261490"), new ISBN("8807900059"), StatoPrestito.ATTIVO, stamp.plusDays(1)));
    }

    @Test
    public void testRimuovi() {
        ISBN l = new ISBN("0261102389");
        int copieDisponibiliPrima = libri.ottieni(l).getCopieDisponibili();
        
        Prestito p1 = new Prestito(new Matricola("8482921412"), l, StatoPrestito.ATTIVO, stamp.plusDays(10));
        prestiti.rimuovi(p1);
        
        p1.setStato(StatoPrestito.RESTITUITO);
        assertTrue(prestiti.getLista().contains(p1));
        assertEquals(prestiti.getLista().get(Collections.binarySearch(prestiti.getLista(), p1)).getStato(), StatoPrestito.RESTITUITO);
        assertEquals(libri.ottieni(l).getCopieDisponibili(), copieDisponibiliPrima + 1);
    }

    @Test
    public void testRegistra() {
        // Prestito valido
        Prestito p1 = new Prestito(new Matricola("8482921412"), new ISBN("0743273567"), StatoPrestito.ATTIVO, stamp.plusDays(10));
        prestiti.registra(p1);
        
        assertTrue(prestiti.getLista().contains(p1));
        
        // Prestito per libro senza altre copie
        Prestito p2 = new Prestito(new Matricola("4729831451"), new ISBN("1443434973"), StatoPrestito.ATTIVO, stamp.plusDays(2));
        assertThrows(NessunaCopiaDisponibileException.class, () -> { prestiti.registra(p2); });
        
        // Prestito per utente con già 3 prestiti
        Prestito p3 = new Prestito(new Matricola("8482921412"), new ISBN("8807900059"), StatoPrestito.ATTIVO, stamp.plusDays(2));
        assertThrows(LimitePrestitiSuperatoException.class, () -> { prestiti.registra(p3); });
        
        // Prestito per utente inesistente
        Prestito p4 = new Prestito(new Matricola("2198892193"), new ISBN("0261102389"), StatoPrestito.ATTIVO, stamp.plusDays(20));
        assertThrows(ValoreNonPresenteException.class, () -> { prestiti.registra(p4); });
        
        // Prestito per libro inesistente
        Prestito p5 = new Prestito(new Matricola("4729831451"), new ISBN("9780451526342"), StatoPrestito.ATTIVO, stamp.plusDays(20));
        assertThrows(ValoreNonPresenteException.class, () -> { prestiti.registra(p5); });
    }

}