package Test_Base;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.datasource.ExcelLib;
import utils.datasource.LoadProperty;

public class Base_Class {
	public static RequestSpecification httpreq;
	public static Response rep;
	ExcelLib obj= new ExcelLib();
	public void Request(String req) throws EncryptedDocumentException, InvalidFormatException, IOException {
		RestAssured.baseURI=LoadProperty.prop.getProperty("URI");
		httpreq=RestAssured.given();
		if(req.equalsIgnoreCase("GetAllLists")) {
			httpreq=Test_Util.pass_header(httpreq);
		}
		else if(req.equalsIgnoreCase("CreateList")) {
			httpreq = Test_Util.pass_header(httpreq);
			httpreq.body(Test_Util.data(req));
		}
		else if(req.equalsIgnoreCase("SpecificList")) {
			
			httpreq=Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id", obj.exceldata("Sheet1", 1, 3), httpreq);
		}
		else if (req.equalsIgnoreCase("UpdatePatchList") || req.equalsIgnoreCase("UpdatePutList") )
		{	
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id",obj.exceldata("Sheet1", 1, 3), httpreq);
			httpreq.body(Test_Util.data(req));
			httpreq.log().all();
		}
		else if (req.equalsIgnoreCase("DeleteList")) {
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id", obj.exceldata("Sheet1", 1, 3), httpreq);
			Test_Util.pass_queryparm("revision", obj.exceldata("Sheet1", 1, 1), httpreq);
		}
		else if (req.equalsIgnoreCase("CreateTask")) {
			httpreq = Test_Util.pass_header(httpreq);
			httpreq.body(Test_Util.data(req));		
		}
		else if (req.equalsIgnoreCase("UpdatePatchTask") || req.equalsIgnoreCase("UpdatePutTask") )
		{	
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id",obj.exceldata("Sheet1", 1, 5), httpreq);
			httpreq.body(Test_Util.data(req));
			httpreq.log().all();
		}
		else if (req.equalsIgnoreCase("DeleteTask")) {
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id", obj.exceldata("Sheet1", 1, 5), httpreq);
			Test_Util.pass_queryparm("revision", obj.exceldata("Sheet1", 1, 6), httpreq);
		}
	}
	
	public boolean response(String req) {
		if(req.equalsIgnoreCase("GetAllLists"))  {
			try {
			rep = httpreq.when().get("lists");
			rep.then().log().all().assertThat().statusCode(200);
			return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("SpecificList")) {
			try {
			rep= httpreq.when().get("lists/{id}");
			rep.then().log().all().assertThat().statusCode(200);
			return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("CreateList")){
			try {
				rep = httpreq.when().post("lists");
				rep.then().log().all().assertThat().statusCode(201);
				obj.UpdateListID(rep.jsonPath().get("id"));
				obj.UpdateListRevision(rep.jsonPath().get("revision"));
				return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("UpdatePatchList")) {
			try {
				rep = httpreq.when().patch("lists/{id}");
				rep.then().log().all().statusCode(200);
				obj.IncrementListRevision();
				return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("UpdatePutList")) {
				try {
					rep = httpreq.when().put("lists/{id}");
					rep.then().log().all().statusCode(200);
					obj.IncrementListRevision();
					return true;
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
					return false;
				}
		}
		else if(req.equalsIgnoreCase("DeleteList")) {
			try {
				rep = httpreq.when().delete("lists/{id}");
				rep.then().log().all().statusCode(204);
				return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("CreateTask")){
			try {
				rep = httpreq.when().post("tasks");
				rep.then().log().all().assertThat().statusCode(201);
				obj.UpdateTaskID(rep.jsonPath().get("id"));
				obj.UpdateTaskRevision(rep.jsonPath().get("revision"));
				return true;
				}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
				}
			}
		else if(req.equalsIgnoreCase("UpdatePatchTask")) {
		try {
			rep = httpreq.when().patch("tasks/{id}");
			rep.then().log().all().statusCode(200);
			obj.IncrementTaskRevision();
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	else if(req.equalsIgnoreCase("UpdatePutTask")) {
			try {
				rep = httpreq.when().put("tasks/{id}");
				rep.then().log().all().statusCode(200);
				obj.IncrementTaskRevision();
				return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	else if(req.equalsIgnoreCase("DeleteTask")) {
		try {
			rep = httpreq.when().delete("tasks/{id}");
			rep.then().log().all().statusCode(204);
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	else
		return false;
	}
}	