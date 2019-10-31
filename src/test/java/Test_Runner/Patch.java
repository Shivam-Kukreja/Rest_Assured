package Test_Runner;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import Test_Base.Base_Class;

public class Patch {
	Base_Class obj= new Base_Class();
	@Test
	public void Patch_List() throws EncryptedDocumentException, InvalidFormatException, IOException {
		obj.Request("UpdatePatchList");
		obj.response("UpdatePatchList");
	}
}
