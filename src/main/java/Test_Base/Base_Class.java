package Test_Base;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.datasource.ExcelLib;
import utils.datasource.LoadProperty;
import utils.report.ExtentReport_Manager;

public class Base_Class {
	public static RequestSpecification httpreq;
	public static Response rep;
	public static Logger logger;
	ExcelLib obj= new ExcelLib();
	public static ExtentReports reports = ExtentReport_Manager.getInstance();
	public static String timestamp;
	public static String testReportFolderPath;
	public ExtentTest test;
	public void Logging() {
	 logger = Logger.getLogger("RestAssured");
	PropertyConfigurator.configure("Log4J.properties");
	logger.setLevel(Level.DEBUG);
	}
	public void Request(String req) throws EncryptedDocumentException, InvalidFormatException, IOException {
		RestAssured.baseURI=LoadProperty.prop.getProperty("URI");
		httpreq=RestAssured.given();
		test=reports.startTest("Request_Test");
		if(req.equalsIgnoreCase("GetAllLists")) {
			logger.info("GetallList@request");
			test.log(LogStatus.INFO, "GetallList Request");
			httpreq=Test_Util.pass_header(httpreq);
			logger.info("RequestCreated");
		}
		else if(req.equalsIgnoreCase("CreateList")) {
			logger.info("CreateList@request");
			test.log(LogStatus.INFO, "CreateList Request");
			httpreq = Test_Util.pass_header(httpreq);
			httpreq.body(Test_Util.data(req));
			logger.info("RequestCreated");
		}
		else if(req.equalsIgnoreCase("SpecificList")) {
			logger.info("SpecificList@request");			
			test.log(LogStatus.INFO, "SpecificList Request");
			httpreq=Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id", obj.exceldata("Sheet1", 1, 3), httpreq);
			logger.info("RequestCreated");
		}
		else if (req.equalsIgnoreCase("UpdatePatchList") || req.equalsIgnoreCase("UpdatePutList") )
		{	
			logger.info("Update@request");
			test.log(LogStatus.INFO, "UpdateList Request");
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id",obj.exceldata("Sheet1", 1, 3), httpreq);
			httpreq.body(Test_Util.data(req));
			httpreq.log().all();
			logger.info("RequestCreated");
		}
		else if (req.equalsIgnoreCase("DeleteList")) {
			logger.info("Delete@request");
			test.log(LogStatus.INFO, "DeleteList Request");
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id", obj.exceldata("Sheet1", 1, 3), httpreq);
			Test_Util.pass_queryparm("revision", obj.exceldata("Sheet1", 1, 1), httpreq);
			logger.info("RequestCreated");
		}
		else if (req.equalsIgnoreCase("CreateTask")) {
			logger.info("createTask@request");
			test.log(LogStatus.INFO, "CreateTask Request");
			httpreq = Test_Util.pass_header(httpreq);
			httpreq.body(Test_Util.data(req));		
			logger.info("RequestCreated");
		}
		else if (req.equalsIgnoreCase("UpdatePatchTask") || req.equalsIgnoreCase("UpdatePutTask") )
		{	
			logger.info("updateTask@request");
			test.log(LogStatus.INFO, "UpdateTask Request");
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id",obj.exceldata("Sheet1", 1, 5), httpreq);
			httpreq.body(Test_Util.data(req));
			httpreq.log().all();
			logger.info("RequestCreated");
		}
		else if (req.equalsIgnoreCase("DeleteTask")) {
			logger.info("DeleteTask@request");
			test.log(LogStatus.INFO, "DeleteTask Request");
			httpreq = Test_Util.pass_header(httpreq);
			Test_Util.pass_pathvar("id", obj.exceldata("Sheet1", 1, 5), httpreq);
			Test_Util.pass_queryparm("revision", obj.exceldata("Sheet1", 1, 6), httpreq);
			logger.info("RequestCreated");
		}
	}
	
