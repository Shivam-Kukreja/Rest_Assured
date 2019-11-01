package Test_Runner;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Test_Base.Base_Class;

public class Delete extends Base_Class {
	Base_Class obj= new Base_Class();
	@BeforeSuite
	public void initialize() {
		obj.Logging();
	}
	@Test (priority=2)
	public void Delete_List() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("DeleteList");
		obj.response("DeleteList");
	}
	@Test (priority=1)
	public void Delete_Task() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("DeleteTask");
		obj.response("DeleteTask");
	}	
	@AfterMethod
	public void afterMethod() {
		if (reports != null) {
			reports.flush();
		}
	}
}
