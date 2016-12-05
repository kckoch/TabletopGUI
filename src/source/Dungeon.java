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
    String imgName, name;
    ArrayList<Room> rooms;
    ArrayList<Corridor> corrs;
    static final Color ROOMCOLOR = new Color(136,136,136);
    
    //This constructor is for creating a dungeon for the first time
    public Dungeon(String filename) {
        name = "Dungeon";
        try {
            imgName = filename;
            img = ImageIO.read(Dungeon.class.getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rooms = new ArrayList<Room>();
        corrs = new ArrayList<Corridor>();
        parseImage();
        separateCorridors();
        createDoors();
        nameRooms();
        nameCorrs();
    }
    
    //This constructor is used if reading in a dungeon from an xml
    public Dungeon(String filename, String name, ArrayList<Room> roomsin, ArrayList<Corridor> corrsin) {
        try {
            imgName = filename;
            img = ImageIO.read(Dungeon.class.getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.name = name;
        rooms = roomsin;
        corrs = corrsin;
    }
    
    //parses the image to distinguish a room/corridor from the background
    private void parseImage() {
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                Color c = new Color(img.getRGB(x, y));
                //checks if the current pixel color is the defined room color
                if(c.equals(ROOMCOLOR)) {
                    Room tmp;
                    //makes sure the current room is not already saved
                    if((tmp=alreadyRoom(x, y)) == null) {
                        //it is a new room
                        rooms.add(getRoomInfo(x, y));
                        //moves to the width of the roomso we don't check each pixel
                        x = rooms.get(rooms.size()-1).getWidth();
                    } else {
                        //else the dungeon already knows about the room and moves to the width of the room
                        x = tmp.getX() + tmp.getWidth() - 1;
                    }
                }
            }
        }
    }
    
    //This function finds the width and the height of the new room and makes a new room
    private Room getRoomInfo(int xcoord, int ycoord) {
        Room room = new Room(xcoord, ycoord);
        int height = 0;
        int width = 0;
        Color c;
        //finds the width of the room
        for(int x = xcoord; x < img.getWidth(); x++) {
            c = new Color(img.getRGB(x, ycoord));
            if(c.equals(Color.BLACK)) {
                x = img.getWidth();
            } else {
                width++;
            }
        }
        //finds the height of the room
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
    
    //checks the current x and y coordinates vs all the rooms we already know about and their size
    private Room alreadyRoom(int x, int y) {
        for(Room room : rooms) {
            if(x >= room.getX() && x < room.getX() + room.getWidth() && y >= room.getY() && y < room.getY() + room.getHeight())
                return room;
        }
        return null;
    }
    
    //separates the corridors from the rooms and stores them in two separate arrays
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
    
    private void createDoors() {
        for(Room room : rooms) {
            for(Corridor corr : corrs) {
                if(corr.getX() <= room.getX() && corr.getX() + corr.getWidth() >= room.getX()) {
                    if(corr.getY() >= room.getY() && corr.getX() + corr.getHeight() <= room.getX()){
                        room.addDoor("EAST");
                    }
                }
            }
        }
    }
    
    //Gives each room a standard name until it is changed
    //Ex. "Room 0"
    private void nameRooms(){
        for(int i = 0; i < rooms.size(); i++) {
            rooms.get(i).setName("Room " + Integer.toString(i));
        }
    }
    
    //Gives each ccorridor a standard name until it is changed
    //Ex. "Corridor 0"
    private void nameCorrs(){
        for(int i = 0; i < corrs.size(); i++) {
            corrs.get(i).setName("Corridor " + Integer.toString(i));
        }
    }
    
    public void setName(String namein) {
        name = namein;
    }
    
    public void setImgName(String namein) {
        imgName = namein;
    }
    
    public void setRooms(ArrayList<Room> roomsin) {
        rooms = roomsin;
    }
    
    public void setCorrs(ArrayList<Corridor> corrsin) {
        corrs = corrsin;
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
    
    public String getImgName() {
        return imgName;
    }
    
    public String getName() {
        return name;
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
