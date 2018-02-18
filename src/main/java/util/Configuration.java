package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	
	private static Properties prop;
	
	public static void loadConfiguration(){
		File file = new File("src/main/resources/config.properties");
		
		InputStream is=null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		prop = new Properties();
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getBrowser(){
		return prop.getProperty("browser");
	}
	public static String getURL(){
		return prop.getProperty("url");
	}
	public static String getFilePath(){
		return prop.getProperty("filePath");
	}
	public static String getSheetName(){
		return prop.getProperty("sheetName");
	}
	public static String getEmail(){
		return prop.getProperty("emailId");
	}
	public static String getCurrentOverStockPassword(){
		return prop.getProperty("currentStockOverPassword");
	}
	public static String getNewOverStockPassword(){
		return prop.getProperty("newStockOverPassword");
	}
	public static String getEmailPassword(){
		return prop.getProperty("emailPassword");
	}
}
