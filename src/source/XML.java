package source;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class XML {
    private Dungeon dungeon;
    public XML() {
    }
    
    public boolean readXML(String xml) {
        Document dom = null;
        String img = "";
        String dunname = "";
        int numRooms = 0;
        int numCorrs = 0;
        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<Corridor> corrs = new ArrayList<Corridor>();
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            try {
                // parse using the builder to get the DOM mapping of the
                // XML file
                dom = db.parse(xml);
            } catch (IOException ex) {
                Logger.getLogger(XML.class.getName()).log(Level.SEVERE, null, ex);
            }

            Element doc = dom.getDocumentElement();
            
            img = getTextValue(doc, "imgName");
            dunname = getTextValue(doc, "name");
            numRooms = Integer.parseInt(getTextValue(doc, "roomsSize"));
            numCorrs = Integer.parseInt(getTextValue(doc, "corrsSize"));
            int x = 0;
            int y = 0;
            int width = 0;
            int height = 0;
            String info = "";
            String name = "";
            boolean start = false;
            
            NodeList nList = doc.getElementsByTagName("room");
            Element e;
            for(int i = 0; i < numRooms; i++){
                e = (Element)nList.item(i);
                x = Integer.parseInt(getTextValue(e, "x"));
                y = Integer.parseInt(getTextValue(e, "y"));
                width = Integer.parseInt(getTextValue(e, "width"));
                height = Integer.parseInt(getTextValue(e, "height"));
                info = getTextValue(e, "info");
                name = getTextValue(e, "name");
                start = Boolean.parseBoolean(getTextValue(e, "start"));
                rooms.add(new Room(x, y, width, height, info, name, start));
            }
            nList = doc.getElementsByTagName("corridor");
            for(int i = 0; i < numCorrs; i++){
                e = (Element)nList.item(i);
                x = Integer.parseInt(getTextValue(e, "x"));
                y = Integer.parseInt(getTextValue(e, "y"));
                width = Integer.parseInt(getTextValue(e, "width"));
                height = Integer.parseInt(getTextValue(e, "height"));
                info = getTextValue(e, "info");
                name = getTextValue(e, "name");
                corrs.add(new Corridor(x, y, width, height, info, name));
            }
            dungeon = new Dungeon(img, dunname, rooms, corrs);
            return true;
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        }
        return false;
    }
    
    public void saveToXML(String xml, Dungeon dungin) {
        dungeon = dungin;
        Document dom;
        Element e;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();
            
            Element dungroot = dom.createElement("dungeon");
            
            // create data elements and place them under root
            e = dom.createElement("imgName");
            e.appendChild(dom.createTextNode("" + dungeon.getImgName()));
            dungroot.appendChild(e);
            
            e = dom.createElement("name");
            e.appendChild(dom.createTextNode("" + dungeon.getName()));
            dungroot.appendChild(e);
            
            e = dom.createElement("roomsSize");
            e.appendChild(dom.createTextNode("" + dungeon.getRooms().size()));
            dungroot.appendChild(e);
            
            e = dom.createElement("corrsSize");
            e.appendChild(dom.createTextNode("" + dungeon.getCorrs().size()));
            dungroot.appendChild(e);

            ArrayList<Room> rooms = dungeon.getRooms();
            for(Room rm : rooms) {
                // create the root element
                Element rootEle = dom.createElement("room");

                // create data elements and place them under root
                e = dom.createElement("x");
                e.appendChild(dom.createTextNode("" + rm.getX()));
                rootEle.appendChild(e);

                e = dom.createElement("y");
                e.appendChild(dom.createTextNode("" + rm.getY()));
                rootEle.appendChild(e);

                e = dom.createElement("width");
                e.appendChild(dom.createTextNode("" + rm.getWidth()));
                rootEle.appendChild(e);

                e = dom.createElement("height");
                e.appendChild(dom.createTextNode("" + rm.getHeight()));
                rootEle.appendChild(e);
                
                e = dom.createElement("info");
                e.appendChild(dom.createTextNode("" + rm.getInfo()));
                rootEle.appendChild(e);
                
                e = dom.createElement("name");
                e.appendChild(dom.createTextNode("" + rm.getName()));
                rootEle.appendChild(e);
                
                e = dom.createElement("start");
                e.appendChild(dom.createTextNode("" + rm.getStart()));
                rootEle.appendChild(e);

                dungroot.appendChild(rootEle);
            }
            ArrayList<Corridor> corr = dungeon.getCorrs();
            for(Corridor cr : corr) {
                // create the root element
                Element rootEle = dom.createElement("corridor");

                // create data elements and place them under root
                e = dom.createElement("x");
                e.appendChild(dom.createTextNode("" + cr.getX()));
                rootEle.appendChild(e);

                e = dom.createElement("y");
                e.appendChild(dom.createTextNode("" + cr.getY()));
                rootEle.appendChild(e);

                e = dom.createElement("width");
                e.appendChild(dom.createTextNode("" + cr.getWidth()));
                rootEle.appendChild(e);

                e = dom.createElement("height");
                e.appendChild(dom.createTextNode("" + cr.getHeight()));
                rootEle.appendChild(e);
                
                e = dom.createElement("info");
                e.appendChild(dom.createTextNode("" + cr.getInfo()));
                rootEle.appendChild(e);
                
                e = dom.createElement("name");
                e.appendChild(dom.createTextNode("" + cr.getName()));
                rootEle.appendChild(e);

                dungroot.appendChild(rootEle);
            }
            dom.appendChild(dungroot);
            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), 
                                     new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException | IOException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }
    
    private String getTextValue(Element doc, String tag) {
        return doc.getElementsByTagName(tag).item(0).getTextContent();
    }
    
    public Dungeon getDungeon() {
        return dungeon;
    }
}
