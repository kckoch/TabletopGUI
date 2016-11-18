/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Screen;

/**
 *
 * @author Kyra Koch
 */
public class GUI extends Application {
    private double WIDTH;
    private double HEIGHT;
    
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        setSize(stage);
        //the main application is a vbox, which basically tackles the gui in horizontal layers
        VBox box = new VBox();
        //middlepane is where the interactive map and room info is stored
        MiddlePane middle = new MiddlePane(WIDTH, HEIGHT);
        
        box.getChildren().add(createMenu());    //adds the menu bar at the top
        box.getChildren().add(middle.getBox()); //adds the map and info pane
        
        Scene scene = new Scene(box, 300, 250);
        scene.getStylesheets().add("gui/stylesheet.css");
        
        stage.setTitle("Map Builder");
        stage.setScene(scene);
        
        stage.show();
    }
    
    public void setSize(Stage stage) {
        //set Stage boundaries to visible bounds of the main screen, is relative to computer running the program
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        WIDTH = primaryScreenBounds.getWidth();
        HEIGHT = primaryScreenBounds.getHeight();
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
    }
    
    /*
    makes the menubar for the application
    Curremt Layout:
    File
        Upload New
        Save
    Edit
    View
    */
    public MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        
        Menu menuFile = new Menu("File");
        MenuItem upload = new MenuItem("Upload New");
        upload.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        upload.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //fill this in
            }
        });
        MenuItem save = new MenuItem("Save");
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //fill this in
            }
        });
        menuFile.getItems().addAll(upload, save); //links the save and upload new under the File tab
        
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        return menuBar;
    }
}
