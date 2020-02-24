/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.Serializable;
import java.security.cert.X509Certificate;

/**
 *
 * @author Bosko Vaskovic
 */
public class User implements Serializable {
	private String name;
	private String surname;
	private String username;
	private transient String password;
	private File certificatePath;
	private File messagesPath;
	private X509Certificate certificate;

	public User() {
		super();

	}
	
	

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}



	public final String getName() {
		return name;

	}

	public final String getSurname() {
		return surname;
	}

	public final void setSurname(String surname) {
		this.surname = surname;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final File getCertificatePath() {
		return certificatePath;
	}

	public final void setCertificatePath(File certificatePath) {
		this.certificatePath = certificatePath;
	}

	public final X509Certificate getCertificate() {
		return certificate;
	}

	public final void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final File getMessagesPath() {
		return messagesPath;
	}

	public final void setMessagesPath(File messagesPath) {
		this.messagesPath = messagesPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", username=" + username + ", certificatePath="
				+ certificatePath + ", certificate=" + certificate + "]";
	}

}
