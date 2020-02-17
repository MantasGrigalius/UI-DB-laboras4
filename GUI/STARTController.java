/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author bloga
 */
public class STARTController implements Initializable {

    @FXML
    private TextField slapyvardzioLaukas;
    @FXML
    private PasswordField slaptazodzioLaukas;
    @FXML
    private Button prisijungimoMygtukas;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    @FXML
    private void prisijungti() throws FileNotFoundException, IOException {
        String slapyvardis = slapyvardzioLaukas.getText();
        String slaptazodis = slaptazodzioLaukas.getText();
        try {
            Startas.t.prisijungimas(slapyvardis, slaptazodis);
            FXMLLoader krautuvas = new FXMLLoader();
            String kelias = null;
            if(slapyvardis.equals("admin")){
            kelias = "src/GUI/Titulinis.fxml";
            }
            else
            {
            kelias = "src/GUI/Titulinis_1.fxml";   
            }
            FileInputStream fxmlStream = new FileInputStream(kelias);
            AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
            prisijungimoMygtukas.getScene().setRoot(root);
        } catch (Exceptionai e) {
            if (e.getLikeBandymai() <= 0) {
                Platform.exit();
                System.exit(0);
            }
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Information");
            alert.setHeaderText("User with such data not found");
            alert.setContentText("You have - " + e.getLikeBandymai() + "  attempts left!");
            alert.showAndWait();
        }

    }
    
}
