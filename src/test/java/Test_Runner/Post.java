package Test_Runner;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Test_Base.Base_Class;

public class Post extends Base_Class {
	Base_Class obj= new Base_Class();
	@BeforeSuite
	public void initialize() {
		obj.Logging();
	}
	@Test
	public void Create_List() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("CreateList");
		obj.response("CreateList");
	}
	@Test
	public void Create_Task() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("CreateTask");
		obj.response("CreateTask");
	}
	@AfterMethod
	public void afterMethod() {
		if (reports != null) {
			reports.flush();
		}
	}

}
