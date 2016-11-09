/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Dungeon {
    BufferedImage img;
    ArrayList<Room> rooms;
    ArrayList<Corridor> corrs;
    static final Color ROOMCOLOR = new Color(136,136,136);
    public Dungeon(String filename) {
        try {
            img = ImageIO.read(Dungeon.class.getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rooms = new ArrayList<Room>();
        corrs = new ArrayList<Corridor>();
        parseImage();
        separateCorridors();
    }
    
    private void parseImage() {
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                Color c = new Color(img.getRGB(x, y));
                if(c.equals(ROOMCOLOR)) {
                    Room tmp;
                    if((tmp=alreadyRoom(x, y)) == null) {
                        rooms.add(getRoomInfo(x, y));
                        x = rooms.get(rooms.size()-1).getWidth();
                    } else {
                        x = tmp.getX() + tmp.getWidth() - 1;
                    }
                }
            }
        }
    }
    
    private Room getRoomInfo(int xcoord, int ycoord) {
        Room room = new Room(xcoord, ycoord);
        int height = 0;
        int width = 0;
        Color c;
        for(int x = xcoord; x < img.getWidth(); x++) {
            c = new Color(img.getRGB(x, ycoord));
            if(c.equals(Color.BLACK)) {
                x = img.getWidth();
            } else {
                width++;
            }
        }
        for(int y = ycoord; y < img.getHeight(); y++) {
            c = new Color(img.getRGB(xcoord, y));
            if(c.equals(Color.BLACK))
                y = img.getHeight();
            else
                height++;
        }
        room.setWdith(width);
        room.setHeight(height);
        return room;
    }
    
    private Room alreadyRoom(int x, int y) {
        for(Room room : rooms) {
            if(x >= room.getX() && x < room.getX() + room.getWidth() && y >= room.getY() && y < room.getY() + room.getHeight())
                return room;
        }
        return null;
    }
    
    private void separateCorridors() {
        ArrayList<Integer> toremove = new ArrayList<Integer>();
        for(Room room : rooms) {
            if(room.getWidth() == 8 || room.getHeight() == 8) {
                Room rm = rooms.get(rooms.indexOf(room));
                toremove.add(rooms.indexOf(room));
                corrs.add(new Corridor(rm.getX(), rm.getY(), rm.getWidth(), rm.getHeight()));
            }
        }
        int count = 0;
        for(Integer in : toremove){
            rooms.remove(in.intValue()-count);
            count++;
        }
    }
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    
    public ArrayList<Corridor> getCorrs() {
        return corrs;
    }
    
    public Room getRoom(int xnd) {
        return rooms.get(xnd);
    }
    
    public Corridor getCorr(int ndx) {
        return corrs.get(ndx);
    }
    
    public int getImgWidth() {
        return img.getWidth();
    }
    
    public int getImgHeight() {
        return img.getHeight();
    }
    
    public String toString() {
        String str = "Rooms\n";
        for(Room room : rooms)
            str += room.toString() + "\n\n";
        str += "\n\n\nCorrs\n";
        for(Corridor cor : corrs)
            str += cor.toString() + "\n\n";
        return str;
    }
}
