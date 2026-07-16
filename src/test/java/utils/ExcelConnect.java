package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelConnect {

    public FileInputStream fis;
    public FileOutputStream fos;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    String path;
    public ExcelConnect(String path) {
        this.path = path;
    }


    public int getRowCount(String sheetName) throws IOException {
        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum();
        workbook.close();
        fis.close();
        return rowCount;

    }

    public int getColumnCount(String sheetName, int rowNum) throws IOException {
        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        int colCount = row.getLastCellNum();
        workbook.close();
        fis.close();
        return colCount;
    }

    public int getCellCount(String sheetName, int rowNum) throws IOException {
        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        int cellCount = row.getLastCellNum();
        workbook.close();
        fis.close();
        return cellCount;
    }

    public String getCellValue(String sheetName, int rowNum, int colNum) throws IOException {
        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);

        DataFormatter formatter = new DataFormatter();
        String data;
        try{
            data = formatter.formatCellValue(cell);
        }catch (Exception e){
            data = "";
        }
        workbook.close();
        fis.close();
        return data;

    }

    public void setCellValue(String sheetName, int rowNum, int colNum, String value) throws IOException {

        File file = new File(path);
        if(!file.exists()){
            workbook = new XSSFWorkbook();
            fos = new FileOutputStream(path);
            workbook.write(fos);
        }

        fis = new FileInputStream(path);
        workbook = new XSSFWorkbook(fis);

        if(workbook.getSheet(sheetName)==null){
            sheet = workbook.createSheet(sheetName);
        }
        sheet =  workbook.getSheet(sheetName);
        if(sheet.getRow(rowNum)==null){
            sheet.createRow(rowNum);
        }
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);
        if(cell==null){
            cell = row.createCell(colNum);
        }
        cell.setCellValue(value);
        fos = new FileOutputStream(path);
        workbook.write(fos);
        fos.close();
        workbook.close();


    }



}