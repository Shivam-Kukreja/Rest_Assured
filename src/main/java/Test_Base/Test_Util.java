package Test_Base;

import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import io.restassured.specification.RequestSpecification;
import utils.datasource.ExcelLib;
import utils.datasource.LoadProperty;

public class Test_Util {
	static ExcelLib obj1= new ExcelLib();
	public static RequestSpecification pass_header(RequestSpecification httpreq) {
		HashMap<String,String> header=new HashMap<String,String>();
		header.put("Content-Type", LoadProperty.prop.getProperty("Content_type"));
		header.put("X-Client-ID", LoadProperty.prop.getProperty("Cliend_ID"));
		header.put("X-Access-Token",LoadProperty.prop.getProperty("Token"));
		return httpreq.headers(header);
	}
	
	public static RequestSpecification pass_queryparm(String key,String value,RequestSpecification httpreq) {
		return httpreq.queryParam(key, value);
	}
	
	public static RequestSpecification pass_pathvar(String key,String value,RequestSpecification httpreq) {
		return httpreq.pathParam(key, value);
	}
	
	public static HashMap<String,Object> data(String req) throws EncryptedDocumentException, InvalidFormatException, IOException{
		HashMap<String, Object> body = new HashMap<String, Object>();
		if(req.equalsIgnoreCase("createList")) {
			body.put("title",obj1.exceldata("Sheet1",1,0));
			return body;
		}
		else if(req.equalsIgnoreCase("UpdatePatchList") || req.equalsIgnoreCase("UpdatePutList")) {
			body.put("title", obj1.exceldata("Sheet1",1,2));
			body.put("revision", Integer.parseInt(obj1.exceldata("Sheet1",1,1)));
			return body;
		}
		else if(req.equalsIgnoreCase("CreateTask")) {
			body.put("list_id",Integer.parseInt(obj1.exceldata("Sheet1",1,3)));
			body.put("title", obj1.exceldata("Sheet1",1,4));
			return body;
		}
		else if(req.equalsIgnoreCase("UpdatePatchTask") || req.equalsIgnoreCase("UpdatePutTask")) {
			body.put("revision", Integer.parseInt(obj1.exceldata("Sheet1",1,6)));
			body.put("title", obj1.exceldata("Sheet1",1,7));
			return body;
		}
		return body;
	}
}
