/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.util;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Date;

/**
 * excel 工具类
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/5/28
 */
public class Excel {

    /**
     * sheet名字
     */
    private String sheetName;

    /**
     * 本次操作的workbook 对象，会在构造器中初始化
     */
    private XSSFWorkbook workbook;

    /**
     * 本次操作的sheet 对象，会在构造器中初始化
     */
    private  XSSFSheet sheet ;

    /**
     * cell的样式，可以通过 setCellStyle方法来个性化
     */
    protected CellStyle style;

    /**
     * 行号号
     */
    int rowNum = 0;



    public Excel(){
        workbook = new XSSFWorkbook();

    }


    /**
     * 构造器，对各种excel对象进行初始化
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
     * 构造一个sheet
     * @param sheetName sheet的名字
     * @return 对象自己
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
     * 定义一行数据
     * @param datas 行数据
     * @return 本excel对象
     */
    public Excel row( Object ... datas){

        //创建一行
        Row row = sheet.createRow(rowNum++);
        int colNum = 0;
        for (Object field : datas) {

            //创建一列
            Cell cell = row.createCell(colNum++);

            //设置样式
            cell.setCellStyle(style);

            //写入数据
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
     * 将workbook对象输出到流
     * @param outputStream 要输出的流
     * @throws IOException
     */
    public void write(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
        workbook.close();

    }

    /**
     * 定义cell的样式
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
