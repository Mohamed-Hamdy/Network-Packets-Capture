
package Network_Packets_Capture;


import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

/**
 * Use jpcap to display network interface data.
 */
public class DispalyNetInterface {

    public static void main(String args[]) {

        try {

            //Get an array of network interface objects on this machine    
            final NetworkInterface[] devices = JpcapCaptor.getDeviceList();
            for (int i = 0; i < devices.length; i++) {

                NetworkInterface nc = devices[i];

                //There may be multiple addresses on a card:  
                String address = "";

//                for (int t = 0; t < nc.addresses.length; t++) {
//                    address += "|addresses[" + t + "]: " + nc.addresses[t].address;
//                }
                System.out.println(i + "interface:" + "|name: " + nc.name + " |description: " + nc.description + " |Mac Address: " + nc.mac_address);

                // + "|loopback: " + nc.loopback + "\r\naddress:" + address + "\n");
            }

        } catch (Exception ef) {

            ef.printStackTrace();

            System.out.println("Failed to display network interface data: " + ef);

        }

    }

}
