import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;


public class gobackNserver {
	//Set the probability of ACK loss as 10%
	public static final double ack_loss_pblt = 0.1;

	public static void main(String[] args) throws Exception{
		//Set the server socket
		DatagramSocket serversocket = new DatagramSocket(9876);
		//New byte array for data received
		byte[] receivedData = new byte[1024];
		
		int expectedSN = 0;
		//All packets received by server, to help get the packet sequence number
		ArrayList<RDTPacket> received = new ArrayList<RDTPacket>();
		//To distinguish different actions when the packet is or is not the last one
		boolean toEnd = false;
		
		//Initial assumption: it is the last one. 
		while(!toEnd){
			//Server waits for connection with client. 
			//Server indicates it is ready.
			System.out.println("Waiting ...");
			//Create packet sent from the client
			DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
			//Receive the packet from the client 
			serversocket.receive(receivedPacket);
			
			// Unserialize to a RDTPacket object
			RDTPacket packet = (RDTPacket) Serializer.toObject(receivedPacket.getData());
			//Adjust to make sequence numbers of packet and ACK coincide
			//Just for reading convenience
			int SN_adjust = packet.getSeq()+1;
			System.out.println("Packet SN = " + SN_adjust);
		
			//Case 1: the packet is the last one
			if(packet.getSeq() == expectedSN && packet.isLast()){
				//Increment the expected sequence number
				expectedSN++;
				//Add the received packet to the final one to be printed
				received.add(packet);
				
				System.out.println("The last character has been received.");
				
				toEnd = true;
				
			}
			//Case2: the sequence number of the packet received is the same with expected one
			else if(packet.getSeq() == expectedSN){
				//Increment the expected sequence number
				expectedSN++;
				received.add(packet);
				System.out.println("Packed stored in buffer");
			}else{
				//Case 3: the number is not expected, which means this packet is out of order
				System.out.println("Discard the out-of-order packet.");
			}
			
			// Create an RDTAck object
			RDTAck ackObject = new RDTAck(expectedSN);
			
			// Serialize
			byte[] ackBytes = Serializer.toBytes(ackObject);
			
			//Create ACK packet sent from the server
			DatagramPacket ackPacket = new DatagramPacket(ackBytes, ackBytes.length, receivedPacket.getAddress(), receivedPacket.getPort());
			
			//According to the set probability, random ACK loss happens
			if(Math.random() > ack_loss_pblt){
				//Send the ACK if not lost
				serversocket.send(ackPacket);
			}else{
				System.out.println("ACK ackSN =  " + ackObject.getPacket() + " has lost.");
			}
			
			System.out.println("ACK ackSN = " + expectedSN + " is sent.");
			

		}
		
		// Print the data received
		System.out.println(" Transmission completed. ");
		
		for(RDTPacket p : received){
			for(byte b: p.getData()){
				//Print the complete textfile.
				System.out.print((char) b);
			}
		}
	}
	
	
}