/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Scrublord
 */
public final class DrawGrid extends Pane {
    static int rows;
    static int columns;
    double width;
    double height;
    Cell[][] cells;
    boolean showHoverCursor = true;
    GridLocation mouseDownLoc = new GridLocation();

    public DrawGrid( int columns, int rows, double width, double height) {
        this.columns = columns;
        this.rows = rows;
        this.width = width;
        this.height = height;
        cells = new Cell[rows][columns];
        
        
        MouseGestures mg = new MouseGestures();
        // fill grid
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Cell cell = new Cell(column, row);
                mg.makePaintable(cell);
                add(cell, column, row);
            }
        }
    }
    
    public static int getRows() {
        return rows;
    }
    
    public static int getColumns() {
        return columns;
    }

    /**
     * Add cell to array and to the UI.
     */
    public void add(Cell cell, int column, int row) {

        cells[row][column] = cell;

        double w = width / columns;
        double h = height / rows;
        double x = w * column;
        double y = h * row;

        cell.setLayoutX(x);
        cell.setLayoutY(y);
        cell.setPrefWidth(w);
        cell.setPrefHeight(h);

        getChildren().add(cell);

    }

    public Cell getCell(int column, int row) {
        return cells[row][column];
    }

    /**
     * Unhighlight all cells
     */
    public void unhighlight() {
        for( int row=0; row < rows; row++) {
            for( int col=0; col < columns; col++) {
                cells[row][col].unhighlight();
            }
        }
    }
    private class Cell extends StackPane {
        int column;
        int row;
        boolean highlighted;

        public Cell(int column, int row) {
            this.column = column;
            this.row = row;
            highlighted = false;
            getStyleClass().add("grid-cell");
        }

        public void highlight() {
            // ensure the style is only once in the style list
            getStyleClass().remove("grid-cell-highlight");
            // add style
            getStyleClass().add("grid-cell-highlight");
            highlighted = true;
        }

        public void unhighlight() {
            //getStyleClass().remove("grid-cell-highlight");
            getStyleClass().add("grid-cell");
            highlighted = false;
        }

        public void hoverHighlight() {
            // ensure the style is only once in the style list
            getStyleClass().remove("grid-cell-hover-highlight");
            // add style
            getStyleClass().add("grid-cell-hover-highlight");
        }

        public void hoverUnhighlight() {
            getStyleClass().remove("grid-cell-hover-highlight");
        }

        @Override
        public String toString() {
            return this.column + "/" + this.row;
        }
    }

    public class MouseGestures {
        public void makePaintable( Node node) {
            // that's all there is needed for hovering, the other code is just for painting
            if( showHoverCursor) {
                node.hoverProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if( newValue) {
                            ((Cell) node).hoverHighlight();
                        } else {
                            ((Cell) node).hoverUnhighlight();
                        }
                    }
                });
            }
            node.setOnMousePressed( onMousePressedEventHandler);
            node.setOnDragDetected( onDragDetectedEventHandler);
            node.setOnMouseDragEntered(onMouseDragEnteredEventHandler);

        }

        EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
            Cell cell = (Cell) event.getSource();
            if(cell.highlighted)
                cell.unhighlight();
            else
                cell.highlight();
        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
            PickResult pickResult = event.getPickResult();
            Node node = pickResult.getIntersectedNode();

            if( node instanceof Cell) {
                Cell cell = (Cell) node;
                if(cell.highlighted)
                    cell.unhighlight();
                else
                    cell.highlight();
            }
        };

        EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
        };

        EventHandler<MouseEvent> onDragDetectedEventHandler = event -> {
            Cell cell = (Cell) event.getSource();
            cell.startFullDrag();
        };

        EventHandler<MouseEvent> onMouseDragEnteredEventHandler = event -> {
            Cell cell = (Cell) event.getSource();
            if(cell.highlighted)
                cell.unhighlight();
            else
                cell.highlight();
        };
    }
    
    private static class GridLocation {
        int x, y ;
    }
}
