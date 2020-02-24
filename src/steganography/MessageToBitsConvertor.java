package steganography;


import java.util.ArrayList;


public class MessageToBitsConvertor {

	public MessageToBitsConvertor() {
		super();
	}

	private static String addLeadingZeroInt(String byteString) {

		StringBuffer buffer = new StringBuffer();
		int textLength = byteString.length();
		while (textLength++ != 32)
			buffer.append('0');
		return buffer.append(byteString).toString();

	}
	private static String addLeadingZeroByte(String byteString) {

		StringBuffer buffer = new StringBuffer();
		int textLength = byteString.length();
		while (textLength++ != 8)
			buffer.append('0');
		return buffer.append(byteString).toString();

	}

	public static ArrayList<Boolean> getConvertedMessage(String message) {
		ArrayList<Boolean> queue = new ArrayList<>();
		byte[] messageBytes = message.getBytes();
		String encodedSize = addLeadingZeroInt(Integer.toBinaryString(message.length()));
		
		for (int i = 0; i < encodedSize.length(); i++)
			queue.add(encodedSize.charAt(i) == '1'); // add size to header//
		
		
		//message conversion --->asci character code//
		for(int i=0;i<messageBytes.length;i++) {
			String rawBits=addLeadingZeroByte(Integer.toBinaryString(messageBytes[i]));
			for (int j = 0; j < rawBits.length(); j++)
				queue.add(rawBits.charAt(j) == '1'); 
			
		}
		
		
		return queue;
	}



}
