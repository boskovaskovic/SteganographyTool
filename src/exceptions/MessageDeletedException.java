package exceptions;

import java.nio.file.Path;

import factory.AlertFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageDeletedException extends Exception {

	public MessageDeletedException() {
		// TODO Auto-generated constructor stub
	}

	public MessageDeletedException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MessageDeletedException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MessageDeletedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MessageDeletedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	
public void showMessage(Path path) {
		
		Alert alert=AlertFactory.getAlert("Steganography tool", "Message contained in file: "+path.toString()+" has been deleted!", AlertType.ERROR);
		alert.showAndWait();
		
	}

}
