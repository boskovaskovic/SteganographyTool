package services;

import java.io.File;
import java.io.IOException;

import exceptions.WrongSizeException;
import factory.AlertFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import steganography.BasicEncoder;
import steganography.Message;

public class EncryptService {
	
	private static File messagesFolder;
	
	static {
		
		messagesFolder=new File("."+File.separator+"messages");
		if(!messagesFolder.exists())messagesFolder.mkdir();
		
	}

	public EncryptService() {
		super();
	
	}
	
	public static boolean sendMessage(File img, Message msg) {
		boolean outcome=false;
		BasicEncoder encoder=new BasicEncoder();
		try {
			encoder.encode(img, msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Alert alert=AlertFactory.getAlert("Steganography Tool", "IO Error occured!", AlertType.ERROR);
			alert.showAndWait();
		}
		catch(WrongSizeException e) {e.showMessage();}
		
		return outcome;
	}

}
