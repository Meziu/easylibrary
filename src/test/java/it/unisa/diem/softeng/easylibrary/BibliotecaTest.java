package it.unisa.diem.softeng.easylibrary;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.archivio.Indicizzabile;
import it.unisa.diem.softeng.easylibrary.dati.libri.Autore;
import it.unisa.diem.softeng.easylibrary.dati.libri.GestoreLibri;
import it.unisa.diem.softeng.easylibrary.dati.libri.ISBN;
import it.unisa.diem.softeng.easylibrary.dati.libri.Libro;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.GestorePrestiti;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.Prestito;
import it.unisa.diem.softeng.easylibrary.dati.prestiti.StatoPrestito;
import it.unisa.diem.softeng.easylibrary.dati.utenti.GestoreUtenti;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class BibliotecaTest {
    private Biblioteca b;
    private static final String TEST_FILE = "biblioteca_test.bin";

    private Matricola m;
    private Utente u;
    private IndirizzoEmail e;
    private Libro l;
    private Prestito p;

    public BibliotecaTest() {
    }

    @Before
    public void setUp() {
        b = new Biblioteca();

        m = new Matricola("0123456789");
        e = new IndirizzoEmail("m.rossi@studenti.unisa.it");
        u = new Utente("Mario", "Rossi", m, e);

        List<Autore> autori = new ArrayList<>();
        autori.add(new Autore("George", "Orwell"));
        l = new Libro("1984", autori, 1949, "123456789X", 3);

        p = new Prestito(m, l.getISBN(), StatoPrestito.ATTIVO, LocalDate.now().plusDays(2));
    }

    @After
    public void tearDown() {
        File f = new File(TEST_FILE);
        if (f.exists()) {
            f.delete();
        }
    }


    @Test
    public void testCostruttoreInizializzaArchivi() {
        assertNotNull(b.getArchivioUtenti());
        assertNotNull(b.getArchivioLibri());
        assertNotNull(b.getArchivioPrestiti());
    }

    /**
     * Test per verificare che Biblioteca() carichi archivi vuoti
     */
    @Test
    public void testArchiviVuotiInNuovaBiblioteca() {
        assertTrue(b.getArchivioUtenti().getLista().isEmpty());
        assertTrue(b.getArchivioLibri().getLista().isEmpty());
        assertTrue(b.getArchivioPrestiti().getLista().isEmpty());
    }


    @Test
    public void testArchivioPrestiti() {
        Archiviabile<Prestito> archivioP = b.getArchivioPrestiti();
        b.getArchivioUtenti().registra(u);
        b.getArchivioLibri().registra(l);
        archivioP.registra(p);

        assertTrue(archivioP.getLista().contains(p));
    }

    @Test
    public void testGetterArchivioUtenti() {
        Indicizzabile<Matricola, Utente> archivioU = b.getArchivioUtenti();
        archivioU.registra(u);

        assertTrue(archivioU.contiene(m));
    }

    @Test
    public void testArchivioLibri() {
        Indicizzabile<ISBN, Libro> archivioL = b.getArchivioLibri();
        archivioL.registra(l);

        assertTrue(archivioL.contiene(l.getISBN()));
    }


    @Test
    public void testGetterClasseCorrettaUtenti() {
        assertTrue(b.getArchivioUtenti() instanceof Indicizzabile);
    }

    @Test
    public void testGetterClasseCorrettaLibri() {
        assertTrue(b.getArchivioLibri() instanceof Indicizzabile);
    }

    @Test
    public void testGetterClasseCorrettaPrestiti() {
        assertTrue(b.getArchivioPrestiti() instanceof Archiviabile);
    }

    @Test
    public void testSalvaFile() {
        b.getArchivioUtenti().registra(u);
        b.getArchivioLibri().registra(l);
        b.getArchivioPrestiti().registra(p);

        try {
            b.salvaFile(TEST_FILE);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testCaricaFile() {
        b.getArchivioUtenti().registra(u);
        b.getArchivioLibri().registra(l);
        b.getArchivioPrestiti().registra(p);

        Biblioteca caricata = null;
        try {
            b.salvaFile(TEST_FILE);
            caricata = Biblioteca.caricaFile(TEST_FILE);
        } catch (Exception e) {
            fail(e.toString());
        }
        assertNotNull(caricata);

        assertTrue(caricata.getArchivioUtenti().contiene(m));
        assertTrue(caricata.getArchivioLibri().contiene(l.getISBN()));
        assertEquals(1, caricata.getArchivioPrestiti().getLista().size());
    }

    @Test
    public void testCaricaFileInesistente() {
        assertThrows(IOException.class, () -> { Biblioteca.caricaFile("file_che_non_esiste.dat"); });
    }
}
