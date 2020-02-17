/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NaujasZmogusController implements Initializable {

    @FXML
    private Button gryztiAtgalV;
    @FXML
    public ChoiceBox projektoKurimoPasirinkimai;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public Button sukurimoMygtukas;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        projektoKurimoPasirinkimai.setItems(FXCollections.observableArrayList("Person", "Company"));
        projektoKurimoPasirinkimai.setValue("Person");
        projektoKurimoPasirinkimai.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if ((int) number2 == 1) {
                    FileInputStream fxmlStream = null;
                    try {
                        FXMLLoader krautuvas = new FXMLLoader();
                        String kelias = "src/GUI/NaujaKompanija.fxml";
                        fxmlStream = new FileInputStream(kelias);
                        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
                        projektoKurimoPasirinkimai.getScene().setRoot(root);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(NaujasProjektas.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(NaujasProjektas.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fxmlStream.close();
                        } catch (IOException ex) {
                            Logger.getLogger(NaujasProjektas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }

    @FXML
    public void gryztiVartotojuLangan() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/VartotojuLangas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        gryztiAtgalV.getScene().setRoot(root);
    }

    @FXML
    public void sukurtiVartotoja() {
        String username = this.username.getText();
        String password = this.password.getText();
        String name = this.name.getText();
        String surname = this.surname.getText();
        if (Startas.t.registruotiZmogu(username, password, name, surname)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Lucky");
            alert.setContentText("You created new user!");
            alert.showAndWait();
            this.username.setText("");
            this.password.setText("");
            this.name.setText("");
            this.surname.setText("");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText("Unlucky");
            alert.setContentText("User with such data already exists!");
            alert.showAndWait();
        }
        

    }

}
