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
    /* -----------------------------------TRYING SOMETHING NEW------------------
    long seed;
    int rows, cols, room_min, room_max, remove_deadends, add_stairs, cell_size;
    String dungeon_layout, room_layout, corridor_layout, map_style;
    
    public Dungeon() {
        seed = (new Date()).getTime();
        Random rand = new Random(seed);
        rows = rand.nextInt()%50;           //must be odd
        cols = rand.nextInt()%50;           //must be odd
        makeOdd();
        room_min = 3;                       //minimum room size
        room_max = 9;                       //maximum room size
        remove_deadends = 50;               //percentage
        add_stairs = 2;                     //number of stairs
        cell_size = 18;                     //pixels
        dungeon_layout = "None";
        room_layout = "Scattered";
        corridor_layout = "Bent";
        map_style = "Standard";
    }
    
    public Dungeon(long seedin, int room_minin, int room_maxin, int remove_deadendsin, 
            int add_stairsin, int cell_sizein, String dungeon_layoutin,
            String room_layuoutin, String corridor_layoutin, String map_stylein) {
        seed = seedin;
        Random rand = new Random(seed);
        rows = rand.nextInt(50);           //must be odd
        cols = rand.nextInt(50);           //must be odd
        makeOdd();
        room_min = room_minin;              //minimum room size
        room_max = room_maxin;              //maximum room size
        remove_deadends = remove_deadendsin;//percentage
        add_stairs = add_stairsin;          //number of stairs
        cell_size = cell_sizein;            //pixels
        dungeon_layout = dungeon_layoutin;
        room_layout = room_layuoutin;
        corridor_layout = corridor_layoutin;
        map_style = map_stylein;
    }
    
    private void makeOdd() {
        if(rows%2 == 0)
            rows += 1;
        if(cols%2 == 0)
            cols += 1;
    }
    
    @Override
    public String toString(){
        String str = "Seed:" + seed + "\nRows:" + rows + "\nCols:" + cols + "\nRoom min:" + room_min + "\nRoom max:" + room_max;
        str += "\nRemove Deadends:" + remove_deadends + "\nAdd Stairs:" + add_stairs + "\nCell Size:" + cell_size;
        return str;
    }*/
    
    //-----------------------------NEW CODE----------------------------------
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
                        x = tmp.getX() + tmp.getWidth();
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
        for(int y = ycoord; y < img.getHeight(); y++) {
            c = new Color(img.getRGB(xcoord, y));
            if(c.equals(ROOMCOLOR)) {
                height++;
            } else {
                y = img.getHeight();
            }
        }
        for(int x = xcoord; x < img.getWidth(); x++) {
            c = new Color(img.getRGB(x, ycoord));
            if(c.equals(ROOMCOLOR)) {
                width++;
            } else {
                x = img.getWidth();
            }
        }
        room.setHeight(height);
        room.setWdith(width);
        return room;
    }
    
    private Room alreadyRoom(int x, int y) {
        for(Room room : rooms) {
            if(x >= room.getX() && x <= room.getX() + room.getWidth() && y >= room.getY() && y <= room.getY() + room.getHeight())
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
