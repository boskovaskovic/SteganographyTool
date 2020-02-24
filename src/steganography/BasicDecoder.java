package steganography;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import exceptions.WrongSizeException;
import javafx.scene.image.Image;

public class BasicDecoder implements Decoder {
	private ArrayList<Integer> buffer;
	private final int bitLengthOfMessage = 32;

	public BasicDecoder() {
		super();
		buffer = new ArrayList<>();

	}

	@Override
	public Message decode(File file) throws IOException {
		BufferedImage bi = createUserSpace(ImageIO.read(file));

		loadBuffer(ImageIO.read(file));
		int messageSize = getSizeOfMessage(bi);// num of characters in message//

		return constructMessage(getMessage(messageSize));
	}

	private void loadBuffer(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				Color c = new Color(bi.getRGB(i, j));
				buffer.add(c.getBlue());
				buffer.add(c.getGreen());
				buffer.add(c.getRed());

			}

	}

	private BufferedImage createUserSpace(BufferedImage img) {
		BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		graphic.drawRenderedImage(img, null);
		graphic.dispose();
		return image;

	}

	public static void main(String[] args) {

		BasicDecoder decoder = new BasicDecoder();

		try {
			System.out.println(decoder.decode(new File("D:" + File.separator + "cekalicaAU.png")));;
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	}

	private int getSizeOfMessage(BufferedImage bi) {
		StringBuffer sizeBuffer = new StringBuffer();
		for (int i = 0; i < bitLengthOfMessage; i++) {
			String s = addLeadingZeroByte(Integer.toBinaryString(buffer.get(i)));
			sizeBuffer.append(s.charAt(7));
		}

		return Integer.parseInt(sizeBuffer.toString(), 2);
	}

	private String getMessage(int messageSize) {
		StringBuffer sizeBuffer = new StringBuffer();
		StringBuffer message = new StringBuffer();
		int position = bitLengthOfMessage;
		for (int i = 0; i < messageSize; i++) {
			for (int j = 0; j < 8; j++) {
				String s = addLeadingZeroByte(Integer.toBinaryString(buffer.get(position++)));
				sizeBuffer.append(s.charAt(7));

			}
			message.append(Character.toString((char) Integer.parseInt(sizeBuffer.toString(), 2)));
			sizeBuffer.setLength(0);

		}

		return message.toString();

	}

	private String addLeadingZeroByte(String byteString) {

		StringBuffer buffer = new StringBuffer();
		int textLength = byteString.length();
		while (textLength++ != 8)
			buffer.append('0');
		return buffer.append(byteString).toString();

	}
	
	private Message constructMessage(String rawMessage) {
		Message msg=new Message();
		String[] values=rawMessage.split("#");
		msg.setFrom(values[0]);
		msg.setTo(values[1]);
		msg.setTime(LocalTime.parse(values[2]));
		msg.setMessage(values[3]);
		return msg;
	}

}
