import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;


public class gobackNclient {

	//Set the maximum segment size as 1 so that the text file can be sent char by char
	public static final int MSS = 1;
	//Set the probability of packet loss as 10%
	public static final double pkt_loss_pblt = 0.1;
	//Set the sliding window size as 5: pipeline, 5
	public static final int WINDOW_SIZE = 5;


	public static void main(String[] args) throws Exception{
		
		//Set the initial number just before the nextseqnum [nextseqnum-1]
		//Sequence number of the last packet sent but not acknowledged
		int nextSN_1 = 0;
		//Set the initial number just before the base [base-1]
		//Sequence number of the last packet transmitted and acknowledged
		int base_1 = 0;

		//New byte array for the textfile
		byte[] fileBytes = "UMBRELLA".getBytes();

		System.out.println("Ready to send UMBRELLA");

		//Sequence number of the last packet to be sent
		//To help decide when to close the client socket
		int lastSeq = (int) Math.ceil( (double) fileBytes.length / MSS);

		//Set the client socket
		DatagramSocket client_socket = new DatagramSocket();
		//Get the IP address of server's host
		InetAddress IPAddress = InetAddress.getByName("localhost");

		//All packets sent by client, to help get the packet sequence number
		ArrayList<RDTPacket> sent = new ArrayList<RDTPacket>();

		//Loop until break
		while(true){
			
			//Actions for packets sent but not acknowledged 
			while(nextSN_1 - base_1 < WINDOW_SIZE && nextSN_1 < lastSeq){

				//New byte array to store one character to send
				byte[] char_send = new byte[MSS];

				// Copy segment of data bytes to array
				char_send = Arrays.copyOfRange(fileBytes, nextSN_1*MSS, nextSN_1*MSS + MSS);

				// Create RDTPacket object
				RDTPacket rdtPacketObject = new RDTPacket(nextSN_1, char_send, (nextSN_1 == lastSeq-1) ? true : false);

				// Serialize the RDTPacket object
				byte[] sendData = Serializer.toBytes(rdtPacketObject);

				//Create packet sent from the client
				DatagramPacket sendpacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876 );
				
				//Adjust to make sequence numbers of packet and ACK coincide
				//Just for reading convenience
				int SN_adjust = nextSN_1 + 1;
				System.out.println("Sending packet SN = " + SN_adjust);

				//Add the packet just sent to the list
				//For recording the sequence number of packets
				sent.add(rdtPacketObject);
		
				//According to the set probability, random packet loss happens
				if(Math.random() > pkt_loss_pblt){
					//Send the packet if no pack loss
					client_socket.send(sendpacket);
				}else{
					//Print the sequence number of lost packet on screen
					System.out.println("Packet SN = " + SN_adjust + " has lost.");
				}

				//Continue to slide the window to send the next packet
				nextSN_1++;
			}
			
			//New byte array for the received packet (ACK from the server)
			byte[] ackBytes = new byte[40];
			
			///Create packet to be received (ACK) 
			DatagramPacket ack = new DatagramPacket(ackBytes, ackBytes.length);
			
			try{
				//Set timeout to determine to resend data or not
				client_socket.setSoTimeout(25);
				
				//Receive the packet (ACK) from the server 
				client_socket.receive(ack);
				
				// Unserialize the RDTAck object
				RDTAck ackObject = (RDTAck) Serializer.toObject(ack.getData());
				
				//Print the sequence number of ACK on screen
				System.out.println("Received ACK for " + ackObject.getPacket());
				
				//Decide if it is the last packet; if it is, end the loop
				if(ackObject.getPacket() == lastSeq){
					break;
				}
				
				//Reset the base sequence number of the last packet transmitted and acknowledged [base]
				base_1 = Math.max(base_1, ackObject.getPacket());
				
			}catch(SocketTimeoutException e){
				//if timeout, resend all packets sent but not acknowledged
				for(int i = base_1; i < nextSN_1; i++){
					
					// Serialize the RDTPacket object
					byte[] sendData = Serializer.toBytes(sent.get(i));

					//Create packet to be resent from the client
					DatagramPacket sendpacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876 );
					
					//According to the set probability, random packet loss happens
					if(Math.random() > pkt_loss_pblt){
						//Resend the packet if no pack loss
						client_socket.send(sendpacket);
					}else{
						//Adjust to make sequence numbers of packet and ACK coincide
						//Just for reading convenience
						int SN_adjust2= sent.get(i).getSeq() + 1;
						
						System.out.println("Packet SN = " + SN_adjust2 + " has lost.");
					}
					//Adjust to make sequence numbers of packet and ACK coincide
					//Just for reading convenience
					int SN_adjust3= sent.get(i).getSeq() + 1;
					
					System.out.println("REsending packet SN = " + SN_adjust3);
				}
			}
			
		
		}
		
		System.out.println("Finished transmission");
		
		///close the client socket
		client_socket.close();
	}

}