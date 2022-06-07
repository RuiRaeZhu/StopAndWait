import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Serializer {
	
	public static byte[] toBytes(Object obj) throws IOException {
		///implements an output stream in which the data iswritten into a byte array
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		///write an object to stream
		o.writeObject(obj);
		return b.toByteArray();
	}

	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		//create a new ByteArrayInputStream
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		//read from the inputstream
		ObjectInputStream o = new ObjectInputStream(b);
		//read an object from the inputstream
		return o.readObject();
	}


}