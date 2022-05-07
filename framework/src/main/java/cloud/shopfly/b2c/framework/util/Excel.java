/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.util;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Date;

/**
 * excel Utility class
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/28
 */
public class Excel {

    /**
     * sheetThe name
     */
    private String sheetName;

    /**
     * Of this operationworkbook Object is initialized in the constructor
     */
    private XSSFWorkbook workbook;

    /**
     * Of this operationsheet Object is initialized in the constructor
     */
    private  XSSFSheet sheet ;

    /**
     * cellThe style can be passedsetCellStyleWays to personalize
     */
    protected CellStyle style;

    /**
     * The line number,
     */
    int rowNum = 0;



    public Excel(){
        workbook = new XSSFWorkbook();

    }


    /**
     * Constructor for variousexcelObject is initialized
     * @param sheetName
     */
    public Excel(String sheetName){
        this.sheetName = sheetName;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(this.sheetName);
        style = workbook.createCellStyle();
        rowNum=0;
        setCellStyle();
    }


    /**
     * To construct asheet
     * @param sheetName sheetThe name of the
     * @return Object itself
     */
    public Excel sheet(String sheetName){
        this.sheetName = sheetName;
        sheet = workbook.createSheet(this.sheetName);
        style = workbook.createCellStyle();
        rowNum=0;
        setCellStyle();
        return this;
    }

    /**
     * Define a row of data
     * @param datas Rows of data
     * @return thisexcelobject
     */
    public Excel row( Object ... datas){

        // Create a line
        Row row = sheet.createRow(rowNum++);
        int colNum = 0;
        for (Object field : datas) {

            // Create a list of
            Cell cell = row.createCell(colNum++);

            // Set the style
            cell.setCellStyle(style);

            // Write data
            if (field instanceof String) {
                cell.setCellValue((String) field);
            } else if (field instanceof Integer) {
                cell.setCellValue((Integer) field);
            }else if (field instanceof Long) {
                cell.setCellValue((Long) field);
            }else if (field instanceof Double) {
                cell.setCellValue((Double) field);
            }else if (field instanceof Double) {
                cell.setCellValue((Double) field);
            }else if (field instanceof Float) {
                cell.setCellValue((Float) field);
            }else if (field instanceof Date) {
                cell.setCellValue((Date) field);
            }else if (field instanceof Boolean) {
                cell.setCellValue((Boolean) field);
            }else if (field instanceof Boolean) {
                cell.setCellValue((Boolean) field);
            }

        }
        return this;
    }


    /**
     * willworkbookObject output to the stream
     * @param outputStream The stream to be output
     * @throws IOException
     */
    public void write(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
        workbook.close();

    }

    /**
     * definecellThe style of the
     */
    protected  void setCellStyle(){
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }


    public static void main(String[] args) throws IOException {
        Excel excel = new Excel();
        for (int j=0;j<2;j++) {
            excel.sheet("test"+j);
            for (int i = 0; i < 10; i++) {
                excel.row("a", "b", i);
            }
        }
        File file= new File("/Users/xulipeng/TempFile/test.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        excel.write(fileOutputStream);
    }
}
