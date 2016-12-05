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
    
    //This constructor will check to see if there is a dungeon saved in the xml.
    //If there is, it will read in that dungeon and all its information.
    //Otherwise, it'll create a default dungeon based on image.png
    public MiddlePaneController() {
        xml = new XML();
        File xmlfile = new File("save.xml");
        if(xmlfile.exists()){
            System.out.println("xml exists");
            if(xml.readXML("save.xml")) {
                System.out.println("we were able to read the xml");
                dungeon = xml.getDungeon();
            } else {
                System.out.println("SOMETHING WENT WRONG WITH THE READ XML!!");
            }  
        } else {
            dungeon = new Dungeon("image.png");
            System.out.println("need to create an xml");
            xml.saveToXML("save.xml", dungeon);
        }
        
        try {
            //p sure this doesn't work yet
            client = new BTClient();
            //client.init();
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
        System.out.println("width: " + width + " height: " + height);
        double norm;
        if(width > height)
            norm = 12.0/width;
        else
            norm = 12.0/height;
        System.out.println("norm: " + norm);
        width = (int) Math.ceil(width*norm) - 1;
        height = (int) Math.ceil(height*norm) - 1;
        
        System.out.println("newwidth: " + width + " newheight: " + height);
        
        int xstart = (12-width)/2;
        int ystart = (12-height)/2;
        int xend = xstart+width;
        int yend = ystart+height;
        
        System.out.println("xstart: " + xstart + " ystart: " + ystart + " xend: " + xend + " yend: " + yend);
        byte[][] arr = new byte[12][12];
        
        for(int i = xstart; i <= xend; i++){
            arr[ystart][i] = 1;
            arr[yend][i] = 1;
        }
        for(int i = ystart; i <= yend; i++) {
            arr[i][xstart] = 1;
            arr[i][xend] = 1;
        }
        
        for(int i = 0; i < 12; i++){
            for(int k = 0; k < 12; k++){
                System.out.print(arr[i][k] + " ");
                if(k == 11) {
                    System.out.print("\n");
                }
                send += arr[i][k];
            }
        }
        
        System.out.println(send);
    }
}
