/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Network_Packets_Capture;

import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

class TestPacketReceiver implements PacketReceiver {

    /**
     * Realized package receiving method:
     */
    public void receivePacket(Packet packet) {

        //Tcp package, only load data can be obtained in java Socket  
        if (packet instanceof jpcap.packet.TCPPacket) {

            TCPPacket p = (TCPPacket) packet;
            // SMTP Packets
            if (p.src_port == 25 && p.dst_port == 25) {

                String q = "@ @ @ SMTPPacket:| Source Port " + p.src_port + "Destination Port" + p.dst_port + "Source Address" + p.src_ip
                        + "Destination Address" + p.dst_ip + "Length" + p.len + "\nData" + p.data;
                System.out.println(q);
            }            
            // HTTP Packet
            else if (p.src_port == 80 && p.dst_port == 80) {

                String q ="! ! ! HTTP Packet" +"Source Port" + p.src_port+ "Destination Port"+ p.dst_port+ "Source Address"+ p.src_ip+ "Destination Address"+ p.dst_ip+
                           "Sequence Number" + p.sequence+ "ACK Number"+ p.ack_num +"ACK"+ p.ack + "FIN"+p.fin+ "SIN"+p.syn+
                           "RST"+p.rst+ "PSH"+p.psh+ "Header"+p.header;            
                System.out.println(q);
            }
            // FTP Packet
            else if (p.src_port == 20 && p.dst_port == 21 || p.src_port == 21 && p.dst_port == 20){

                String q ="| | | FTP Packet" +"Source Port" + p.src_port+ "Destination Port"+ p.dst_port+ "Source Address"+ p.src_ip+ "Destination Address"+ p.dst_ip+
                           "Sequence Number" + p.sequence+ "ACK Number"+ p.ack_num +"ACK"+ p.ack + "FIN"+p.fin+ "SIN"+p.syn+
                           "RST"+p.rst+ "PSH"+p.psh+ "Header"+p.header;            
                System.out.println(q);
            } 
            // TCP Packets
            else {
                String s = "TCPPacket:| dst_ip " + p.dst_ip + ":" + p.dst_port
                        + "|src_ip " + p.src_ip + ":" + p.src_port
                        + "|len:" + p.len;

                System.out.println(s);
            }
        } //UDP package, open QQ, you will see: it is tcp+udp  
        else if (packet instanceof jpcap.packet.UDPPacket) {

            UDPPacket p = (UDPPacket) packet;
            // DNS Packets
            if (p.src_port == 53 && p.dst_port == 53) {

                String q = "& & & DNSPacket:| Source Port " + p.src_port + "Destination Port" + p.dst_port + "Source Address" + p.src_ip
                        + "Destination Address" + p.dst_ip + "Length" + p.len + "\nData" + p.data;
                System.out.println(q);
            } // Normal UDP Packets 
            else {
                String s = "UDPPacket:| dst_ip " + p.dst_ip + ":" + p.dst_port
                        + "|src_ip " + p.src_ip + ":" + p.src_port
                        + "|len:" + p.len;

                System.out.println(s);
            }
        } //If you want to construct a ping message in the program, you must construct an ICMPPacket package  
        else if (packet instanceof jpcap.packet.ICMPPacket) {

            ICMPPacket p = (ICMPPacket) packet;

            //ICMP packet routing chain  
            String router_ip = "";

            for (int i = 0; i < p.router_ip.length; i++) {

                router_ip += "" + p.router_ip[i].getHostAddress();

            }

            String s = "@ @ @ ICMPPacket:| router_ip " + router_ip
                    + "|redir_ip:" + p.redir_ip
                    + "|mtu:" + p.mtu
                    + "|length:" + p.len;

            System.out.println(s);

        } //Whether the address conversion protocol request packet  
        else if (packet instanceof jpcap.packet.ARPPacket) {

            ARPPacket p = (ARPPacket) packet;

            //Returns the hardware address (MAC address) of the sender  
            Object saa = p.getSenderHardwareAddress();

            Object taa = p.getTargetHardwareAddress();

            String s = "* * * ARPPacket:| SenderHardwareAddress " + saa
                    + "| TargetHardwareAddress " + taa
                    + "|len:" + p.len;

            System.out.println(s);

        } else if (packet instanceof jpcap.packet.IPPacket) {
            String s = "";
            if (packet instanceof IPPacket && ((IPPacket) packet).version == 4 && packet instanceof TCPPacket) {
                s = "^ ^ ^ IP Version 4 Packet" + "Version" + ((IPPacket) packet).version + "Length" + ((IPPacket) packet).len + "Identification" + ((IPPacket) packet).ident
                        + "Protocol" + ((IPPacket) packet).protocol + "Source IP" + ((IPPacket) packet).src_ip + "Destination IP" + ((IPPacket) packet).dst_ip;
                System.out.println(s);
            }

        }
        // ICMP Packets
        else if (packet instanceof jpcap.packet.ICMPPacket) {
            ICMPPacket p = (ICMPPacket) packet;
            String s = "";
            s = "^ ^ ^ ICMP Packet" + "Source IP" + p.src_ip + "Destination IP" + p.dst_ip +"Length" + p.len+ "Data" + p.data;
            System.out.println(s);

        }

        //Get the link layer data header: If you want to capture or forge data packets in the local network, hehe  
        DatalinkPacket datalink = packet.datalink;

        //If it is an Ethernet packet  
        if (datalink instanceof jpcap.packet.EthernetPacket) {

            EthernetPacket ep = (EthernetPacket) datalink;

            String s = "datalink layer packet:"
                    + "|DestinationAddress: " + ep.getDestinationAddress()
                    + "|SourceAddress: " + ep.getSourceAddress();

            System.out.println(s);

        }

    }

}
