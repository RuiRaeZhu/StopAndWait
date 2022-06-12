import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class client3 {

public static void main(String args[]) throws Exception
{
		
	//Create the string to send
	String umum = "umbrella";
	//Create the client socket
	DatagramSocket clientSocket = new DatagramSocket();
	//Set the timeout for the socket; To determine when to resend the data
	clientSocket.setSoTimeout( 100 );
	//Get the IP address of the host by the given host name
	InetAddress IPAddress = InetAddress.getByName("localhost");
	//Set the initial sequence number of the packet
	int SN = 0;

	//A for loop to count times and if ends, socket would close
	for (int counter = 0; counter < 8; counter++) {
	while( true && SN < 8 ) {
		
		
		//Create new byte array to hold the data sent and received
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		//Create new character to send the string char by char
		char umchar = umum.charAt(SN);
		//Combine the character with the sequence number of packet
		String umcharsting=Character.toString(umchar) + Integer.toBinaryString(SN);
		//Encode String to Bytes for the next action of sending packet
		sendData = umcharsting.getBytes();
		
		//Create the new packet to send
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress, 9876);
		//Send the packet from the client to the server
		clientSocket.send(sendPacket);
		
		//Create the new packet to receive
		DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
		
		try {
			//Receive the packet from the server
			clientSocket.receive(receivePacket);
			
			//Decode the byte array to String
			String modifiedSentence = new String(receivePacket.getData());
			//Print the data received from the server
			System.out.println("FROM SERVER:  " + modifiedSentence);
			
			//Increment the sequence number every time one packet sent
			SN++;}
		catch( SocketTimeoutException exception ) {
			System.out.println( "Timeout (SN = " + SN + " has lost.)" );}
		}
	}
	
	//Close the client socket
	clientSocket.close();
}

}
