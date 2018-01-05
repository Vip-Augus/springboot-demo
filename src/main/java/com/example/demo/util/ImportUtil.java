package com.example.demo.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件导入工具类
 * Author JingQ on 2018/1/5.
 */
public class ImportUtil {

    /**
     * 分隔符
     */
    private final static String SEPARATOR = "|";

    /**
     * 由Excel流的Sheet导出至List
     *
     * @param is
     * @param sheetNum
     * @return
     * @throws IOException
     */
    public static List<String> exportListFromExcel(InputStream is, int sheetNum) throws IOException {

        Workbook workbook = new HSSFWorkbook(is);
        return exportListFromExcel(workbook, sheetNum);
    }

    /**
     * 由指定的Sheet导出至List
     *
     * @param workbook
     * @param sheetNum
     * @return
     * @throws IOException
     */
    private static List<String> exportListFromExcel(Workbook workbook, int sheetNum) {
        Sheet sheet = workbook.getSheetAt(sheetNum);
        // 解析公式结果
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        List<String> list = new ArrayList<String>();
        int minRowIx = sheet.getFirstRowNum();
        int maxRowIx = sheet.getLastRowNum();
        Row row1 = sheet.getRow(minRowIx);
        short minColIx = row1.getFirstCellNum();
        short maxColIx = row1.getLastCellNum();
        for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
            Row row = sheet.getRow(rowIx);
            StringBuilder sb = new StringBuilder();
            for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
                Cell cell = row.getCell(Integer.valueOf(colIx));
                CellValue cellValue = evaluator.evaluate(cell);
                if (cellValue == null) {
                    if(colIx!=maxColIx){
                        sb.append(SEPARATOR + "null");
                    }
                    continue;
                }
                // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
                // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
                switch (cellValue.getCellType()) {

                    case Cell.CELL_TYPE_BOOLEAN:
                        sb.append(SEPARATOR + cellValue.getBooleanValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        // 这里的日期类型会被转换为数字类型，需要判别后区分处理
                        if (DateUtil.isCellDateFormatted(cell)) {
                            sb.append(SEPARATOR + cell.getDateCellValue());
                        } else {
                            Double doubleVal=cellValue.getNumberValue();
                            Object inputValue=null;
                            long longVal = Math.round(cell.getNumericCellValue());
                            if(Double.parseDouble(longVal + ".0") == doubleVal)
                                inputValue = longVal;
                            else
                                inputValue = doubleVal;
                            sb.append(SEPARATOR +inputValue);
                        }
                        break;
                    case Cell.CELL_TYPE_STRING:
                        sb.append(SEPARATOR + cellValue.getStringValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        break;
                    default:
                        break;
                }
            }
            list.add(sb.toString());
        }
        return list;
    }
}
