package steganography;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import data.User;
import exceptions.WrongLoginInformationsException;
import services.SerializationService;

public class PasswordEncryptorDecryptor {

	private static File passwordsFilePath;
	private static HashMap<User, String> map;

	static {

		passwordsFilePath = new File("." + File.separator + "settings" + File.separator + "settings.file");
		if (passwordsFilePath.exists())
			map = SerializationService.<HashMap<User, String>>unmarshall(passwordsFilePath);
		else {

			map = new HashMap<>();
			SerializationService.<HashMap<User, String>>marshall(map, passwordsFilePath);
		}

	}

	public PasswordEncryptorDecryptor() {
		super();
	}

	public static String encrypt(String data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		SecureRandom r = new SecureRandom();
		String salt = getReadableForm(r.generateSeed(20));
		digest.update(salt.getBytes(StandardCharsets.UTF_8));
		byte[] bytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
		System.out.println(getReadableForm(bytes));
		return getReadableForm(bytes) + "|" + salt;

	}

	public static void verify(User user, String enteredPassword) throws WrongLoginInformationsException  {
		MessageDigest digest=null;;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String salt = map.get(user).split("\\|")[1];
		String hash=map.get(user).split("\\|")[0];
		digest.update(salt.getBytes(StandardCharsets.UTF_8));
		byte[] bytes = digest.digest(enteredPassword.getBytes());
		
		if(!getReadableForm(bytes).equals(hash)) throw new WrongLoginInformationsException();

	}

	private static String getReadableForm(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++)
			buffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		return buffer.toString();

	}

	public static void addPassword(User user, String password) throws NoSuchAlgorithmException {
		
		map.put(user, encrypt(password));
		SerializationService.<HashMap<User, String>>marshall(map, passwordsFilePath);
		
		
		
	}



}
