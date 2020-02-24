package data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

import services.SerializationService;
import steganography.CertificateManipulation;

public class Users {

	private static HashSet<User> users;
	private static File usersFile;

	static {
		usersFile = new File("." + File.separator + "users" + File.separator + "users.list");
		if (!usersFile.exists()) {
			users = new HashSet<>();
			SerializationService.<HashSet<User>>marshall(users, usersFile);
		} else {

			users = SerializationService.<HashSet<User>>unmarshall(usersFile);
			
		}

	}

	public static boolean addUser(User user) {
		boolean outcome = false;
		if (users.add(user)) {
			SerializationService.<HashSet<User>>marshall(users, usersFile);
			outcome = true;
		}
		return outcome;

	}

	public static HashSet<User> getAllUsers() {
		return users;
	}

	public static void main(String[] args) {

		User admin = new User();
		admin.setName("Marko");
		admin.setSurname("Markovic");
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setCertificatePath(new File(
				"." + File.separator + "certs" + File.separator + "userCerts" + File.separator + "adminCert.pem"));
		admin.setCertificate(CertificateManipulation.getCertificateFromFile(admin.getCertificatePath()));
		try {
			admin.setMessagesPath(Files
					.createDirectories(
							Paths.get("." + File.separator + "messages" + File.separator + admin.getUsername()))
					.toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User bosko = new User();
		bosko.setName("Bosko");
		bosko.setSurname("Vaskovic");
		bosko.setUsername("bosko");
		bosko.setPassword("bosko");
		bosko.setCertificatePath(new File("." + File.separator + "certs" + File.separator + "userCerts" + File.separator + "boskoVaskovic.pem"));
		bosko.setCertificate(CertificateManipulation.getCertificateFromFile(bosko.getCertificatePath()));
		try {
			bosko.setMessagesPath(Files
					.createDirectories(
							Paths.get("." + File.separator + "messages" + File.separator + bosko.getUsername()))
					.toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addUser(bosko);
		addUser(admin);

	}

}
