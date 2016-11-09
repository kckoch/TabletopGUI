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
        viewRoomInfoPane();
        
        box.getChildren().add(mapArea);
        box.getChildren().add(info);
    }
    
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
    
    public void addRoom(int x, int y, int width, int height, int ndx) {
        BorderPane room = new BorderPane();
        room.setPrefSize(width, height);
        room.relocate(x, y);
        room.getStyleClass().add("room-background");
        room.setOnMouseClicked((MouseEvent t) -> {
            System.out.println(ndx);
        });
        Label lbl = new Label(Integer.toString(ndx));
        lbl.getStyleClass().add("room-label-text");
        room.setCenter(lbl);
        map.getChildren().add(room);
    }
    public void addCorr(int x, int y, int width, int height, int ndx) {
        Pane room = new Pane();
        room.setPrefSize(width, height);
        room.relocate(x, y);
        room.getStyleClass().add("room-background");
        room.setOnMouseClicked((MouseEvent t) -> {
            System.out.println(ndx);
        });
        map.getChildren().add(room);
    }
    
    public HBox getBox() {
        return box;
    }
    
    public void viewRoomInfoPane() {
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
        
        //-------------------------Send to Mat Button
        Button butt = new Button();
        butt.setText("Send to Mat");
        butt.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        info.getChildren().add(butt);
    }
    
    public void createMap() {
        mapArea = new GridPane();
        
        map = new Pane();
        map.getStyleClass().add("pane-background");
        control = new MiddlePaneController(new Dungeon("image.png"));
        map.setPrefSize(control.getWidth(), control.getHeight());
        
        mapArea.setPrefWidth(WIDTH*MAPCONST);
        mapArea.setAlignment(Pos.CENTER);
        mapArea.getStyleClass().add("grid-background");
        mapArea.getChildren().add(map);
    }
}
