import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class server3 {

	public static void main(String args[]) throws Exception{
			//Create the server socket
			DatagramSocket serverSocket = new DatagramSocket(9876);
			
			//Create new byte array to hold the data sent and received
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			
			while(true)
			{
				//Create the new packet to receive from the client
				DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
				//Receive the data from the client
				serverSocket.receive(receivePacket);
				
				//Convert the packet bytes to string for reading convenience
				String sentence = new String(receivePacket.getData());
				System.out.println("FROM CLIENT: " + sentence);
				
				//Get the IP address associated with this packet (client)
				InetAddress IPAddress = receivePacket.getAddress();
				//Get the port number of the client
				int port = receivePacket.getPort();
				//Convert the data sent to upper case
				String capitalizedSentence = sentence.toUpperCase();
				//Convert the data to bytes for packeting
				sendData = capitalizedSentence.getBytes();
				////Create the new packet to send to the client
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				//Send the UPPERCASE data back to the client
				serverSocket.send(sendPacket);
			}
	  }

}
