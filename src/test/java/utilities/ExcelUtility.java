package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	String path;

	public ExcelUtility(String path) throws IOException {
		try (FileInputStream fis = new FileInputStream(path)) {
			workbook = new XSSFWorkbook(fis);
		}
	}

	// Get Row Count from a Sheet
	public int getRowCount(String sheetName) {
		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			return 0; // Handle missing sheet as needed
		}
		return sheet.getPhysicalNumberOfRows();
	}

	// Get Cell Count from a Row
	public int getCellCount(String sheetName, int rownum) {
		sheet = workbook.getSheet(sheetName);
		XSSFRow row = sheet.getRow(rownum);
		if (row == null) {
			return 0; // Handle missing row as needed
		}
		return row.getPhysicalNumberOfCells();
	}

	// Get Data from a Specific Cell
	public String getCellData(String sheetName, int rownum, int colnum) {
		sheet = workbook.getSheet(sheetName);
		XSSFRow row = sheet.getRow(rownum);
		if (row == null) {
			return ""; // Or handle it as needed
		}
		XSSFCell cell = row.getCell(colnum);
		if (cell == null) {
			return ""; // Or handle it as needed
		}

		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell);
	}

	// Close the workbook (if you're writing to it, otherwise no need to do
	// anything)
	public void close() throws IOException {
		workbook.close(); // No need for FileOutputStream unless you're saving changes
	}
}
