/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DS.ToDoList;
import java.sql.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author bloga
 */
public class Startas extends Application {

    public static ToDoList t;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException {

        t = new ToDoList();
        try {
            t.connect();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Neprisijunge prie DB!");
        }

        FXMLLoader krautuvas = new FXMLLoader();
        String kelias = "src/GUI/START.fxml";
        FileInputStream fxmlStream = new FileInputStream(kelias);
        AnchorPane root = (AnchorPane) krautuvas.load(fxmlStream);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Admin control");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop(){
        try {
            t.disconnect();
        } catch (Exception ex) {
            System.out.print("Nepavyko atsijungti nuo DB!");
        }
    }
}
