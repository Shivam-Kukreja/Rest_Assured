package Test_Runner;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Test_Base.Base_Class;

public class Get extends Base_Class  {
	Base_Class obj= new Base_Class();
	@BeforeSuite
	public void initialize() {
		obj.Logging();
	}
	@Test
	public void Get_AllList() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("GetAllLists");
		obj.response("GetAllLists");
	}
	@Test
	public void Get_SpecificList() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("SpecificList");
		obj.response("SpecificList");
		
	}
	@AfterMethod
	public void afterMethod() {
		if (reports != null) {
			reports.flush();
		}
	}
}
