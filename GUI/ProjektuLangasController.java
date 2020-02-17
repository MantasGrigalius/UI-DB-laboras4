/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.Projektas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

    

public class ProjektuLangasController implements Initializable {

    @FXML
    private Button gryzimoMygtukasP;
    @FXML
    private Button naujoObjektoMygtukas;
    @FXML
    private ChoiceBox projektoLentelesPasirinkimai;
    @FXML
    private TableColumn idStulpelis;
    @FXML
    private TableColumn vardoStulpelis;
    @FXML
    private TableView projektuLentele;
    @FXML
    private Button editMygtukas;
    @FXML
    private TextField filtroLaukas;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vardoStulpelis.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projektas, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Projektas, String> p) {
                return p.getValue().nameProperty();
            }
        });
        idStulpelis.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Projektas, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Projektas, Integer> p) {
                return p.getValue().idColumnProperty().asObject();
            }
        });
        ObservableList<Projektas> teamMembers = FXCollections.observableArrayList(Startas.t.gautiProjektus());
        projektuLentele.setItems(teamMembers);
        projektoLentelesPasirinkimai.setItems(FXCollections.observableArrayList( "Projects", "Tasks"));
        projektoLentelesPasirinkimai.setValue("Projects");
        projektoLentelesPasirinkimai.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
        if((int)number2 == 1){
            FileInputStream fxmlStream = null;
            try {
                FXMLLoader krautuvas = new FXMLLoader();
                String kelias = "src/GUI/UzduociuLangas.fxml";
                fxmlStream = new FileInputStream(kelias);
                AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
                projektoLentelesPasirinkimai.getScene().setRoot(root);
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
    public void gryztiPagrindiniLanganP() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/Titulinis.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        gryzimoMygtukasP.getScene().setRoot(root);
    }

    @FXML
    public void kurtiNaujaObjekta() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/NaujasProjektas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        naujoObjektoMygtukas.getScene().setRoot(root);
    }
    
    @FXML
    public void redaguotiInformacija() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/EditoLangasProject.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        editMygtukas.getScene().setRoot(root);
    }
    
    @FXML
    public void filtruotiLentele() throws Exception {
        
        String filtras = filtroLaukas.getText();
        ArrayList<Projektas> filtravimas = new ArrayList();
            for (Projektas p : Startas.t.gautiProjektus()) {
                if (p.getClass().equals(Projektas.class)) {
                    if(p.getPavadinimas().contains(filtras)){
                       filtravimas.add(p); 
                    }
                    
                }
            }
        ObservableList<Projektas> teamMembers = FXCollections.observableArrayList(filtravimas);
        projektuLentele.setItems(teamMembers);
        
    }
    
}
