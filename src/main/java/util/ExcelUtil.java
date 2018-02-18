package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public static Map<String,Map<String,String>> getExcelData(String excelPath, String sheetName){
		Map<String,Map<String,String>> sheetData = new LinkedHashMap<String, Map<String,String>>();
		
		File file = new File(excelPath);
		InputStream is=null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Please check the file path. Entered value is "+file.getAbsolutePath());
		}
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Extracting sheet from Workbook
		Sheet sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getPhysicalNumberOfRows();
		
		for(int i =1;i<rowCount;i++){
			Row row = sheet.getRow(i);
			//Getting testcase number
			String testcase = row.getCell(0).getStringCellValue();
			
			//Creating row data
			Map<String, String> rowData = new LinkedHashMap<String,String>();
			int columnCount = row.getLastCellNum();
			
			for(int j=1;j<columnCount;j++){
				Cell cell = row.getCell(j);
				String cellData=null;
				
				switch(cell.getCellTypeEnum()){
				
				case STRING:
					cellData = cell.getStringCellValue();
					break;
				case NUMERIC:
					double doubleData = cell.getNumericCellValue();
					long roundedData = Math.round(doubleData);
					if(doubleData == roundedData){
						cellData = String.valueOf(roundedData);
					}else{
						cellData=String.valueOf(doubleData);
					}
					break;
				case BOOLEAN:
					cellData = String.valueOf(cell.getBooleanCellValue());
					break;
				default:
					System.out.println("Data is not in proper format with cell address as "+cell.getAddress());
				
				}
				//Entering Cell header and cell value. Ex Country and USA
				rowData.put(sheet.getRow(0).getCell(j).getStringCellValue(), cellData);
			}
			
			//Entering testcase name and row data
			sheetData.put(testcase, rowData);
		}
		return sheetData;
	}
	
	public static List<Object> getAllValuesForHeaderInAColumn(String excelPath, String sheetName, String header){
		List<Object> values = new ArrayList<Object>();
		
		File file = new File(excelPath);
		InputStream is=null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Please check the file path. Entered value is "+file.getAbsolutePath());
		}
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Extracting sheet from Workbook
		Sheet sheet = workbook.getSheet(sheetName);
		int colIndex = -1;
		Row headerRow = sheet.getRow(0);
		for(int i=0;i<headerRow.getLastCellNum();i++){
			if(header.equals(headerRow.getCell(i).getStringCellValue())){
				colIndex = i;
			}
		}
		
		//Extracting the values of the header
		if(colIndex != -1){
			int rowCount = sheet.getPhysicalNumberOfRows();
			
			for(int i =1;i<rowCount;i++){
				Row currRow = sheet.getRow(i);
				Cell currCell = currRow.getCell(colIndex);
				Object value = null;
				switch(currCell.getCellTypeEnum()){
				
				case STRING:
					value = currCell.getStringCellValue();
					break;
				case NUMERIC:
					double doubleData = currCell.getNumericCellValue();
					long roundedData = Math.round(doubleData);
					if(doubleData == roundedData){
						value = roundedData;
					}else{
						value=doubleData;
					}
					break;
				case BOOLEAN:
					value = currCell.getBooleanCellValue();
					break;
				default:
					System.out.println("Data is not in proper format with cell address as "+currCell.getAddress());
				
				}
				values.add(value);
			}
		}
		
		return values;
	}
	
}
