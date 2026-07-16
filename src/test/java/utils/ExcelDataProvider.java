package utils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {


    @DataProvider(name = "sourceDestinationData")
    public String[][] getSourceDestinationData() throws IOException{
        String excelPath = "C:\\Software Testing\\Selenium\\redbus_automation\\testData\\RedBusExcelSheet.xlsx";
        ExcelConnect excelConnect = new ExcelConnect(excelPath);
        String sheetName = "SourceDestination";

        int numberOfRows = excelConnect.getRowCount(sheetName);
        int numberOfColumns = excelConnect.getColumnCount(sheetName, 0);

        String[][] data = new String[numberOfRows][numberOfColumns];

        for(int i=1;i<=numberOfRows;i++){
            for(int j= 0;j<numberOfColumns;j++){
                data[i-1][j] = excelConnect.getCellValue(sheetName, i, j);
            }
        }
        return data;
    }

}
