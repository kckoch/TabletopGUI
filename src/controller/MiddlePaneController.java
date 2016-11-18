/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.ArrayList;
import source.*;

public class MiddlePaneController {
    private Dungeon dungeon;
    XML xml;
    
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
    }
    
    public MiddlePaneController(Dungeon dung) {
        dungeon = dung;
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
}
