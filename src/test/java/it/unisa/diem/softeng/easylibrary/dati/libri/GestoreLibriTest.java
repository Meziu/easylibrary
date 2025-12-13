package it.unisa.diem.softeng.easylibrary.dati.libri;

import it.unisa.diem.softeng.easylibrary.archivio.ValoreNonPresenteException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
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
                1948,
                new ISBN("1443434973"),
                1
        ));

        g.registra(new Libro(
                "Il Signore degli Anelli",
                new ArrayList<>(Arrays.asList(new Autore("John Ronald Reuel", "Tolkien"))),
                1954,
                new ISBN("0261102389"),
                3
        ));

        g.registra(new Libro(
                "Il Grande Gatsby",
                new ArrayList<>(Arrays.asList(new Autore("Francis Scott", "Fitzgerald"))),
                1925,
                new ISBN("0743273567"),
                3
        ));

        g.registra(new Libro(
                "Dune",
                new ArrayList<>(Arrays.asList(new Autore("Frank", "Herbert"))),
                1965,
                new ISBN("0441013597"),
                1
        ));

        g.registra(new Libro(
                "La Metamorfosi",
                new ArrayList<>(Arrays.asList(new Autore("Franz", "Kafka"))),
                1915,
                new ISBN("8807900059"),
                3
        ));

        g.registra(new Libro(
                "Il Piccolo Principe",
                new ArrayList<>(Arrays.asList(new Autore("Antoine", "de Saint-Exupéry"))),
                1943,
                new ISBN("0156012197"),
                1
        ));
    }

    @Before
    public void setUp() {
        gestore = new GestoreLibri();

        GestoreLibriTest.aggiungiLibri(gestore);
    }

    @Test
    public void testRegistra() {
        Libro l = new Libro(
                "To Kill a Mockingbird",
                new ArrayList<>(Arrays.asList(new Autore("Harper", "Lee"))),
                2002,
                new ISBN("0060935464"),
                1
        );
        
        gestore.registra(l);
        
        assertTrue(gestore.contiene(l.getISBN()));
        assertTrue(gestore.getLista().contains(l));
    }

    @Test
    public void testRimuovi() {
        Libro l = gestore.ottieni(new ISBN("0441013597"));
        
        gestore.rimuovi(l);
        
        assertFalse(gestore.contiene(l.getISBN()));
        assertFalse(gestore.getLista().contains(l));
        
        Libro l2 = new Libro(
                "To Kill a Mockingbird",
                new ArrayList<>(Arrays.asList(new Autore("Harper", "Lee"))),
                2002,
                new ISBN("0060935464"),
                1
        );
        
        assertThrows(ValoreNonPresenteException.class, () -> {
            gestore.rimuovi(l2);
        });
    }

    @Test
    public void testModifica() {
        Libro l = gestore.ottieni(new ISBN("8807900059"));
        
        gestore.modifica(l, libro -> {
            libro.setTitolo("DecisamenteUnTitoloErroneo");
        });
        
        assertEquals(gestore.ottieni(l.getISBN()).getTitolo(), "DecisamenteUnTitoloErroneo");
        
        assertThrows(ValoreNonPresenteException.class, () -> {gestore.modifica(new Libro(
                "To Kill a Mockingbird",
                new ArrayList<>(Arrays.asList(new Autore("Harper", "Lee"))),
                2002,
                new ISBN("0060935464"),
                1
        ), libro -> {});});
    }

    @Test
    public void testOttieni() {
        ISBN i1 = new ISBN("0743273567"); // esistente
        ISBN i2 = new ISBN("0060935464"); // inesistente
        
        assertEquals(gestore.ottieni(i1).getISBN(), i1);
        
        assertEquals(gestore.ottieni(i2), null);
    }

    @Test
    public void testContiene() {
        ISBN i1 = new ISBN("0743273567"); // esistente
        ISBN i2 = new ISBN("0060935464"); // inesistente
        
        assertTrue(gestore.contiene(i1));
        
        assertFalse(gestore.contiene(i2));
    }

}