	public boolean response(String req) {
		test=reports.startTest("Response_Test");
		if(req.equalsIgnoreCase("GetAllLists"))  {
			try { 
			logger.info("getting response");
			test.log(LogStatus.INFO, "AllList Response");
			rep = httpreq.when().get("lists");
			logger.info("response"+rep.prettyPrint());
			rep.then().log().all().assertThat().statusCode(200);
			test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
			logger.info("passed@GetallList");
			//obj.IncrementListRevision();
			return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("SpecificList")) {
			try {
			logger.info("getting response");
			test.log(LogStatus.INFO, "SpecificList Response");
			rep= httpreq.when().get("lists/{id}");
			logger.info("response"+rep.prettyPrint());
			rep.then().log().all().assertThat().statusCode(200);
			test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
			logger.info("passed@SpecificList");
			//obj.IncrementListRevision();
			return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("CreateList")){
			try {
				logger.info("getting response");
				test.log(LogStatus.INFO, "CreateList Response");
				rep = httpreq.when().post("lists");
				logger.info("response"+rep.prettyPrint());
				rep.then().log().all().assertThat().statusCode(201);
				test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
				logger.info("passed@CreateList");
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
				logger.info("getting response");
				test.log(LogStatus.INFO, "updateList Response");
				rep = httpreq.when().patch("lists/{id}");
				logger.info("response"+rep.prettyPrint());
				rep.then().log().all().statusCode(200);
				test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
				logger.info("passed@UpdatePatchList");
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
					logger.info("getting response");
					test.log(LogStatus.INFO, "updateList Response");
					rep = httpreq.when().put("lists/{id}");
					logger.info("response"+rep.prettyPrint());
					rep.then().log().all().statusCode(200);
					test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
					logger.info("passed@UpdatePutList");
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
				logger.info("getting response");
				test.log(LogStatus.INFO, "DeleteList Response");
				rep = httpreq.when().delete("lists/{id}");
				logger.info("response"+rep.prettyPrint());
				rep.then().log().all().statusCode(204);
				test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
				logger.info("passed@DeleteList");
				return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		else if(req.equalsIgnoreCase("CreateTask")){
			try {
				logger.info("getting response");
				test.log(LogStatus.INFO, "CreateTask Response");
				rep = httpreq.when().post("tasks");
				logger.info("response"+rep.prettyPrint());
				rep.then().log().all().assertThat().statusCode(201);
				test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
				logger.info("passed@CreateTask");
				obj.UpdateTaskID(rep.jsonPath().get("id"));
				obj.UpdateTaskRevision(rep.jsonPath().get("revision"));
				obj.IncrementListRevision();
				return true;
				}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
				}
			}
		else if(req.equalsIgnoreCase("UpdatePatchTask")) {
		try {
			logger.info("getting response");
			test.log(LogStatus.INFO, "UpdateTask Response");
			rep = httpreq.when().patch("tasks/{id}");
			logger.info("response"+rep.prettyPrint());
			rep.then().log().all().statusCode(200);
			test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
			logger.info("passed@PatchTask");
			obj.IncrementTaskRevision();
			obj.IncrementListRevision();

			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	else if(req.equalsIgnoreCase("UpdatePutTask")) {
			try {
				logger.info("getting response");
				test.log(LogStatus.INFO, "UpdateTask Response");
				rep = httpreq.when().put("tasks/{id}");
				logger.info("response"+rep.prettyPrint());
				rep.then().log().all().statusCode(200);
				test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
				logger.info("passed@PutTask");
				obj.IncrementTaskRevision();
				obj.IncrementListRevision();

				return true;
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
	}
	else if(req.equalsIgnoreCase("DeleteTask")) {
		try {
			logger.info("getting response");
			test.log(LogStatus.INFO, "DeleteTask Response");
			rep = httpreq.when().delete("tasks/{id}");
			logger.info("response"+rep.prettyPrint());
			rep.then().log().all().statusCode(204);
			test.log(LogStatus.PASS, "Success Status Code", "Accurate Success Code is displayed..");
			logger.info("passed@DeleteTask");
			obj.IncrementListRevision();
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