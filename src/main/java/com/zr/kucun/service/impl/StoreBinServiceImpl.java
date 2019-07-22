package com.zr.kucun.service.impl;

import com.zr.kucun.model.CustomerInfo;
import com.zr.kucun.service.StoreBinService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lx on 2018/10/22.
 */
@Service
@Slf4j
public class StoreBinServiceImpl implements StoreBinService {
    @Override
    public Result<List<CustomerInfo>> importFile(MultipartFile file) throws Exception{
        //声明一个空的list集合
        List<CustomerInfo> customerInfoList =new ArrayList();
        //读取excel表格中的数据
        Result<List<CustomerInfo>> addVOResultVO = this.parse(customerInfoList, file);
        if (!addVOResultVO.getStatus()){
            return addVOResultVO;
        }
/*
        System.out.println("list集合中的数据为===="+addVOResultVO);
        //给状态ID赋值
        List<CustomerInfo> customerList =  assigment(addVOResultVO);
        //把数据入到客户表中
        addCustomerInfo(customerList);*/
        return addVOResultVO;
    }
    /**
     * 特殊销账导入模板标题
     */
    public static final String IMPORT_WRITEOFF_ACCOUNT_EXCEL_TITLE = "客户信息";

    /**
     *
    2003版本的excel使用以下方式读取
     HSSFWorkbook wk = new HSSFWorkbook();
     //2.创建一个工作簿
     HSSFSheet sheet = wk.createSheet("测试sheet");

     HSSFCellStyle cellStyle = wk.createCellStyle();
     cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

     */

    //读取2007及以上excel表格中的数据
    private Result<List<CustomerInfo>> parse(List<CustomerInfo> customerInfoList, MultipartFile file)throws Exception {
        try {
            if (file==null){
                return ResultVo.error("500","导入文件不能为空！");
            }
            String fileName = file.getOriginalFilename();
            if (fileName.endsWith(".xls")){
                return ResultVo.error("500","导入版本必须为2007版及以上版本！");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //获取单元格中的信息
            XSSFSheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet.getRow(sheet.getFirstRowNum()).getCell(0));
            //验证第一列第一行的表格标头信息是否为 “客户信息”，如果不是，提示模板错误。
            if (!String.valueOf(sheet.getRow(sheet.getFirstRowNum()).getCell(0)).equals(IMPORT_WRITEOFF_ACCOUNT_EXCEL_TITLE)) {
                return ResultVo.error("500","模板错误，请检查模板！");
            }
            //遍历excel表格中的所有数据，从第五行开始读取，没有数据时终止遍历。
            for (int i = sheet.getFirstRowNum() + 4; i <= sheet.getLastRowNum(); i++) {
                //读取每一行的数据
                XSSFRow xssfRow = sheet.getRow(i);
                if(xssfRow!=null) {
                    //验证第一列数据、第二列、第三列数据是否为空
                    System.out.println(String.valueOf(xssfRow.getCell(0)));
                    System.out.println(String.valueOf(xssfRow.getCell(1)));
                    System.out.println(String.valueOf(xssfRow.getCell(2)));
                    if (!StringUtils.isEmpty(String.valueOf(xssfRow.getCell(0)).trim()) &&
                            String.valueOf(xssfRow.getCell(0))!=null &&
                            String.valueOf(xssfRow.getCell(1))!=null &&
                            String.valueOf(xssfRow.getCell(2))!=null &&
                            !StringUtils.isEmpty(String.valueOf(xssfRow.getCell(1))) &&
                            !StringUtils.isEmpty(String.valueOf(xssfRow.getCell(2)))
                            ) {
                        //把每一行的数据放入到实体类中
                      CustomerInfo customerInfo = build(xssfRow);
                      //把实体类中的数据放入到list集合中
                        customerInfoList.add(customerInfo);
                    }
                }

            }
        } catch (IOException e) {
            log.error("parse", e);
        }
        return ResultVo.success(customerInfoList);
    }

    private CustomerInfo build(XSSFRow xssfRow) throws Exception {
        CustomerInfo addVO = new CustomerInfo();
        addVO.setCustomerId(String.valueOf(xssfRow.getCell(0)));
        addVO.setCustomerName(String.valueOf(xssfRow.getCell(1)));
        addVO.setStatusName(String.valueOf(xssfRow.getCell(2)));
        addVO.setCompany(String.valueOf(xssfRow.getCell(3)));
        return addVO;
    }
}
