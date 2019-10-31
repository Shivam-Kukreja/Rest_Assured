package utils.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperty {
	public static Properties prop = null;
	public static File file = null;
	public static FileInputStream fis = null;
	
	static {
		String path = System.getProperty("user.dir");

		file = new File(path+"/resources/config.properties");
		try {
			fis = new FileInputStream(file);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		prop = new Properties();
		try {
			prop.load(fis);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
