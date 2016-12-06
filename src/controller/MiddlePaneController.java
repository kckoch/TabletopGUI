/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import java.util.logging.Level;
import java.util.logging.Logger;
import source.*;

public class MiddlePaneController {
    private Dungeon dungeon;
    XML xml;
    BTClient client;
    byte[][] arr;
    
    //This constructor will check to see if there is a dungeon saved in the xml.
    //If there is, it will read in that dungeon and all its information.
    //Otherwise, it'll create a default dungeon based on image.png
    public MiddlePaneController() {
        xml = new XML();
        File xmlfile = new File("save.xml");
        if(xmlfile.exists()){
            System.out.println("XML Exists");
            if(xml.readXML("save.xml")) {
                System.out.println("We were able to read the XML.");
                dungeon = xml.getDungeon();
                dungeon.createDoors();
            } else {
                System.out.println("SOMETHING WENT WRONG WITH THE READ XML!!");
            }  
        } else {
            dungeon = new Dungeon("image.png");
            System.out.println("Need to create an xml");
            xml.saveToXML("save.xml", dungeon);
        }
        try {
            //p sure this doesn't work yet
            client = new BTClient();
            client.init();
        } catch (Exception e) {
            Logger.getLogger(MiddlePaneController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public int getNumberRooms() {
        return dungeon.getRooms().size();
    }
    
    public int getNumberCorr(){
        return dungeon.getCorrs().size();
    }
    
    public Room getRoom(int ndx) {
        return dungeon.getRoom(ndx);
    }
    
    public Corridor getCorr(int ndx) {
        return dungeon.getCorr(ndx);
    }
    
    public int getWidth() {
        return dungeon.getImgWidth();
    }
    
    public int getHeight() {
        return dungeon.getImgHeight();
    }
    
    public String getName(int ndx) {
        return dungeon.getRoom(ndx).getName();
    }
    
    public String getInfo(int ndx) {
        return dungeon.getRoom(ndx).getInfo();
    }
    
    public int getRoomHeight(int ndx) {
        return dungeon.getRoom(ndx).getHeight();
    }
    
    public int getRoomWidth(int ndx) {
        return dungeon.getRoom(ndx).getWidth();
    }
    
    public void setInfo(String str, int ndx) {
        dungeon.getRoom(ndx).setInfo(str);
    }
    
    public void setName(String str, int ndx) {
        dungeon.getRoom(ndx).setName(str);
    }
    
    public void save() {
        xml.saveToXML("save.xml", dungeon);
    }
    
    public void sendToMat(int roomndx) {
        String send = "";
        int width = dungeon.getRoom(roomndx).getWidth();
        int height = dungeon.getRoom(roomndx).getHeight();
        double norm;
        if(width > height)
            norm = 12.0/width;
        else
            norm = 12.0/height;
        width = (int) Math.ceil(width*norm) - 1;
        height = (int) Math.ceil(height*norm) - 1;
        
        int xstart = (12-width)/2;
        int ystart = (12-height)/2;
        int xend = xstart+width;
        int yend = ystart+height;

        arr = new byte[12][12];
        
        for(int i = xstart; i <= xend; i++){
            arr[ystart][i] = 2;
            arr[yend][i] = 2;
        }
        for(int i = ystart; i <= yend; i++) {
            arr[i][xstart] = 2;
            arr[i][xend] = 2;
        }
        
        drawDoors(xstart, ystart, xend, yend, roomndx);
        
        for(int i = 0; i < 12; i++){
            for(int k = 0; k < 12; k++){
                if(i%2 == 0) {
                    System.out.print(arr[i][k] + " ");
                    if(k == 11) {
                        System.out.print("\n");
                    }
                    send += arr[i][k];
                } else {
                    System.out.print(arr[i][11-k] + " ");
                    send += arr[i][11-k];
                    if(k == 11) {
                        System.out.print("\n");
                    }
                }
            }
        }
        System.out.println(send);
        client.send(send);
    }
    
    private void drawDoors(int xstart, int ystart, int xend, int yend, int roomndx) {
        ArrayList<String> doors = dungeon.getRoom(roomndx).getDoors();
        int N = 0;
        int S = 0;
        int E = 0;
        int W = 0;
        for(String door : doors) {
            if(door.equals("NORTH"))
                N++;
            else if (door.equals("SOUTH"))
                S++;
            else if (door.equals("EAST"))
                E++;
            else
                W++;
        }
        System.out.println(dungeon.getRoom(roomndx).getName()+" N: " + N + " S: " + S + " E: " + E + " W: " + W);
        if(N == 1){
            arr[ystart][(xstart+xend)/2 - 1] = 1;
            arr[ystart][(xstart+xend)/2] = 1;
        } else if (N == 2) {
            arr[ystart][(xstart+xend)/4 - 1] = 1;
            arr[ystart][(xstart+xend)/4] = 1;
            arr[ystart][((xstart+xend)*3)/4 - 1] = 1;
            arr[ystart][((xstart+xend)*3)/4] = 1;
        }
        if(S == 1){
            arr[yend][(xstart+xend)/2 - 1] = 1;
            arr[yend][(xstart+xend)/2] = 1;
        } else if (S == 2) {
            arr[yend][(xstart+xend)/4 - 1] = 1;
            arr[yend][(xstart+xend)/4] = 1;
            arr[yend][((xstart+xend)*3)/4 - 1] = 1;
            arr[yend][((xstart+xend)*3)/4] = 1;
        }
        if(E == 1){
            arr[(ystart+yend)/2 - 1][xend] = 1;
            arr[(ystart+yend)/2][xend] = 1;
        } else if (E == 2) {
            arr[(ystart+yend)/4 - 1][xend] = 1;
            arr[(ystart+yend)/4][xend] = 1;
            arr[((ystart+yend)*3)/4 - 1][xend] = 1;
            arr[((ystart+yend)*3)/4][xend] = 1;
        }
        if(W == 1){
            arr[(ystart+yend)/2 - 1][xstart] = 1;
            arr[(ystart+yend)/2][xstart] = 1;
        } else if (W == 2) {
            arr[(ystart+yend)/4 - 1][xstart] = 1;
            arr[(ystart+yend)/4][xstart] = 1;
            arr[((ystart+yend)*3)/4 - 1][xstart] = 1;
            arr[((ystart+yend)*3)/4][xstart] = 1;
        }
    }
}
