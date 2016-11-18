/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.MiddlePaneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import source.Corridor;
import source.Dungeon;
import source.Room;

public final class MiddlePane {
    private static double HEIGHT;
    private static double WIDTH;
    final static double MAPCONST = .7;
    final static double INFOCONST = .3;
    final static double MIDHEIGHTCONST = .8;
    final static int NAME = 0;
    final static int SIZE = 1;
    final static int ROOMINFO = 2;
    private final HBox box;
    private GridPane mapArea;
    private Pane map;
    private MiddlePaneController control;
    private VBox info;
    private int selectedRoom;
    
    public MiddlePane(double width, double height){
        box = new HBox();
        WIDTH = width;
        HEIGHT = height;
        selectedRoom = 0;
        box.setPrefHeight(HEIGHT*MIDHEIGHTCONST);
        
        createMap();
        drawRooms();
        roomInfoPane();
        
        box.getChildren().add(mapArea);
        box.getChildren().add(info);
    }
    
    //creates the map gui as well as the backend map information
    public void createMap() {
        mapArea = new GridPane();
        
        //--------------THIS IS WHERE THE IMAGE IS PARSED TO CREATE DUNGEON INFO-----------------
        control = new MiddlePaneController();
        
        //map image gui
        map = new Pane();
        map.getStyleClass().add("pane-background");
        map.setPrefSize(control.getWidth(), control.getHeight());
        
        //map area gui
        mapArea.setPrefWidth(WIDTH*MAPCONST);
        mapArea.setAlignment(Pos.CENTER);
        mapArea.getStyleClass().add("grid-background");
        mapArea.getChildren().add(map);
    }
    
    //this function draws the rooms and corridors onto the map
    //NOTE: this function violates the rule where gui code isn't supposed to deal with backend code
    //but I've decided ease and readability is more important
    public void drawRooms(){
        int size = control.getNumberCorr();
        for(int i = 0; i < size; i++) {
            Corridor cr = control.getCorr(i);
            addCorr(cr.getX(), cr.getY(), cr.getWidth(), cr.getHeight(), i);
        }
        size = control.getNumberRooms();
        for(int i = 0; i < size; i++) {
            Room rm = control.getRoom(i);
            addRoom(rm.getX(), rm.getY(), rm.getWidth(), rm.getHeight(), i);
        }
    }
    
    //draws the corridors on the map.  currently does not do anything when the corridor is clicked
    public void addCorr(int x, int y, int width, int height, int ndx) {
        Pane corr = new Pane();
        corr.setPrefSize(width, height);
        corr.relocate(x, y);
        corr.getStyleClass().add("room-background");
        corr.setOnMouseClicked((MouseEvent t) -> {
            System.out.println(ndx);
        });
        map.getChildren().add(corr);
    }
    
    //draws the rooms on the map.  will update the info panel when a new room is clicked
    public void addRoom(int x, int y, int width, int height, int ndx) {
        BorderPane room = new BorderPane();
        room.setPrefSize(width, height);
        room.relocate(x, y);
        room.getStyleClass().add("room-background");
        room.setOnMouseClicked((MouseEvent t) -> {
            //update info to whatever room was clicked
            selectedRoom = ndx;
            Node node = info.getChildren().get(NAME);
            if(node instanceof TextField) {
                //update the room name
                ((TextField) node).setText(control.getName(ndx));
                node = info.getChildren().get(SIZE);
                if(node instanceof GridPane){
                    //update the height and width info
                    GridPane size = new GridPane();
                    size.getStyleClass().add("info-label");
                    ColumnConstraints column1 = new ColumnConstraints();
                    column1.setHalignment(HPos.RIGHT);
                    size.getColumnConstraints().add(column1); 
                    ColumnConstraints column2 = new ColumnConstraints();
                    column2.setHalignment(HPos.LEFT);
                    size.getColumnConstraints().add(column2); 

                    Label h = new Label("Height: " + control.getRoomHeight(selectedRoom));
                    Label w = new Label("Width: " + control.getRoomWidth(selectedRoom));
                    size.add(h, 0, 0);
                    size.add(w, 1, 0);
                    ((GridPane) node).getChildren().clear();
                    ((GridPane) node).getChildren().add(size);
                    node = info.getChildren().get(ROOMINFO);
                    if(node instanceof TextArea) {
                        //update room info
                        ((TextArea) node).setText(control.getInfo(ndx));
                    }
                }
            }
        });
        //labels the room on the map
        Label lbl = new Label(Integer.toString(ndx));
        lbl.getStyleClass().add("room-label-text");
        room.setCenter(lbl);
        map.getChildren().add(room);
    }
    
    //this is the panel on the right that shows key room information
    public void roomInfoPane() {
        info = new VBox();
        info.setPrefWidth(WIDTH*INFOCONST);
        info.getStyleClass().add("info");

        //------------------------Room Name
        TextField roomNameField = new TextField();
        roomNameField.setText("Room " + Integer.toString(selectedRoom));
        info.getChildren().add(roomNameField);

        //-------------------------Room Size
        GridPane size = new GridPane();
        size.getStyleClass().add("info-label");
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        size.getColumnConstraints().add(column1); 
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        size.getColumnConstraints().add(column2); 
        
        Label height = new Label("Height: " + control.getRoom(selectedRoom).getHeight());
        Label width = new Label("Width: " + control.getRoom(selectedRoom).getWidth());
        size.add(height, 0, 0);
        size.add(width, 1, 0);
        info.getChildren().add(size);
        
        //-------------------------Room Info
        TextArea roomInfo = new TextArea(control.getInfo(selectedRoom));
        roomInfo.setPrefSize(WIDTH*INFOCONST, 650);
        roomInfo.setWrapText(true);
        info.getChildren().add(roomInfo);
        
        //-------------------------Save Button
        GridPane butt = new GridPane();
        butt.getStyleClass().add("buttons-pane");
        butt.getColumnConstraints().add(column1); 
        butt.getColumnConstraints().add(column2);
        
        Button save = new Button();
        save.setText("Save");
        save.setOnAction((ActionEvent event) -> {
            control.setInfo(roomInfo.getText(), selectedRoom);
            control.setName(roomNameField.getText(), selectedRoom);
            control.save();
        });
        butt.add(save, 0, 0);
        
        //-------------------------Send to Mat Button
        Button mat = new Button();
        mat.getStyleClass().add("buttons-send");
        mat.setText("Send to Mat");
        mat.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        butt.add(mat, 1, 0);
        info.getChildren().add(butt);
    }
    
    public HBox getBox() {
        return box;
    }
}
