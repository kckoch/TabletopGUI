/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.bluetooth.*;
import javax.microedition.io.*;

public class BTClient implements DiscoveryListener {
    //object used for waiting
    private static Object lock=new Object();
    //vector containing the devices discovered
    private static Vector vecDevices=new Vector();
    private static String connectionURL=null;

    public void init() throws IOException {
        System.out.println("in init");
        System.out.flush();
        BTClient client=new BTClient();

        //display local device address and name
        LocalDevice localDevice = LocalDevice.getLocalDevice();
        System.out.println("Address: "+localDevice.getBluetoothAddress());
        System.out.println("Name: "+localDevice.getFriendlyName());

        System.out.println("created local device");
        System.out.flush();
        //find devices
        DiscoveryAgent agent = localDevice.getDiscoveryAgent();

        System.out.println("Starting device inquiry...");
        agent.startInquiry(DiscoveryAgent.GIAC, client);

        try {
            synchronized(lock){
                lock.wait();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Device Inquiry Completed. ");

        //print all devices in vecDevices
        int deviceCount=vecDevices.size();

        if(deviceCount <= 0){
            System.out.println("No Devices Found .");
            System.exit(0);
        }
        else{
            //print bluetooth device addresses and names in the format [ No. address (name) ]
            System.out.println("Bluetooth Devices: ");
            for (int i = 0; i <deviceCount; i++) {
                RemoteDevice remoteDevice=(RemoteDevice)vecDevices.elementAt(i);
                System.out.println((i+1)+". "+remoteDevice.getBluetoothAddress()+" ("+remoteDevice.getFriendlyName(true)+")");
            }
        }

        System.out.print("Choose Device index: ");
        BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));

        String chosenIndex=bReader.readLine();
        int index=Integer.parseInt(chosenIndex.trim());

        //check for spp service
        RemoteDevice remoteDevice=(RemoteDevice)vecDevices.elementAt(index-1);
        UUID[] uuidSet = new UUID[1];
        uuidSet[0]=new UUID("446118f08b1e11e29e960800200c9a66", false);

        System.out.println("\nSearching for service...");
        agent.searchServices(null,uuidSet,remoteDevice,client);

        try {
            synchronized(lock){
                lock.wait();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(connectionURL==null){
            System.out.println("Device does not support Simple SPP Service.");
            System.exit(0);
        }

        //connect to the server and send a line of text
        StreamConnection streamConnection=(StreamConnection)Connector.open(connectionURL);

        //send string

        //read response

    }//init

    //methods of DiscoveryListener
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        //add the device to the vector
        if(!vecDevices.contains(btDevice)){
            vecDevices.addElement(btDevice);
        }
    }

    //implement this method since services are not being discovered
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        if(servRecord!=null && servRecord.length>0){
            connectionURL=servRecord[0].getConnectionURL(0,false);
        }
        synchronized(lock){
            lock.notify();
        }
    }

    //implement this method since services are not being discovered
    public void serviceSearchCompleted(int transID, int respCode) {
        synchronized(lock){
            lock.notify();
        }
    }


    public void inquiryCompleted(int discType) {
        synchronized(lock){
            lock.notify();
        }

    }//end method
}
