package steganography;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertificateManipulation {

	public CertificateManipulation() {
		super();
	}

	public static X509Certificate getCertificateFromFile(File file) {
		X509Certificate cert = null;
		CertificateFactory factory = null;
		try (FileInputStream fis = new FileInputStream(file);) {
			factory = CertificateFactory.getInstance("x509");
			cert = (X509Certificate) factory.generateCertificate(fis);

		} catch (IOException | CertificateException e) {
			e.printStackTrace();
		}
		return cert;
	}

}
