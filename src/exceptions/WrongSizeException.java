package exceptions;

import factory.AlertFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WrongSizeException extends SteganograpyException {

	public WrongSizeException() {
		super();
	}

	public WrongSizeException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WrongSizeException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WrongSizeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public WrongSizeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	
	public void showMessage() {
		
		Alert alert=AlertFactory.getAlert("Steganography tool", "Message is too big for this picture. Please choose another picture! ", AlertType.ERROR);
		alert.showAndWait();
		
	}

}
