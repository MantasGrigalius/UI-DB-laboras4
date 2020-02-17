/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.Zmogus;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author bloga
 */
public class EditoLangasUserController implements Initializable {

    @FXML
    private Button gryztiAtgalMygtukas;
    @FXML
    private Button saugojimoMygtukas;
    @FXML
    private Button trynimoMygtukas;
    @FXML
    private TextField idLaukas;
    @FXML
    private TextField naujoVardoLaukas;
    @FXML
    private TextField naujoPavardesLaukas;
    @FXML
    private TextField senoVardoLaukas;
    @FXML
    private TextField senoPavardesLaukas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void gryztiVartotojuLangan() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/VartotojuLangas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        gryztiAtgalMygtukas.getScene().setRoot(root);
    }

    @FXML
    public void saugotiPakeitimus() throws Exception {

        String nVardas = naujoVardoLaukas.getText();
        String nPavarde = naujoPavardesLaukas.getText();
        String id = idLaukas.getText();
        int pid;
        try {
            pid = Integer.parseInt(id);
            Zmogus zmogus = Startas.t.gautiZmoguId(pid);
            if (nVardas.trim().equals("")) {
                nVardas = zmogus.getVardas();
            }
            if (nPavarde.trim().equals("")) {
                nPavarde = zmogus.getPavarde();
            }
            if (zmogus != null) {
                Startas.t.redaguotiZmogausInfo(pid, nVardas, nPavarde);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Lucky");
                alert.setContentText("User data was edited!");
                alert.showAndWait();
                idLaukas.setText("");
                naujoVardoLaukas.setText("");
                naujoPavardesLaukas.setText("");
                senoVardoLaukas.setText("");
                senoPavardesLaukas.setText("");

            }
        } catch (Exception e) {

        }

    }

    @FXML
    public void trintiVartotoja() throws Exception {

        String id = idLaukas.getText();
        int pid;
        try {
            pid = Integer.parseInt(id);

            Zmogus zmogus = Startas.t.gautiZmoguId(pid);

            if (zmogus != null) {
                if(!zmogus.isAktyvus()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information");
                alert.setHeaderText("Unlucky");
                alert.setContentText("User with such ID already deleted!");
                alert.showAndWait();
                }
                else{
                Startas.t.uzsaldytiZmogu(pid);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Lucky");
                alert.setContentText("User was deleted!");
                alert.showAndWait();
                idLaukas.setText("");
                naujoVardoLaukas.setText("");
                naujoPavardesLaukas.setText("");
                senoVardoLaukas.setText("");
                senoPavardesLaukas.setText("");
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

            Zmogus zmogus = Startas.t.gautiZmoguId(pid);
            if (zmogus != null) {
                senoVardoLaukas.setText(zmogus.getVardas());
                senoPavardesLaukas.setText(zmogus.getPavarde());
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            senoVardoLaukas.setText("");
            senoPavardesLaukas.setText("");
        }

    }

}
