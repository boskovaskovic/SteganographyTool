package factory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class AlertFactory {

	public AlertFactory() {
		super();
	}
	
	public static Alert getAlert(String title, String text,AlertType type) {
		
		Alert alert=new Alert(type);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.setTitle(title);
		return alert;
		
		
	}

}
