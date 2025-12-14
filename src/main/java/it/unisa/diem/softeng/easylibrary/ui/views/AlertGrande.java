package it.unisa.diem.softeng.easylibrary.ui.views;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class AlertGrande {

    public static void mostraAlertErrore(String errore) {
        System.err.println(errore);
        Alert a = new Alert(Alert.AlertType.ERROR);

        a.getDialogPane().setContent(new Label(errore));

        a.showAndWait();
    }
}
