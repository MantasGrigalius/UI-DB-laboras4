/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.Uzduotys;
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

/**
 * FXML Controller class
 *
 * @author bloga
 */
public class NaujaSubUzduotis implements Initializable {

    @FXML
    public Button gryztiAtgalP;
    @FXML
    public ChoiceBox projektoKurimoPasirinkimai;
    @FXML
    public TextField name;
    @FXML
    public Button sukurimoMygtukas;
    @FXML
    public TextField taskId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        projektoKurimoPasirinkimai.setItems(FXCollections.observableArrayList("Project", "Task", "Sub Task"));
        projektoKurimoPasirinkimai.setValue("Sub Task");
        projektoKurimoPasirinkimai.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
        if((int)number2 == 0){
            FileInputStream fxmlStream = null;
            try {
                FXMLLoader krautuvas = new FXMLLoader();
                String kelias = "src/GUI/NaujasProjektas.fxml";
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
        if((int)number2 == 1){
            FileInputStream fxmlStream = null;
            try {
                FXMLLoader krautuvas = new FXMLLoader();
                String kelias = "src/GUI/NaujaUzduotis.fxml";
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
    public void gryztiProjektuLangan() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/UzduociuLangas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        gryztiAtgalP.getScene().setRoot(root);
    }    
    
    @FXML
    public void sukurtiObjekta() {
        String name = this.name.getText();
        String id = taskId.getText();
        int pid;
        try {
            pid = Integer.parseInt(id);
            boolean rastasID = false;
            for(Uzduotys u : Startas.t.gautiUzduotis()){
                if(u.getId()==pid){
                    rastasID = true;
                    Startas.t.pridetiSubUzduoti(pid, name);
                    break;
                }
            }
            if (rastasID) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Lucky");
                alert.setContentText("You created new task!");
                alert.showAndWait();
                this.name.setText("");
                taskId.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information");
                alert.setHeaderText("Unlucky");
                alert.setContentText("Project with such ID doesn't exist!");
                alert.showAndWait();
            }

        } catch (Exception e) {

        }
    }
    
}
