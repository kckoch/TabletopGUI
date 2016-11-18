/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

public class Corridor {
    int x, y, width, height;
    String info, name;
    
    //This constructor is used if we are creating the corridor for the first time
    public Corridor(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.info = "Revelent Corridor Info";
        this.name = "";
    }
    
    //This constructor is used if we are reading in an xml corridor
    public Corridor(int x, int y, int width, int height, String info, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.info = info;
        this.name = name;
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
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String in){
        info = in;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String in) {
        name = in;
    }
    
    @Override
    public String toString() {
        String str = "x: " + x + "\ny: " + y + "\nwidth: " + width + "\nheight: " + height;
        return(str);
    }
}
