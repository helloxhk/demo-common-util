package com.taiji.common.util.excel;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtil {
	
	public static Object getCellValue(Cell cell, boolean isString) {
		Object obj = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				obj = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					obj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
				} else if (isString) {
					obj = String.format("%.0f", cell.getNumericCellValue());
				} else {
					obj = cell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				obj = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				obj = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				obj = cell.getCellFormula();
				break;
			}
		}
		return obj;
	}
	
	public static Object getCellValueNoRounding(Cell cell, boolean isString) {
		Object obj = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				obj = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					obj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
				} else if (isString) {
					obj = cell.getNumericCellValue();
				} else {
					obj = cell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				obj = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_ERROR:
				obj = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				obj = cell.getCellFormula();
				break;
			}
		}
		return obj;
	}
	
	/**
	 * XSSFWorkbook 读取方法
	 * */
	public static Object getCellValue(XSSFCell cell, boolean isString) {
		Object obj = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				obj = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					obj = new SimpleDateFormat("yyyyMMdd").format(DateUtil.getJavaDate(cell.getNumericCellValue()));
				} else if (isString) {
					obj = String.format("%.2f", cell.getNumericCellValue());
				} else {
					obj = cell.getNumericCellValue()+"";
				}
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN:
				obj = cell.getBooleanCellValue();
				break;
			case XSSFCell.CELL_TYPE_ERROR:
				obj = cell.getErrorCellValue();
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				obj = cell.getCellFormula();
				break;
			}
		}
		return obj;
	}
}
