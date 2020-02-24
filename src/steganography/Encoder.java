package steganography;

import java.io.File;
import java.io.IOException;

import exceptions.WrongSizeException;
import javafx.scene.image.Image;

public interface Encoder {
	
	public void encode(File img, Message msg) throws IOException,WrongSizeException;
	

}
