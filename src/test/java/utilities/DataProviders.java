package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	// DataProvider 1
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
		String path = ".\\testData\\Opencart_LoginData.xlsx"; // taking XLfile from testData folder.

		ExcelUtility xlutil = new ExcelUtility(path); // creating an object for XLutility

		int totalrows = xlutil.getRowCount("sheet1");
		int totalcols = xlutil.getCellCount("sheet1", 1);

		String logindata[][] = new String[totalrows][totalcols]; // created for two dimension array which can store
		for (int i = 1; i <= totalrows; i++) { // 1 reading data from xl storing in two dimensional array.
			for (int j = 0; j < totalcols; j++) { // 0 i is rows and j is col
				logindata[i - 1][j] = xlutil.getCellData("sheet1", i, j); // 1,0
			}
		}
		return logindata; // returning two dimension array
	}

	// DataProvider 2
	// DataProvider 3
	// DataProvider 4
}
