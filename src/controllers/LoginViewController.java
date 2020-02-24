/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import data.User;
import exceptions.WrongLoginInformationsException;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import steganography.PasswordEncryptorDecryptor;
import util.Util;

/**
 *
 * @author Bosko Vaskovic
 */
public class LoginViewController implements Initializable {

    @FXML
    private AnchorPane topMenu;

    @FXML
    private ImageView registerBtn;

    @FXML
    private ImageView autenticationBtn;

    @FXML
    private ImageView infoBtn;

    @FXML
    private ImageView powerBtn;

    @FXML
    private AnchorPane infoAnchor;

    @FXML
    private AnchorPane registerAnchor;

    @FXML
    private JFXButton registerUserBtn;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField surnameField;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField dateOfBirthField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label succeedLabelRegister;

    @FXML
    private Label usernameTaken;

    @FXML
    private AnchorPane autenticationAnchor;

    @FXML
    private JFXTextField usernameLoginField;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXPasswordField passwordLoginField;

    @FXML
    private Label succeedLabel;

    @FXML
    private Label failedLabel;

    @FXML
    private Label failedLoginLabel;
    
    
    public static User user=null;
    
    

    @FXML
    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                authentication();
            }
        });

    }

    @FXML
    private void close() {

        System.exit(0);

    }

    @FXML
    private void authentication() {

        try {
        	user=util.Util.checkUser(usernameLoginField.getText(), passwordLoginField.getText());
            if (user!=null) {
            	Util.checkIfSignedByCA(user); //check if signed by CA//
            	Util.checkCertificate(user); //checking sertificate of user//
            	
            	PasswordEncryptorDecryptor.verify(user, passwordLoginField.getText());
                succeedLabel.setText("Succeed");
                
                if (!succeedLabel.isVisible()) {
                    succeedLabel.setVisible(true);
                }
                Stage t = (Stage) loginBtn.getScene().getWindow();

                new StageController().createNewStage(t, "/views/Main.fxml", "", false, true);
            } else {
                succeedLabel.setText("Failed!");
                if (!succeedLabel.isVisible()) {
                    succeedLabel.setVisible(true);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateExpiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateNotYetValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongLoginInformationsException e) {
			// TODO Auto-generated catch block
			e.showMessage();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
