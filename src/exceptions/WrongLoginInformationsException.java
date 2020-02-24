package exceptions;

import factory.AlertFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WrongLoginInformationsException extends SteganograpyException {

	public WrongLoginInformationsException() {
		super();
	}

	public WrongLoginInformationsException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WrongLoginInformationsException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public WrongLoginInformationsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public WrongLoginInformationsException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	
	public void showMessage() {
		
		Alert alert=AlertFactory.getAlert("Steganography tool", "Incorrect login informations. Please check again username and password!", AlertType.ERROR);
		alert.showAndWait();
		
	}

}
