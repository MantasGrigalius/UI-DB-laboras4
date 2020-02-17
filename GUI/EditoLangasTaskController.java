/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.Uzduotys;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author bloga
 */
public class EditoLangasTaskController implements Initializable {

    @FXML
    private Button gryztiAtgalMygtukas;
    @FXML
    private Button trynimoMygtukas;
    @FXML
    private TextField idLaukas;
    @FXML
    private Label senoVardoLaukas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void gryztiProjektuLangan() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/UzduociuLangas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        gryztiAtgalMygtukas.getScene().setRoot(root);

    }

    @FXML
    public void trintiVartotoja() throws Exception {

        String id = idLaukas.getText();
        int pid;
        try {
            pid = Integer.parseInt(id);
            for (Uzduotys u : Startas.t.gautiUzduotis()) {
                if (u.getId() == pid) {
                    Startas.t.uzbaigtiUzduoti(pid);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Lucky");
                    alert.setContentText("Task was completed!");
                    alert.showAndWait();
                    idLaukas.setText("");
                    senoVardoLaukas.setText("");
                    break;
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information");
            alert.setHeaderText("Unlucky");
            alert.setContentText("ID is not valid!");
            alert.showAndWait();
        }
    }

    @FXML
    public void gautiDuomenis() throws Exception {

        String id = idLaukas.getText();
        int pid;
        try {
            pid = Integer.parseInt(id);
            boolean rastasID = false;
            for (Uzduotys u : Startas.t.gautiUzduotis()) {
                if (u.getId() == pid) {
                    rastasID = true;
                    senoVardoLaukas.setText(u.getPavadinimas());
                    break;
                }
            }
            if (!rastasID) {
                throw new Exception();
            }
        } catch (Exception e) {
            senoVardoLaukas.setText("");
        }
    }

}
