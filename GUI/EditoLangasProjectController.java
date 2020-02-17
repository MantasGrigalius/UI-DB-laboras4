/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.Imone;
import DS.Projektas;
import DS.Vartotojas;
import DS.Zmogus;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author bloga
 */
public class EditoLangasProjectController implements Initializable {

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
    private TextField senoVardoLaukas;
    @FXML
    private ListView dalyviuLentele;
    @FXML
    private ChoiceBox naujasDalyvis;
    @FXML
    private Button pridejimoMygtukas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void gryztiProjektuLangan() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/ProjektuLangas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        gryztiAtgalMygtukas.getScene().setRoot(root);
    }

    @FXML
    public void saugotiPakeitimus() throws Exception {

        String nVardas = naujoVardoLaukas.getText();
        String id = idLaukas.getText();
        int pid;
        try {
            pid = Integer.parseInt(id);
            Projektas projektas = (Projektas) Startas.t.gautiProjektoID(pid);
            if (nVardas.trim().equals("")) {
                nVardas = projektas.getPavadinimas();
            }
            if (projektas != null) {
                Startas.t.redaguotiProjektoInfo(pid, nVardas);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Lucky");
                alert.setContentText("Project data was edited!");
                alert.showAndWait();
                idLaukas.setText("");
                naujoVardoLaukas.setText("");
                senoVardoLaukas.setText("");
                dalyviuLentele.setItems(FXCollections.observableArrayList());
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

            Projektas projektas = (Projektas) Startas.t.gautiProjektoID(pid);

            if (projektas != null) {
                Startas.t.trintiProjekta(pid);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Lucky");
                alert.setContentText("Project was deleted!");
                alert.showAndWait();
                idLaukas.setText("");
                naujoVardoLaukas.setText("");
                senoVardoLaukas.setText("");
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

            Projektas projektas = (Projektas) Startas.t.gautiProjektoID(pid);
            if (projektas != null) {
                senoVardoLaukas.setText(projektas.getPavadinimas());

                ObservableList<Vartotojas> dalyviai = FXCollections.observableArrayList(projektas.getDalyviai());
                dalyviuLentele.setCellFactory(param -> new ListCell<Vartotojas>() {
                    @Override
                    protected void updateItem(Vartotojas item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            if (item.getClass().equals(Zmogus.class)) {
                                Zmogus zmogus = (Zmogus) item;
                                setText(zmogus.getVardas() + " " + zmogus.getPavarde());
                            } else if (item.getClass().equals(Imone.class)) {
                                Imone imone = (Imone) item;
                                setText(imone.getPavadinimas());
                            }
                        } else {
                            setText("");
                        }
                    }
                });
                dalyviuLentele.setItems(dalyviai);

                ArrayList<Vartotojas> visi = Startas.t.gautiVartotojus();

                visi.removeIf(vv -> !vv.isAktyvus());
                for (Vartotojas v : projektas.getDalyviai()) {

                    for (Vartotojas v2 : visi) {

                        if (v2.getId() == v.getId()&& v.getClass().equals(v2.getClass())) {

                            visi.remove(v2);
                            break;
                        }
                    }
                }

                naujasDalyvis.setConverter(new StringConverter<Vartotojas>() {
                    @Override
                    public String toString(Vartotojas v) {
                        return v.getAtpazinimoDuomenys();
                    }

                    @Override
                    public Vartotojas fromString(String string) {
                        return null;
                    }
                });
                naujasDalyvis.setItems(FXCollections.observableArrayList(visi));

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            senoVardoLaukas.setText("");
            dalyviuLentele.setItems(FXCollections.observableArrayList());
        }
    }
    
    @FXML
    public void pridetiProjektan(){
    
        String id = idLaukas.getText();
        int pid;
        try {
            pid = Integer.parseInt(id);

            Projektas projektas = (Projektas) Startas.t.gautiProjektoID(pid);

            if (projektas != null) {
                Vartotojas naujasDalyvis = (Vartotojas) this.naujasDalyvis.getValue();
                if (naujasDalyvis != null){
                    if(naujasDalyvis.getClass().equals(Zmogus.class)){
                        Startas.t.pridetiZmoguProjektan(pid, naujasDalyvis.getId());
                    }
                    else if(naujasDalyvis.getClass().equals(Imone.class)){
                        Startas.t.pridetiImoneProjektan(pid, naujasDalyvis.getId());
                    }
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Lucky");
                    alert.setContentText("User was added to project!");
                    
                    projektas = (Projektas) Startas.t.gautiProjektoID(pid);
                    ObservableList<Vartotojas> dalyviai = FXCollections.observableArrayList(projektas.getDalyviai());
                    dalyviuLentele.setCellFactory(param -> new ListCell<Vartotojas>() {
                        @Override
                        protected void updateItem(Vartotojas item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty && item != null) {
                                if (item.getClass().equals(Zmogus.class)) {
                                    Zmogus zmogus = (Zmogus) item;
                                    setText(zmogus.getVardas() + " " + zmogus.getPavarde());
                                } else if (item.getClass().equals(Imone.class)) {
                                    Imone imone = (Imone) item;
                                    setText(imone.getPavadinimas());
                                }
                            } else {
                                setText("");
                            }
                        }
                    });
                    dalyviuLentele.setItems(dalyviai);

                    ArrayList<Vartotojas> visi = Startas.t.gautiVartotojus();

                    visi.removeIf(vv -> !vv.isAktyvus());
                    for (Vartotojas v : projektas.getDalyviai()) {
                        for (Vartotojas v2 : visi) {
                            if (v2.getId() == v.getId() && v.getClass().equals(v2.getClass())) {

                                visi.remove(v2);
                                break;
                            }
                        }
                    }
                    this.naujasDalyvis.setItems(FXCollections.observableArrayList(visi));
                    
                    alert.showAndWait();
                }
                else{
                    throw new Exception();
                }
                
            }
            else{
                throw new Exception();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information");
            alert.setHeaderText("Unlucky");
            alert.setContentText("There is no project with such ID or user was not selected!");
            alert.showAndWait();
        }
        
    }

}
