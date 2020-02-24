package steganography;

import java.io.File;
import java.io.IOException;

public interface Decoder {
	
	public Message decode(File image) throws IOException;
	

}
