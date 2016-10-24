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
    @Override
    public void start(Stage stage) {
        setSize(stage);
        VBox box = new VBox();
        
        box.getChildren().add(createMenu());
        box.getChildren().add(createMiddlePart());
        
        Scene scene = new Scene(box, 300, 250);
        scene.getStylesheets().add("gui/stylesheet.css");
        
        stage.setTitle("Map Builder");
        stage.setScene(scene);
        
        stage.show();
    }
    
    public void setSize(Stage stage) {
        //set Stage boundaries to visible bounds of the main screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        WIDTH = primaryScreenBounds.getWidth();
        HEIGHT = primaryScreenBounds.getHeight();
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
    }
    
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
        menuFile.getItems().addAll(upload, save);
        
        Menu menuEdit = new Menu("Edit");
        Menu menuView = new Menu("View");
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        return menuBar;
    }
    
    public HBox createMiddlePart() {
        HBox box = new HBox();
        DrawPane pane = new DrawPane();
        
        GridPane mapArea = new GridPane();
        mapArea.setPrefSize(WIDTH, HEIGHT*.8);
        mapArea.setAlignment(Pos.CENTER);
        mapArea.getStyleClass().add("grid-background");
        mapArea.getChildren().add(pane.getPane());
        
        box.getChildren().add(mapArea);
        
        return box;
    }
    
    public Button confirmButton() {
        Button butt = new Button();
        butt.setText("Generate");
        butt.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        GridPane.setConstraints(butt, 20, 6);
        return butt;
    }
    
    public GridPane createInputs() {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("inputs");
        GridPane.setConstraints(pane, 0, 0);

        //------------------------Dungeon Name
        Label dunName = new Label("Dungeon Name:");
        pane.add(dunName, 0, 1);
        TextField dunTextField = new TextField();
        pane.add(dunTextField, 1, 1);

        //-------------------------Dungeon Level
        Label level = new Label("Dungeon Level:");
        pane.add(level, 0, 2);
        final ComboBox lvl = new ComboBox(FXCollections.observableArrayList(
                "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
                "16","17","18","19","20"
            ));
        lvl.setValue("1");
        pane.add(lvl, 1, 2);
        
        return pane;
    }
}
