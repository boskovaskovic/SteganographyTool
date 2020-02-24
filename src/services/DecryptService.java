package services;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import data.User;
import factory.AlertFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import steganography.BasicDecoder;
import steganography.Message;

public class DecryptService {

	private static File messagesFolder;
	private static ArrayList<File> imageFiles;

	static {

		messagesFolder = new File("." + File.separator + "messages");
		imageFiles = new ArrayList<>();
	}

	public DecryptService() {
		super();
	}

	public static Message retrieveMessage(File img) {

		BasicDecoder decoder = new BasicDecoder();
		try {
			return decoder.decode(img);
		} catch (IOException e) {
			Alert alert=AlertFactory.getAlert("Steganography tool", "Some files are not available!", AlertType.ERROR);
			alert.showAndWait();
			return null;
		}
	}

	public static ArrayList<File> getMessagesLocation(User user) {
		ArrayList<File> imageFiles = null;
		File userFolder = user.getMessagesPath();
		if (userFolder.exists()) {
			File[] files = userFolder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {

					return (pathname.toString().endsWith(".jpg") || pathname.toString().endsWith(".png"));
				}
			});

			if (files.length > 0) {

				imageFiles = new ArrayList<>();
				for (int i = 0; i < files.length; i++)
					imageFiles.add(files[i]);
			}

		}

		return imageFiles;
	}

	public static void deleteFiles(ArrayList<File> imageFiles) {

		for (File file : imageFiles) {
			file.delete();
		}

	}

}
