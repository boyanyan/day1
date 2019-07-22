package com.zr.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.ClassPathResource;

public class POIClass {
	/**
	 * 封装返回的流信息
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void toPackageOs(HttpServletResponse response , String fileName)throws Exception{
		//设置编码格式
		response.setContentType("application/octet-stream;charset=utf-8");
		//拼接名称加上日期
		String outFileName = fileName + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//给输出的文件设置名称
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileName.getBytes(),"iso-8859-1") + ".xls");
	}

	/**
	 * 生成模板输入流
	 * @param temPath
	 * @return
	 * @throws Exception
	 */
	public static InputStream toPackageIn(String temPath)throws Exception{
		return new ClassPathResource((temPath)).getInputStream();
	}
	// 给具体的某个行中的某个列赋值
	public static void toCellValue(Row row, int cellColumn, String value) {
		Cell cell = row.createCell(cellColumn);
		cell.setCellValue(value);
	}

}
