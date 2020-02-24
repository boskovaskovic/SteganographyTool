/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import data.User;
import data.Users;


import java.io.File;
import java.io.FileFilter;
import java.net.URL;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import org.controlsfx.control.Notifications;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import services.DecryptService;
import services.EncryptService;
import services.FolderWatchingService;
import steganography.KeyVerifier;
import steganography.Message;

/**
 * FXML Controller class
 *
 * @author Bosko Vaskovic
 */
public class MainController implements Initializable {

	@FXML
	private ImageView closeButtonMain;

	@FXML
	private JFXListView<User> userList;

	@FXML
	private JFXButton sendMessageButton;

	@FXML
	private JFXTextField photographyField;

	@FXML
	private JFXButton loadPicButton;

	@FXML
	private TextField textBox;

	@FXML
	private Label userLabel;

	@FXML
	private JFXButton decryptButton;

	@FXML
	private Label newMessagesLabel;

	private String imagePath;
	
	private ArrayList<File> imageFiles;
	private FolderWatchingService messagesWatcher;
    private User user=LoginViewController.user;
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		loadDefault();
		loadUsers();
		checkForMessages();
		setButtons();
		messagesWatcher = new FolderWatchingService();

		Task<Void> t=new Task<Void>(
				
				) {

					@Override
					protected Void call() throws Exception {
						messagesWatcher.run();
						return null;
					}
		};
		new Thread(t).start();
			
			
			
		
	}

	@FXML
	private void close() {

		System.exit(0);
		messagesWatcher.setEndOfService();
	}

	@FXML
	private void loadPhoto() {
		Stage t = (Stage) loadPicButton.getScene().getWindow();
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Choose photo to hide text..");
		filechooser.setInitialDirectory(new File(System.getProperty("user.home")));
		filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
		try {
			imagePath = filechooser.showOpenDialog(t).toString();
			photographyField.setText(imagePath);
		} catch (NullPointerException e) {
			photographyField.setText("");

		}

	}

	private void loadDefault() {
		userLabel.setText("Welcome, " + user.getUsername());
		loadPicButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadPhoto();

			}

		});
		userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			@Override
			public ListCell<User> call(ListView<User> param) {
				ListCell<User> temp = new ListCell<User>() {

					@Override
					public void updateItem(User item, boolean btl) {
						super.updateItem(item, btl);
						if (item != null) {
							ImageView image = new ImageView(new Image("/images/userBig2.png"));
							setGraphic(image);
							setText(item.getUsername());
						} else {
							setText(null);
							setGraphic(null);
						}

					}

				};

				return temp;
			}
		});

		userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (!textBox.getText().equals("") && !textBox.getText().equals(null)
						&& !photographyField.getText().equals("") && !photographyField.getText().equals(null)) {
					sendMessageButton.setDisable(false);
				} else {
					sendMessageButton.setDisable(true);
				}
			}

		});
		textBox.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals(null) && !newValue.equals("")
						&& userList.getSelectionModel().getSelectedItem() != null
						&& !photographyField.getText().equals("")) {
					sendMessageButton.setDisable(false);

				} else {
					sendMessageButton.setDisable(true);
				}

			}

		});

		photographyField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.equals(null) && !newValue.equals("")
						&& userList.getSelectionModel().getSelectedItem() != null && !textBox.getText().equals("")) {
					sendMessageButton.setDisable(false);

				} else {
					sendMessageButton.setDisable(true);
				}

			}

		});

		sendMessageButton.disableProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

			}

		});
	}

	private void checkForMessages() {

		imageFiles = DecryptService.getMessagesLocation(user);
		if (imageFiles != null) {
			newMessagesLabel.setText("New messages: " + imageFiles.size());
			decryptButton.setDisable(false);
		} else
			newMessagesLabel.setText("New messages: 0");

	}

	private void setButtons() {
		decryptButton.setOnAction(new EventHandler<ActionEvent>(

		) {

			@Override
			public void handle(ActionEvent event) {
				ChoiceDialog<File> dialog=new ChoiceDialog<>();
				dialog.getItems().addAll(getExistingKeys());
				dialog.setTitle("Steganography tool");
				dialog.setHeaderText(null);
				dialog.setContentText("Select your private key: ");
				
				dialog.showAndWait();
				File choiced=dialog.getSelectedItem();
				try {
					if(KeyVerifier.checkKey(user, choiced)) {

						Notifications builder = Notifications.create();

						builder.title("Steganography tool");
						builder.graphic(null);
						builder.hideAfter(Duration.seconds(10));
						builder.position(Pos.TOP_CENTER);
						builder.darkStyle();

						for (File file : imageFiles) {
							Message m = DecryptService.retrieveMessage(file);
							if(m!=null) {
							builder.text("Message from: " + m.getFrom() + "\n" + "Time: " + m.getTime() + "\n" + "Message: "
									+ m.getMessage());

							builder.showInformation();
							}

						}

						Platform.runLater(() -> {
							newMessagesLabel.setText("New messages: 0");
							decryptButton.setDisable(true);
							 DecryptService.deleteFiles(imageFiles);
						});
						
						
					}
					else {System.out.println("Key is not valid.!");}
				} catch (InvalidKeyException | InvalidKeySpecException | SignatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				

			}

		});
		sendMessageButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Message msg = new Message(user.getUsername(), userList.getSelectionModel().getSelectedItem().getUsername());
				msg.setMessage(textBox.getText());
				msg.setTime(LocalTime.now());
				EncryptService.sendMessage(new File(imagePath), msg);

			}
		});
	}
	
	private void loadUsers() {
		
		Users.getAllUsers().stream()
		.filter(new Predicate<User>() {

			@Override
			public boolean test(User arg0) {
				
				return !arg0.equals(user);
			}
			
		})
		.forEach((s)->{userList.getItems().add(s);});
	}
	
	private  List<File> getExistingKeys(){
		List<File> files=new ArrayList<>();
		File keyPath=new File("."+File.separator+"certs"+File.separator+"userCerts");
		File[] fs=keyPath.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				
				return pathname.toString().endsWith("key")||pathname.toString().endsWith("KEY");
			}
		});
		for(int i=0;i<fs.length;i++)files.add(fs[i]);
		return files;
		
	}

}
