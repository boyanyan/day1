package com.zr.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * excel导出数据
 * @author dk
 */
@Slf4j
public class ExportUtil {

	private ExportUtil(){}
	
	/**
	 * 封装返回的流信息
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void toPackageOs(HttpServletResponse response , String fileName)throws Exception{
		response.setContentType("application/octet-stream;charset=utf-8");  
		String outFileName = fileName + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileName.getBytes(),"iso-8859-1") + ".xls");  
	}

	public static void toPackageOs(HttpServletResponse response , String fileName, String suffix)throws Exception{
		response.setContentType("application/octet-stream;charset=utf-8");
		String outFileName = fileName + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileName.getBytes(),"iso-8859-1") + suffix);
	}

	/**
	 * 生成zip返回流头部信息
	 * @param response
	 * @param zipName
	 * @throws Exception
	 */
	public static void toPackageZipOs(HttpServletResponse response , String zipName)throws Exception{
		response.setContentType("APPLICATION/OCTET-STREAM");  
	    response.setHeader("Content-Disposition","attachment; filename="+new String(zipName.getBytes(),"iso-8859-1"));
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
	
	/**
	 * 一次性导出全部数据
	 * @param <T>
	 * @param list
	 * @param os
	 */
	/*public static <T> void exportExcel(List<T> list, OutputStream os ,
			InputStream in)throws Exception{
		long exportExcelBegin = System.currentTimeMillis();
		log.warn("exportExcel begin: " + exportExcelBegin);
		Context context = new Context();
		context.putVar("list", list);
		JxlsHelper.getInstance().processTemplateAtCell(in, os, context, "Result!A1");
        os.flush();
		long exportExcelEnd = System.currentTimeMillis();
		log.warn("exportExcel fininshed in: " + (exportExcelEnd - exportExcelBegin));
	}*/
	

	
	/**
	 * 压缩制定目录下的文件, 生成并下载zip文件
	 * @param srcFile 目标目录
	 * @param zipPath 生成的zip文件的全路径
	 * @param os 返回流  把zip流写到返回流中
	 * @throws Exception
	 */
	public static void zipExcel(String srcFile , OutputStream os)throws Exception{
		// 要被压缩的文件夹
        File file = new File(srcFile);
        InputStream input = null;
        ZipOutputStream zipOut = new ZipOutputStream(os);
        // zip的名称为
        zipOut.setComment(file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; ++i) {
                input = new FileInputStream(files[i]);
                zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
                int temp = 0;
                while ((temp = input.read()) != -1) {
                    zipOut.write(temp);
                }
                input.close();
            } 
        }
        
        zipOut.close();
	}
	
	/**
	 * 删除文件夹下所有的文件
	 * @param path
	 * @return
	 */
	public static boolean delAllFile(String path) {
       boolean flag = false;
       File file = new File(path);
       if (!file.exists()) {
         return flag;
       }
       if (!file.isDirectory()) {
         return flag;
       }
       String[] tempList = file.list();
       File temp = null;
       for (int i = 0; i < tempList.length; i++) {
          if (path.endsWith(File.separator)) {
             temp = new File(path + tempList[i]);
          } else {
              temp = new File(path + File.separator + tempList[i]);
          }
          if (temp.isFile()) {
             temp.delete();
          }
          if (temp.isDirectory()) {
             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
             flag = true;
          }
       }
       return flag;
	}
	
}