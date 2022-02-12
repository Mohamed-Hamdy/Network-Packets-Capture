/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Network_Packets_Capture;

/**
 *
 * @author dell
 */
import static java.lang.System.exit;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.*;

/**
 * Use jpcap to display various data packets on the network
 *
 * @author www.NetJava.cn
 */
public class DispalyNetPacket {
    //Program start main method  

    public static void main(String args[]) {

        try {
            //Get an array of network interface objects on this machine  
            final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            JpcapCaptor captor = JpcapCaptor.openDevice(devices[0], 65535, false, 20);
            //captor.processPacket(10, new PacketPrinter());
            for (int i = 0; i < devices.length; i++) {

                NetworkInterface nc = devices[i];

                //Create a grabbed object on a bayonet, the maximum is 2000  
                /*
                devices[i] -> Network interface that you want to open.
                
                true -> True if you want to open the interface in promiscuous mode, and otherwise false.
                In promiscuous mode, you can capture packets every packet from the wire
                
                2000 -> Max number of bytes to capture at once.
                
                20 -> Set a capture timeout value in milliseconds.
                */
                JpcapCaptor jpcap = JpcapCaptor.openDevice(nc, 2000, true, 20);
                System.out.println(i + "--> Start to capture the first " + nc.description + " " + nc.addresses + " data on the bayonet");
                startCapThread(i, nc.description, jpcap);

            }

        } catch (Exception ef) {

            ef.printStackTrace();

            System.out.println("Start failed: " + ef);

        }

    }

    static class PacketPrinter implements PacketReceiver {
        //this method is called every time Jpcap captures a packet

        public void receivePacket(Packet packet) {
            //just print out a captured packet
            System.out.println(packet);
        }
    }

    //Put each Captor in a separate thread to run  
    public static void startCapThread(int i, String name, final JpcapCaptor jpcap) {
        System.out.println(i);
        JpcapCaptor jp = jpcap;

        java.lang.Runnable rnner = new Runnable() {

            public void run() {
                //Use the packet receiving processor to capture packets in a loop  
                jpcap.loopPacket(-1, new TestPacketReceiver());
            }
        };
        new Thread(rnner).start(); //Start the packet capture thread  
    }

}

