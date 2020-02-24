package steganography;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import exceptions.WrongSizeException;
import factory.AlertFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class BasicEncoder implements Encoder {

	private ArrayList<Integer> buffer;
	private ArrayList<Integer> modifiedPixelsList;
	private int modifiedPixels;
	private static File messagesFolder;

	static {
		messagesFolder = new File("." + File.separator + "messages");
		if (!messagesFolder.exists())
			messagesFolder.mkdir();

	}

	public BasicEncoder() {
		super();
		modifiedPixels = 0;
		buffer = new ArrayList<>();
		modifiedPixelsList = new ArrayList<>();

	}

	@Override
	public void encode(File img, Message msg) throws IOException, WrongSizeException {

		BufferedImage bi = createUserSpace(ImageIO.read(img));
		if ((addSeparators(msg).length() * 8 + 32) > bi.getWidth() * bi.getHeight() * 3)
			throw new WrongSizeException();
		else {

			loadBuffer(bi);
			ArrayList<Boolean> bitsToWrite = MessageToBitsConvertor.getConvertedMessage(addSeparators(msg));
			StringBuffer b = new StringBuffer();
			for (int i = 0; i < bitsToWrite.size(); i++) {
				if (bitsToWrite.get(i))
					b.append('1');
				else
					b.append('0');

			}
			System.out.println(b);
			encodeText(bi, bitsToWrite);
			writeBufferToImage(bi);
			test(bi);

			writeImage(bi, msg, img);

		}

	}

	private BufferedImage createUserSpace(BufferedImage img) {
		BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		// graphic.drawImage(img,0,0,null);
		graphic.drawRenderedImage(img, null);
		graphic.dispose();
		img.flush();
		return image;

	}

	private void writeImage(BufferedImage image, Message msg, File img) {
		Alert alert = null;
		try {
			ImageIO.write(image, "png", new File(createMessagePathForUser(msg).toString()+File.separator+img.getName()+".png"));
			 alert = AlertFactory.getAlert("Steganography Tool", "Message successfuly sent!", AlertType.INFORMATION);
			 alert.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// alert = AlertFactory.getAlert("Steganography Tool", "Could not sent message
			// due to I/O error!",
			// AlertType.ERROR);
			// alert.showAndWait();
			
		}

	}

	private void encodeText(BufferedImage img, ArrayList<Boolean> message) {

		ListIterator<Boolean> it = message.listIterator();

		while (it.hasNext()) {

			int argb = buffer.get(modifiedPixels);

			int newArgb = argb;
			try {
				newArgb = changeBitValue(argb, 0, getBooleanValueOfChar(it.next())); // blue//
			} catch (NoSuchElementException e) {
				modifiedPixelsList.add(newArgb);
				break;
			}

			try {
				newArgb = changeBitValue(newArgb, 8, getBooleanValueOfChar(it.next())); // green//
			} catch (NoSuchElementException e) {
				modifiedPixelsList.add(newArgb);
				break;
			}

			try {
				newArgb = changeBitValue(newArgb, 16, getBooleanValueOfChar(it.next())); // red//
			} catch (NoSuchElementException e) {
				modifiedPixelsList.add(newArgb);
				break;
			}

			modifiedPixelsList.add(newArgb);
			modifiedPixels++;

		}

	}

	private void loadBuffer(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				buffer.add(bi.getRGB(i, j));

	}

	private static String addLeadingZeroInteger(String byteString) {

		StringBuffer buffer = new StringBuffer();
		int textLength = byteString.length();
		while (textLength++ != 32)
			buffer.append('0');
		return buffer.append(byteString).toString();

	}

	public static int changeBitValue(int number, int position, char newValue) {
		StringBuffer buffer = new StringBuffer(addLeadingZeroInteger(Integer.toBinaryString(number)));
		if (buffer.charAt(buffer.length() - position - 1) != newValue)
			return number ^ (1 << position);
		return number;

	}

	private char getBooleanValueOfChar(boolean value) {

		char c;
		if (value)
			c = '1';
		else
			c = '0';
		return c;

	}

	private void writeBufferToImage(BufferedImage bi) {
		Iterator<Integer> it = modifiedPixelsList.iterator();
		int width = bi.getWidth();
		int height = bi.getHeight();

		try {
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {
					bi.setRGB(i, j, it.next());

				}

		} catch (NoSuchElementException e) {
			System.out.println("Completed!");
		}

	}

	public static void main(String[] args) {

	}

	private String addSeparators(Message msg) {
		return "" + msg.getFrom() + "#" + msg.getTo() + "#" + msg.getTime() + "#" + msg.getMessage();

	}

	private void test(BufferedImage bi) {

	}

	private File createMessagePathForUser(Message msg) {
		File path = new File(messagesFolder.toString() + File.separator + msg.getTo());
		if (!path.exists())
			path.mkdir();
		return path;

	}

}
