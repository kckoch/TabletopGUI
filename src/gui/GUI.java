/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
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
        Pane input = createInputs();
        GridPane grid = new GridPane();
        DrawGrid draw = new DrawGrid(40, 50, 800, 1000);
        Scene scene = new Scene(grid, 300, 250);
        scene.getStylesheets().add("gui/stylesheet.css");
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        grid.getColumnConstraints().addAll(column1, column2);
        
        GridPane rhs = new GridPane();
        rhs.setAlignment(Pos.CENTER);
        rhs.getStyleClass().add("grid-background");
        rhs.getChildren().add(draw);
        
        grid.add(input, 0, 0);
        grid.add(rhs, 1, 0);
        
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
    
    public Button makeButton() {
        Button butt = new Button();
        butt.setText("Say 'Hello World'");
        butt.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        GridPane.setConstraints(butt, 10, 6);
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
        
        //------------------------Party Size
        Label prty = new Label("Party Size:");
        pane.add(prty, 0, 3);
        final ComboBox prt = new ComboBox(FXCollections.observableArrayList(
                "1","2","3","4","5","6","7","8","9","10","11","12","13"
            ));
        prt.setValue("1");
        pane.add(prt, 1, 3);
        
        //-------------------------Random Seed
        Label seed = new Label("Random Seed:");
        pane.add(seed, 0, 4);
        TextField seedTextField = new TextField();
        pane.add(seedTextField, 1, 4);
        
        //-------------------------Map Style
        Label styl = new Label("Map Style:");
        pane.add(styl, 0, 5);
        final ComboBox sty = new ComboBox(FXCollections.observableArrayList(
                "Standard"
            ));
        sty.setValue("Standard");
        pane.add(sty, 1, 5);
        
        //---------------------------Grid
        Label grd = new Label("Grid:");
        pane.add(grd, 0, 6);
        final ComboBox gr = new ComboBox(FXCollections.observableArrayList(
                "Square"
            ));
        gr.setValue("Square");
        pane.add(gr, 1, 6);
        
        //--------------------------Dungeon Layout
        Label lyot = new Label("Dungeon Layout:");
        pane.add(lyot, 0, 7);
        final ComboBox lo = new ComboBox(FXCollections.observableArrayList(
                "Rectangle"
            ));
        lo.setValue("Retangle");
        pane.add(lo, 1, 7);
        
        //--------------------------Dungeon Size
        Label dunsize = new Label("Dungeon Size:");
        pane.add(dunsize, 0, 8);
        final ComboBox sz = new ComboBox(FXCollections.observableArrayList(
                "Small","Medium","Large"
            ));
        sz.setValue("Medium");
        pane.add(sz, 1, 8);
        
        //--------------------------Peripheral Egress
        Label egress = new Label("Peripheral Egress:");
        pane.add(egress, 0, 9);
        final ComboBox eg = new ComboBox(FXCollections.observableArrayList(
                "No","Yes","Many","Tiling"
            ));
        eg.setValue("No");
        pane.add(eg, 1, 9);
        
        //--------------------------Stairs
        Label stairs = new Label("Peripheral Egress:");
        pane.add(stairs, 0, 10);
        final ComboBox strs = new ComboBox(FXCollections.observableArrayList(
                "No","Yes","Many"
            ));
        strs.setValue("Yes");
        pane.add(strs, 1, 10);
        
        //--------------------------Room Layout
        Label rmlayout = new Label("Room Layout:");
        pane.add(rmlayout, 0, 11);
        final ComboBox rmly = new ComboBox(FXCollections.observableArrayList(
                "Sparse","Scattered","Dense","Symmetric","Complex"
            ));
        rmly.setValue("Scattered");
        pane.add(rmly, 1, 11);
        
        //--------------------------Room Size
        Label rmsize = new Label("Room Size:");
        pane.add(rmsize, 0, 12);
        final ComboBox rmsz = new ComboBox(FXCollections.observableArrayList(
                "Small","Medium","Large"
            ));
        rmsz.setValue("Medium");
        pane.add(rmsz, 1, 12);
        
        //--------------------------Doors
        Label door = new Label("Doors:");
        pane.add(door, 0, 13);
        final ComboBox doors = new ComboBox(FXCollections.observableArrayList(
                "None","Basic","Secure","Standard","Deathtrap"
            ));
        doors.setValue("Standard");
        pane.add(doors, 1, 13);
        
        //--------------------------Corridors
        Label crdr = new Label("Corridors:");
        pane.add(crdr, 0, 14);
        final ComboBox crdrs = new ComboBox(FXCollections.observableArrayList(
                "Labyrinth","Errant","Straight"
            ));
        crdrs.setValue("Errant");
        pane.add(crdrs, 1, 14);
        
        //--------------------------Deadends
        Label dedend = new Label("Remove Deadends?");
        pane.add(dedend, 0, 15);
        final ComboBox dedends = new ComboBox(FXCollections.observableArrayList(
                "None","Some","All"
            ));
        dedends.setValue("Some");
        pane.add(dedends, 1, 15);
        
        return pane;
    }
}
