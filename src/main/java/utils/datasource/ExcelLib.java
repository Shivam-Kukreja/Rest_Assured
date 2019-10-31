package utils.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelLib {
	
	public String exceldata(String sheetName, int rowNum, int colNum) throws EncryptedDocumentException, InvalidFormatException, IOException {
		String excelPath = LoadProperty.prop.getProperty("Excel_Path");
		excelPath= System.getProperty("user.dir")+excelPath;
		FileInputStream is=new FileInputStream(excelPath);
		Workbook wb=WorkbookFactory.create(is);
		Sheet sh= wb.getSheet(sheetName);
		Row r=sh.getRow(rowNum);
		Cell c=r.getCell(colNum);
		c.setCellType(Cell.CELL_TYPE_STRING);
		String data = c.getStringCellValue();
		return data;
		}
	public boolean updateExceldata(String sheetname,int rownum,int colmn,String value) throws IOException, EncryptedDocumentException, InvalidFormatException {
		try{
			String excelpath = LoadProperty.prop.getProperty("Excel_Path");
		
		excelpath = System.getProperty("user.dir")+excelpath;
		FileInputStream inputStream = new FileInputStream(new File(excelpath));
	    Workbook workbook = WorkbookFactory.create(inputStream);
	    Sheet sheet = workbook.getSheetAt(0);
	    Cell cell2Update = sheet.getRow(rownum).getCell(colmn);
	    cell2Update.setCellValue(value);
	    FileOutputStream outputStream = new FileOutputStream(excelpath);
	    workbook.write(outputStream);
	    workbook.close();
	    outputStream.close();
		return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	public void IncrementListRevision() throws NumberFormatException, EncryptedDocumentException, InvalidFormatException, IOException {
		int rev = Integer.parseInt(exceldata("Sheet1", 1, 1));
		rev=rev + 1;
		updateExceldata("Sheet1", 1, 1, Integer.toString(rev));
	}
	public void UpdateListID(Object object) throws NumberFormatException, EncryptedDocumentException, InvalidFormatException, IOException {
		updateExceldata("Sheet1", 1, 3, Integer.toString((Integer) object));
	}
	public void UpdateListRevision(Object object) throws NumberFormatException, EncryptedDocumentException, InvalidFormatException, IOException {
		updateExceldata("Sheet1", 1, 1, Integer.toString((Integer) object));
	}
	public void IncrementTaskRevision() throws NumberFormatException, EncryptedDocumentException, InvalidFormatException, IOException {
		int rev = Integer.parseInt(exceldata("Sheet1", 1, 6));
		rev=rev + 1;
		updateExceldata("Sheet1", 1, 6, Integer.toString(rev));
	}
	public void UpdateTaskID(Object object) throws NumberFormatException, EncryptedDocumentException, InvalidFormatException, IOException {
		updateExceldata("Sheet1", 1, 5, Long.toString((Long) object));
	}
	public void UpdateTaskRevision(Object object) throws NumberFormatException, EncryptedDocumentException, InvalidFormatException, IOException {
		updateExceldata("Sheet1", 1, 6, Integer.toString((Integer) object));
	}
}
