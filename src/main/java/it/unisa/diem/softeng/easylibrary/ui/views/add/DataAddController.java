package it.unisa.diem.softeng.easylibrary.ui.views.add;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public abstract class DataAddController<F> implements Initializable {

    @FXML
    private BorderPane registrazioneContent;
    @FXML
    private Button confermaButton;
    @FXML
    private Label addTitle;

    protected BooleanProperty inserimentoValido;

    protected F formController;
    private String aggiuntaForm;
    private String viewTitle;

    protected DataAddController(String viewTitle, F formController, String aggiuntaForm) {
        this.formController = formController;
        this.aggiuntaForm = aggiuntaForm;
        this.inserimentoValido = new SimpleBooleanProperty(false);
        this.viewTitle = viewTitle;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addTitle.setText("Registra Nuovo " + this.viewTitle);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.aggiuntaForm));
        loader.setController(this.formController);

        try {
            registrazioneContent.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        confermaButton.disableProperty().bind(this.inserimentoValido.not());
    }

    @FXML
    protected abstract void confermaInserimento();

    @FXML
    protected void annullaInserimento() {
        chiudiFinestra();
    }

    public void chiudiFinestra() {
        Stage s = (Stage) (registrazioneContent.getScene().getWindow());
        s.close();
    }
}
