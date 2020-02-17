/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.Imone;
import DS.Vartotojas;

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

/**
 * FXML Controller class
 *
 * @author bloga
 */
public class VartotojuLangas_2Controller implements Initializable {

    @FXML
    private Button gryzimoMygtukasV;
    @FXML
    private Button kurtiNaujaVartotoja;
    @FXML
    private ChoiceBox projektoLentelesPasirinkimai;
    @FXML
    private TableView vartotojuLentele;
    @FXML
    private TableColumn idStulpelis;
    @FXML
    private TableColumn vardoStulpelis;
    @FXML
    private TableColumn aktyvumoStulpelis;
    @FXML
    private Button editMygtukas;
    @FXML
    private TextField filtroLaukas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vardoStulpelis.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Imone, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Imone, String> p) {
                return p.getValue().nameProperty();
            }
        });
        idStulpelis.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Imone, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Imone, Integer> p) {
                return p.getValue().idColumnProperty().asObject();
            }
        });
        aktyvumoStulpelis.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Imone, Boolean>, ObservableValue<Boolean>>() {
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Imone, Boolean> p) {
                return p.getValue().activeColumnProperty().asObject();
            }
        });
        ArrayList<Imone> filtravimas = new ArrayList();
            for (Vartotojas v : Startas.t.gautiVartotojus()) {
                if (v.getClass().equals(Imone.class)) {
                    Imone i =(Imone)v;
                    filtravimas.add(i);
                }
            }
        ObservableList<Imone> teamMembers = FXCollections.observableArrayList(filtravimas);
        vartotojuLentele.setItems(teamMembers);
        projektoLentelesPasirinkimai.setItems(FXCollections.observableArrayList( "Persons", "Companies"));
        projektoLentelesPasirinkimai.setValue("Companies");
        projektoLentelesPasirinkimai.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
        if((int)number2 == 0){
            FileInputStream fxmlStream = null;
            try {
                FXMLLoader krautuvas = new FXMLLoader();
                String kelias = "src/GUI/VartotojuLangas.fxml";
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
    public void gryztiPagrindiniLanganV() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/Titulinis.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        gryzimoMygtukasV.getScene().setRoot(root);
    }
    
    @FXML
    public void kurtiNaujaVartotoja() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/NaujaKompanija.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        kurtiNaujaVartotoja.getScene().setRoot(root);
    }
    
    @FXML
    public void redaguotiInformacija() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/EditoLangasCompany.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        editMygtukas.getScene().setRoot(root);
    }
    
    @FXML
    public void filtruotiLentele() throws Exception {
        
        String filtras = filtroLaukas.getText();
        ArrayList<Imone> filtravimas = new ArrayList();
            for (Vartotojas v : Startas.t.gautiVartotojus()) {
                if (v.getClass().equals(Imone.class)) {
                    Imone i =(Imone)v;
                    if(i.getPavadinimas().contains(filtras)){
                       filtravimas.add(i); 
                    }
                    
                }
            }
        ObservableList<Imone> teamMembers = FXCollections.observableArrayList(filtravimas);
        vartotojuLentele.setItems(teamMembers);
        
    }
    
}
