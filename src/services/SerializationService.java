package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationService {

	public static <T> boolean marshall(T object, File path) {

		boolean result = false;
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(path))) {
			out.writeObject(object);
			result = true;

		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}
		return result;

	}

	public static <T> T unmarshall(File path) {

		T object = null;

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {

			object = (T) in.readObject();
			return object;

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return object;

	}

}
