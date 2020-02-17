package GUI;

import DS.Projektas;
import DS.Uzduotys;
import DS.Vartotojas;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TitulinisController_1 implements Initializable {

    @FXML
    private Label vardoVieta;
    @FXML
    private Button atsijungimoMygtukas;
    @FXML
    private ListView projektuLentele;
    @FXML
    private ListView uzduociuLentele;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Vartotojas v = Startas.t.gautiPrisijungusiVartotoja();
        vardoVieta.setText(v.getAtpazinimoDuomenys());

        ObservableList<Projektas> projektai = FXCollections.observableArrayList(v.getProjektai());
        projektuLentele.setCellFactory(param -> new ListCell<Projektas>() {
            @Override
            protected void updateItem(Projektas item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    setText(item.getPavadinimas());
                } else {
                    setText("");
                }
            }
        });
        projektuLentele.setItems(projektai);

        uzduociuLentele.setCellFactory(param -> new ListCell<Uzduotys>() {
            @Override
            protected void updateItem(Uzduotys item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if (item.getProjektas() == null) {
                        setText("   " + item.getPavadinimas());
                    } else {
                        setText(item.getPavadinimas());
                    }
                    
                    setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent click) {
                            if (click.getClickCount() == 2) {

                                try {
                                    FXMLLoader krautuvas = new FXMLLoader();
                                    String kelias = "src/GUI/Titulinis_1_1.fxml";
                                    FileInputStream fxmlStream = new FileInputStream(kelias);
                                    AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);
                                    Stage stage = new Stage();
                                    stage.setTitle("Commenting");
                                    stage.setScene(new Scene(root));
                                    
                                    TitulinisController_1_1 controller = krautuvas.<TitulinisController_1_1>getController();
                                    controller.nustatytiUzduoti(item, this::atnaujintiUzduotis);    
                                    
                                    stage.show();
                                } catch (IOException ex) {
                                    System.out.println("Nepavyko atidaryti komentarų!" + ex.getMessage());
                                }
                            }
                        }

                        private Void atnaujintiUzduotis(Void t) {
                            ArrayList<Uzduotys> visi = Startas.t.gautiUzduotis();
                            ArrayList<Uzduotys> uzduotys = new ArrayList();

                            for (Uzduotys u : visi) {
                                if (u.getProjektas() != null && !u.isPabaigta() && u.getProjektas().getId() == ((Projektas) projektuLentele.getSelectionModel().getSelectedItem()).getId()) {
                                    uzduotys.add(u);
                                    for (Uzduotys subU : u.getSubUzduotys()) {
                                        if (!subU.isPabaigta()){
                                            uzduotys.add(subU);
                                        }
                                    }
                                }
                            }

                            uzduociuLentele.setItems(FXCollections.observableArrayList(uzduotys));
                            return null;
                        }
                    });
                } else {
                    setText("");
                }
            }
        });

        projektuLentele.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Projektas>() {

            @Override
            public void changed(ObservableValue<? extends Projektas> observable, Projektas oldValue, Projektas newValue) {

                ArrayList<Uzduotys> visi = Startas.t.gautiUzduotis();
                ArrayList<Uzduotys> uzduotys = new ArrayList();

                for (Uzduotys u : visi) {
                    if (u.getProjektas() != null && !u.isPabaigta() && u.getProjektas().getId() == newValue.getId()) {
                        uzduotys.add(u);
                        for (Uzduotys subU : u.getSubUzduotys()) {
                            if (!subU.isPabaigta()){
                                uzduotys.add(subU);
                            }
                        }
                    }
                }

                uzduociuLentele.setItems(FXCollections.observableArrayList(uzduotys));
            }
        });
    }

    @FXML
    public void atsijungti() throws Exception {
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
