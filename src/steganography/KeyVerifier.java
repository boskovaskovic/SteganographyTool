package steganography;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;


import data.User;


public class KeyVerifier {

	public KeyVerifier() {
		super();
	}
	
	private static boolean verify(String plaintext, String encryptedData, PublicKey pk)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		Signature s = Signature.getInstance("SHA256withRSA");
		s.initVerify(pk);
		s.update(plaintext.getBytes(StandardCharsets.UTF_8));
		byte[] signatureBytes = Base64.getDecoder().decode(encryptedData);

		return s.verify(signatureBytes);

	}

	private static String sign(String plaintext, PrivateKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(key);
		signature.update(plaintext.getBytes(StandardCharsets.UTF_8));
		byte[] signedData = signature.sign();
		return Base64.getEncoder().encodeToString(signedData);

	}
	
	
	public static boolean checkKey(User user, File privateKey) throws InvalidKeySpecException, InvalidKeyException, SignatureException {
		X509Certificate cert = user.getCertificate();
		KeyFactory factory;
		boolean outcome=false;
		try {
			factory=KeyFactory.getInstance("RSA");
			String data=new String(Files.readAllBytes(privateKey.toPath())).replace("\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
			PKCS8EncodedKeySpec pkcs8=new PKCS8EncodedKeySpec(Base64.getDecoder().decode(data));
			PrivateKey privKey=factory.generatePrivate(pkcs8);
			PublicKey publicKey=cert.getPublicKey();
			SecureRandom r=new SecureRandom();
			byte[] bitsToEncrypt=r.generateSeed(30);
			String dataToncrypt=Base64.getEncoder().encodeToString(bitsToEncrypt);
			String signedData=sign(dataToncrypt, privKey);
			outcome=verify(dataToncrypt,signedData, publicKey);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outcome;
		
	}
	

}
