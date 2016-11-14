/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

public class Room {
    int x, y, width, height;
    String info, name;
    boolean start;
    
    public Room(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
        this.info = "Relevant Info About This Specific Room";
        this.name = "";
        this.start = false;
    }
    
    public void setWdith(int width){
        this.width = width;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setStart(){
        start = true;
    }
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String in) {
        info = in;
    }
    
    public void setName(String namein) {
        name = namein;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        String str = "x: " + x + "\ny: " + y + "\nwidth: " + width + "\nheight: " + height;
        return(str);
    }
}
