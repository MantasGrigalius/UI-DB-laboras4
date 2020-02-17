/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.*;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author bloga
 */
public class TitulinisController implements Initializable {

    @FXML
    private Pane paspaustasVartotojai;
    @FXML
    private Pane paspaustasProjektai;
    @FXML
    private Label zmoniuSkaicius;
    @FXML
    private Label imoniuSkaicius;
    @FXML
    private PieChart pyragelioDiagrama;
    @FXML
    private BarChart stulpelineDiagrama;
    @FXML
    private Label projektuSkaicius;
    @FXML
    private Label uzduociuSkaicius;
    @FXML
    private Label vardoVieta;
    @FXML
    private Button atsijungimoMygtukas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int zmoniuSkaicius = 0;
        int imoniuSkaicius = 0;
        int nzskaiciavimas = 0;
        int niskaiciavimas = 0;
        int atskaiciavimas = 0;
        int ntskaiciavimas = 0;
        int projektuSkaicius = Startas.t.gautiProjektus().size();
        
        for (Vartotojas v : Startas.t.gautiVartotojus()) {
            if (v.getClass().equals(Zmogus.class)) {
                if (v.isAktyvus()) {
                    zmoniuSkaicius++;
                } else {
                    nzskaiciavimas++;
                }
            } else if (v.getClass().equals(Imone.class)) {
                if (v.isAktyvus()) {
                    imoniuSkaicius++;
                } else {
                    niskaiciavimas++;
                }

            }
        }
        
        for (Uzduotys u : Startas.t.gautiUzduotis()) {
                if (u.isPabaigta()) {
                    ntskaiciavimas++;
                } else {
                    
                    atskaiciavimas++;
                }
            } 
        
        this.zmoniuSkaicius.setText(String.valueOf(zmoniuSkaicius + nzskaiciavimas));
        this.imoniuSkaicius.setText(String.valueOf(imoniuSkaicius + niskaiciavimas));
        this.projektuSkaicius.setText(String.valueOf(projektuSkaicius));
        this.uzduociuSkaicius.setText(String.valueOf(atskaiciavimas + ntskaiciavimas));

        
        vardoVieta.setText(Startas.t.gautiPrisijungusiVartotoja().getAtpazinimoDuomenys());
        pyragelioDiagrama.setData(FXCollections.observableArrayList(
            new PieChart.Data("Active persons", zmoniuSkaicius),
            new PieChart.Data("Inactive persons", nzskaiciavimas),
            new PieChart.Data("Active companies", imoniuSkaicius),
            new PieChart.Data("Inactive companies", niskaiciavimas)));
        pyragelioDiagrama.setLabelLineLength(4);
        
        Series series1 = new Series();
        series1.setName("2003");       
        series1.getData().add(new Data("Active tasks", atskaiciavimas));
        series1.getData().add(new Data("Completed tasks", ntskaiciavimas));
        stulpelineDiagrama.getData().add(series1);
    }

    @FXML
    public void eitiVartotojuLangan() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/VartotojuLangas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        paspaustasVartotojai.getScene().setRoot(root);
    }

    @FXML
    public void eitiProjektuLangan() throws Exception {

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/ProjektuLangas.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        paspaustasProjektai.getScene().setRoot(root);
    }

    @FXML
    public void atsijungti() throws Exception{
        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/START.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
        atsijungimoMygtukas.getScene().setRoot(root);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("You successfully disconnected from system!");
        alert.showAndWait();
    }
}
