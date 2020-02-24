/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Predicate;

import data.User;
import data.Users;
import services.SerializationService;
import steganography.CertificateManipulation;

/**
 *
 * @author Bosko Vaskovic
 */
public class Util {
	private static File path;

	static {
		path = new File("." + File.separator + "users" + File.separator + "users.list");

	}

	public static User checkUser(String username, String password) throws FileNotFoundException, IOException {
		User requested = null;
		HashSet<User> users = Users.getAllUsers();
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User usr = it.next();
			if (usr.equals(new User(username, password)))
				requested = usr;
		}

		return requested;
	}

	public static void checkCertificate(User user) throws CertificateExpiredException, CertificateNotYetValidException {

		X509Certificate cert = user.getCertificate();
		cert.checkValidity();
	}

	public static void checkIfSignedByCA(User user) throws InvalidKeyException, CertificateException,
			NoSuchAlgorithmException, NoSuchProviderException, SignatureException {

		X509Certificate certCA = CertificateManipulation.getCertificateFromFile(
				new File("." + File.separator + "certs" + File.separator + "cacert" + File.separator + "caCert.pem"));

		X509Certificate cert = user.getCertificate();
		;
		cert.verify(certCA.getPublicKey());

	}

	public static void main(String[] args) {

		try {

			System.out.println(checkUser("bosko", "bosko"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
