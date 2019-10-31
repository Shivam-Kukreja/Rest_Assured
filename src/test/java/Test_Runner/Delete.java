package Test_Runner;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import Test_Base.Base_Class;

public class Delete {
	Base_Class obj= new Base_Class();
	//@Test
	public void Delete_List() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("DeleteList");
		obj.response("DeleteList");
	}
	@Test
	public void Delete_Task() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("DeleteTask");
		obj.response("DeleteTask");
	}
	
}
