/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.DrawPaneController;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import source.Corridor;
import source.Dungeon;
import source.Room;

public class DrawPane {
    Pane pane;
    DrawPaneController control;
    
    public DrawPane(){
        pane = new Pane();
        pane.getStyleClass().add("pane-background");
        control = new DrawPaneController(new Dungeon("image.png"));
        pane.setPrefSize(control.getWidth(), control.getHeight());
        drawRooms();
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
        room.setOnMouseClicked(new EventHandler<MouseEvent> () {
           @Override
           public void handle(MouseEvent t) {
               System.out.println(ndx);
           }
        });
        Label lbl = new Label(Integer.toString(ndx));
        lbl.getStyleClass().add("room-label-text");
        room.setCenter(lbl);
        pane.getChildren().add(room);
    }
    public void addCorr(int x, int y, int width, int height, int ndx) {
        Pane room = new Pane();
        room.setPrefSize(width, height);
        room.relocate(x, y);
        room.getStyleClass().add("room-background");
        room.setOnMouseClicked(new EventHandler<MouseEvent> () {
           @Override
           public void handle(MouseEvent t) {
               System.out.println(ndx);
           }
        });
        pane.getChildren().add(room);
    }
    
    public Pane getPane() {
        return pane;
    }
}
