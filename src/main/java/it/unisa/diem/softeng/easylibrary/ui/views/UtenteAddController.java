package it.unisa.diem.softeng.easylibrary.ui.views;

import it.unisa.diem.softeng.easylibrary.archivio.Archiviabile;
import it.unisa.diem.softeng.easylibrary.archivio.ValoreGiàPresenteException;
import it.unisa.diem.softeng.easylibrary.dati.utenti.IndirizzoEmail;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Matricola;
import it.unisa.diem.softeng.easylibrary.dati.utenti.Utente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;

public class UtenteAddController extends DataAddController<UtenteAddForm> {
    private Archiviabile<Utente> utenti;

    public UtenteAddController(Archiviabile<Utente> utenti) {
        super("Utente", new UtenteAddForm(), "/res/AddUtentiForm.fxml");

        this.utenti = utenti;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        inserimentoValido.bind(Bindings.createBooleanBinding(
            () -> {
                return !this.formController.nomeField.getText().isEmpty() &&
                        !this.formController.cognomeField.getText().isEmpty() &&
                        Matricola.verifica(this.formController.matricolaField.getText()) &&
                        IndirizzoEmail.verifica(this.formController.emailField.getText());
            },
            this.formController.nomeField.textProperty(),
            this.formController.cognomeField.textProperty(),
            this.formController.matricolaField.textProperty(),
            this.formController.emailField.textProperty()
        ));
    }

    @Override
    protected void confermaInserimento() {
        Utente newUtente = new Utente(this.formController.nomeField.getText(),
                                        this.formController.cognomeField.getText(),
                                        new Matricola(this.formController.matricolaField.getText()),
                                        new IndirizzoEmail(this.formController.emailField.getText())
        );
        
        try {
            utenti.registra(newUtente);
            chiudiFinestra();
        } catch (ValoreGiàPresenteException e) {
            String error = "Utente con Matricola \"" + newUtente.getMatricola().getMatricola() + "\" già presente nell'archivio";
            System.err.println(error);
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
        }
    }
}
