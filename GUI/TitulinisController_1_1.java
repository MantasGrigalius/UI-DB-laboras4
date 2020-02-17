package GUI;

import DS.Komentaras;
import DS.Uzduotys;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TitulinisController_1_1 implements Initializable {
    
    private Uzduotys uzduotis;
    private Function<Void, Void> func;
    
    @FXML
    private Label pavadinimoVieta;
    @FXML
    private Button uzbaigimoMygtukas;
    @FXML
    private Button komentaroMygtukas;
    @FXML
    private ListView komentaruLentele;
    @FXML
    private TextArea komentaruLaukas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }
    
    public void nustatytiUzduoti(Uzduotys uzduotis, Function<Void, Void> func){
        
        this.uzduotis = uzduotis;
        this.func = func;
        
        pavadinimoVieta.setText(uzduotis.getPavadinimas());
        
        ObservableList<Komentaras> komentarai = FXCollections.observableArrayList(Startas.t.gautiUzduotiesKomentarus(uzduotis.getId()));
        komentaruLentele.setCellFactory(param -> new ListCell<Komentaras>() {
            @Override
            protected void updateItem(Komentaras item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item.getTekstas());
                } else {
                    setText("");
                }
            }
        });
        komentaruLentele.setItems(komentarai);
        
    }
    
    @FXML
    public void sukurtiKomentara(){
        
        String komentaras = komentaruLaukas.getText();
        if (!komentaras.trim().equals("")) {
            if (Startas.t.pridetiKomentara(komentaras, uzduotis.getId())){
                ObservableList<Komentaras> komentarai = FXCollections.observableArrayList(Startas.t.gautiUzduotiesKomentarus(uzduotis.getId()));
                komentaruLentele.setItems(komentarai);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Lucky");
                alert.setContentText("Comment was added!");
                alert.showAndWait();
                komentaruLaukas.setText("");
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Unlucky");
                alert.setContentText("Comment was not added!");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    public void uzbaigtiUzduoti() throws Exception{
        if (Startas.t.uzbaigtiUzduoti(uzduotis.getId())){
            func.apply(null);
            ((Stage) uzbaigimoMygtukas.getScene().getWindow()).close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Lucky");
            alert.setContentText("Task was completed!");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Unlucky");
            alert.setContentText("Task was not completed!");
            alert.showAndWait();
        }
    }
    
}
