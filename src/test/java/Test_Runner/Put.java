package Test_Runner;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Test_Base.Base_Class;

public class Put extends Base_Class {
	Base_Class obj= new Base_Class();
	@BeforeSuite
	public void initialize() {
		obj.Logging();
	}
	@Test
	public void Put_List() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("UpdatePutList");
		obj.response("UpdatePutList");
	}
	@Test
	public void Put_Task() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("UpdatePutTask");
		obj.response("UpdatePutTask");
	}
	@AfterMethod
	public void afterMethod() {
		if (reports != null) {
			reports.flush();
		}
	}
}
