package utils.report;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

import Test_Base.Base_Class;
import utils.datasource.LoadProperty;

public class ExtentReport_Manager {
	private static ExtentReports extent;
    public static ExtentReports getInstance() {
          if(extent == null) {
                 Date d  = new Date();
                       Base_Class.timestamp = d.toString().replace(":", "_").replace(" ", "_");
                       
                       String reportFolderPath = LoadProperty.prop.getProperty("reportsPath" )+"Test_Report_"+Base_Class.timestamp+"\\";
                       reportFolderPath = System.getProperty("user.dir")+reportFolderPath;
                       Base_Class.testReportFolderPath =reportFolderPath;
                       String filename = Base_Class.timestamp+".html";
                       extent = new ExtentReports(Base_Class.testReportFolderPath+filename,true,DisplayOrder.NEWEST_FIRST);
                       extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
                       extent.addSystemInfo("Selenium version", "3.11.0");
          }
          return extent;
    }
}
