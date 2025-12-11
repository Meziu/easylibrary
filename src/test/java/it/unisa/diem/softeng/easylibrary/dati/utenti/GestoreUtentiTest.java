package it.unisa.diem.softeng.easylibrary.dati.utenti;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class GestoreUtentiTest {
    private GestoreUtenti gestore;

    public GestoreUtentiTest() {
    }
    
    public static void aggiungiUtenti(GestoreUtenti g) {
        g.registra(new Utente("Pippo", "Pluto", new Matricola("1938274018"), new IndirizzoEmail("p.pluto@studenti.unisa.it")));
        g.registra(new Utente("Franco", "Rossi", new Matricola("4729831451"), new IndirizzoEmail("francorossi@studenti.unisa.it")));
        g.registra(new Utente("Mario", "Incredibile", new Matricola("8473261490"), new IndirizzoEmail("marincredibile@studenti.unisa.it")));
        g.registra(new Utente("Spazio", "Ortogonale", new Matricola("8482921412"), new IndirizzoEmail("euclideo@studenti.unisa.it")));
    }
    
    @Before
    public void setUp() {
        gestore = new GestoreUtenti();
        
        // Inserimento volutamente non in ordine
        GestoreUtentiTest.aggiungiUtenti(gestore);
    }

    @Test
    public void testRegistra() {
        Matricola m = new Matricola("9123171233");
        
        gestore.registra(new Utente("Roberto", "Russo", m, new IndirizzoEmail("robertonerusso124@studenti.unisa.it")));
        
        assertTrue(gestore.contiene(m));
    }

    @Test
    public void testRimuovi() {
        Matricola m = new Matricola("1938274018");
        
        Utente u = gestore.ottieni(m);
        
        gestore.rimuovi(u);
        
        assertFalse(gestore.contiene(m));
        assertFalse(gestore.getLista().contains(u));
    }

    @Test
    public void testModifica() {
        Matricola m = new Matricola("8473261490");
        
        gestore.modifica(gestore.ottieni(m), u -> {
            u.getAnagrafica().setCognome("Zelante");
        });
        
        assertEquals(gestore.ottieni(m).getAnagrafica().getCognome(), "Zelante");
        
        // Test per l'ordinamento dopo la modifica (Gestore.modifica()). La lista qui è ordinata correttamente per assicurare anche l'ordine.
        List<Utente> listaOrd = new ArrayList<>();
        listaOrd.add(new Utente("Spazio", "Ortogonale", new Matricola("8482921412"), new IndirizzoEmail("euclideo@studenti.unisa.it")));
        listaOrd.add(new Utente("Pippo", "Pluto", new Matricola("1938274018"), new IndirizzoEmail("p.pluto@studenti.unisa.it")));
        listaOrd.add(new Utente("Franco", "Rossi", new Matricola("4729831451"), new IndirizzoEmail("francorossi@studenti.unisa.it")));
        listaOrd.add(new Utente("Mario", "Zelante", new Matricola("8473261490"), new IndirizzoEmail("marincredibile@studenti.unisa.it")));
        
        assertEquals(gestore.getLista(), listaOrd);
    }

    @Test
    public void testOttieni() {
        Matricola m1 = new Matricola("1938274018");
        Matricola m2 = new Matricola("4729831451");
        Matricola m3 = new Matricola("8473261490");
        Matricola m4 = new Matricola("8482921412");
        
        assertEquals(gestore.ottieni(m1).getMatricola(), m1);
        assertEquals(gestore.ottieni(m2).getMatricola(), m2);
        assertEquals(gestore.ottieni(m3).getMatricola(), m3);
        assertEquals(gestore.ottieni(m4).getMatricola(), m4);
        
        assertEquals(gestore.ottieni(new Matricola("8213742101")), null);
        assertEquals(gestore.ottieni(new Matricola("4210801231")), null);
    }

    @Test
    public void testContiene() {
        assertTrue(gestore.contiene(new Matricola("1938274018")));
        assertTrue(gestore.contiene(new Matricola("4729831451")));
        assertTrue(gestore.contiene(new Matricola("8473261490")));
        assertTrue(gestore.contiene(new Matricola("8482921412")));
        
        assertFalse(gestore.contiene(new Matricola("3719191111")));
        assertFalse(gestore.contiene(new Matricola("4820301134")));
    }

}