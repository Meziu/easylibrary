/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homepage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author marco
 */
public class UtentiController extends GenericController {

    @FXML
    private Label labelRicerca;
    @FXML
    private VBox vbox;
    @FXML
    private Label labelNome;
    @FXML
    private TextField textFieldNome;
    @FXML
    private Label labelMatricola;
    @FXML
    private TextField textFieldMatricola;
    @FXML
    private VBox vboxNome;
    @FXML
    private VBox vboxMatricola;

    public void initialize(URL url, ResourceBundle rb) {
        if (vbox != null) {

            double hBoxHeightPercentage = 0.05;
            double fontSizePercentage = 0.03;

            // 1) RIDIMENSIONAMENTO VERTICALE DELLE HBOX (Altezza)
            vboxNome.prefHeightProperty().bind(vbox.heightProperty().multiply(hBoxHeightPercentage));
            vboxMatricola.prefHeightProperty().bind(vbox.heightProperty().multiply(hBoxHeightPercentage));

            // 2) RIDIMENSIONAMENTO DEL FONT con il nuovo metodo
            // Ridimensiona il font della Label principale
            bindFontSize(labelRicerca, vbox.heightProperty(), 0.05);
            labelRicerca.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            // Ridimensiona il font dei campi Nome
            bindFontSize(labelNome, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldNome, vbox.heightProperty(), fontSizePercentage);

            // Ridimensiona il font dei campi Matricola
            bindFontSize(labelMatricola, vbox.heightProperty(), fontSizePercentage);
            bindFontSize(textFieldMatricola, vbox.heightProperty(), fontSizePercentage);
        }
    }

    private void bindFontSize(Node node, ReadOnlyDoubleProperty containerHeightProperty, double percentage) {
        StringBinding fontSizeBinding = containerHeightProperty
                .multiply(percentage)
                .asString("-fx-font-size: %.0fpx;");

        node.styleProperty().bind(fontSizeBinding);
    }
}
