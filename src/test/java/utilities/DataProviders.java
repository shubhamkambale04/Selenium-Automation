package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "LoginData")
	public String[][] getAllData() throws IOException {
		String path = ".\\TestData\\login data driven test.xlsx"; // Ensure correct path

		ExcelUtility excelData = new ExcelUtility(path);
		try {
			int rowCount = excelData.getRowCount("Sheet1");
			if (rowCount <= 1) {
				throw new IllegalArgumentException("Sheet1 has no data rows.");
			}

			int colCount = excelData.getCellCount("Sheet1", 1); // Assuming row 1 contains data
			String[][] loginData = new String[rowCount - 1][colCount]; // Exclude header row

			// Populate data from Excel
			for (int i = 1; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					loginData[i - 1][j] = excelData.getCellData("Sheet1", i, j);
				}
			}
			return loginData;
		} finally {
			excelData.close();
		}
	}
}
