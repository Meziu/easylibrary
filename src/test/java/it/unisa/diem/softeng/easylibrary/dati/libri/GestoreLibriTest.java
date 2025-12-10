package it.unisa.diem.softeng.easylibrary.dati.libri;

import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class GestoreLibriTest {

    private GestoreLibri gestore;

    public GestoreLibriTest() {
    }
    
    public static void aggiungiLibri(GestoreLibri g) {
        // Inserimento volutamente non in ordine
        g.registra(new Libro(
                "1984",
                new ArrayList<>(Arrays.asList(new Autore("George", "Orwell"))),
                Year.of(1948),
                "1443434973",
                1
        ));

        g.registra(new Libro(
                "Il Signore degli Anelli",
                new ArrayList<>(Arrays.asList(new Autore("John Ronald Reuel", "Tolkien"))),
                Year.of(1954),
                "0261102389",
                3
        ));

        g.registra(new Libro(
                "Il Grande Gatsby",
                new ArrayList<>(Arrays.asList(new Autore("Francis Scott", "Fitzgerald"))),
                Year.of(1925),
                "0743273567",
                3
        ));

        g.registra(new Libro(
                "Dune",
                new ArrayList<>(Arrays.asList(new Autore("Frank", "Herbert"))),
                Year.of(1965),
                "0441013597",
                1
        ));

        g.registra(new Libro(
                "La Metamorfosi",
                new ArrayList<>(Arrays.asList(new Autore("Franz", "Kafka"))),
                Year.of(1915),
                "8807900059",
                3
        ));

        g.registra(new Libro(
                "Il Piccolo Principe",
                new ArrayList<>(Arrays.asList(new Autore("Antoine", "de Saint-Exupéry"))),
                Year.of(1943),
                "0156012197",
                1
        ));
    }

    @Before
    public void setUp() {
        List<Autore> autori = new ArrayList<Autore>();
        autori.add(new Autore("George", "Orwell"));

        gestore = new GestoreLibri();

        GestoreLibriTest.aggiungiLibri(gestore);
    }

    @Test
    public void testRegistra() {
        fail();
    }

    @Test
    public void testRimuovi() {
        fail();
    }

    @Test
    public void testModifica() {
        fail();
    }

    @Test
    public void testOttieni() {
        fail();
    }

    @Test
    public void testContiene() {
        fail();
    }

}
