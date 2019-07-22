package com.zr.kucun3.service.impl;

import com.zr.kucun3.model.CustomerInfo;
import com.zr.kucun3.model.CustomerStatusEnum;
import com.zr.kucun3.model.CustomerStatusNameEnum;
import com.zr.kucun3.service.StoreBinService;
import com.zr.plant.mapper.PlantMapper;
import com.zr.plant.mode.Plant;
import com.zr.util.ExportUtil;
import com.zr.util.POIClass;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lx on 2018/10/22.
 */
@Service
@Slf4j
public class StoreBinServiceImpl implements StoreBinService {
    @Autowired
    private PlantMapper plantMapper;

    @Override
    public Result<List<CustomerInfo>> importFile(MultipartFile file) throws Exception{
        //声明一个空的list集合
        List<CustomerInfo> customerInfoList =new ArrayList();
        //读取excel表格中的数据
        Result<List<CustomerInfo>> addVOResultVO = this.parse(customerInfoList, file);
        if (!addVOResultVO.getStatus()){
            return addVOResultVO;
        }
        //验证数据是否合法
        Result<List<CustomerInfo>> listResult = validImportDate(addVOResultVO.getData());
        if (!listResult.getStatus()){
            return listResult;
        }
        //插入数据库 客户信息

        return addVOResultVO;
    }

    @Override
    public void exportExcel(HttpServletResponse response) throws Exception{
        //1.先获取到要导出的数据
        List<Plant> plantAll = plantMapper.findPlantAll();//查询所有
        //2.给excel赋值要导出的数据

        //获取响应输出流
        ServletOutputStream out = response.getOutputStream();
        //给输出文件设置名称
        POIClass.toPackageOs(response, "法人工厂导出");//POIClass封装类
        //读取模板中的数据
       // File fi = new File( pathname:"D:\\test\\客户信息.xlsx");
        InputStream in = ExportUtil.toPackageIn("static/法人工厂.xlsx");//ExportUtil封装类,InputStream输入流
        //根据模板的数据、把查询出来的数据给摸版SHeet1组中的数据赋值、把excel输出到浏览器上
        writeDataToExcel(in, "Sheet1", plantAll, out);
        if (in != null) {
            in.close();
            out.close();
        }

    }
    // 由于此方法不能通用, 所以单独写在这里
    private void writeDataToExcel(InputStream in, String sheetName,
                                  List<Plant> resultList, ServletOutputStream out) throws Exception {
        //POi读取模板
        XSSFWorkbook wb = new XSSFWorkbook(in);
        //读取sheet1中的数据
        Sheet sheet = wb.getSheet(sheetName);
        //创建样式(可无)
        CellStyle style = wb.createCellStyle();
        if (sheet != null) {
            //向sheet1中赋值，设置样式
            toResultListValueInfo(style, sheet, resultList);
        }
        //把数据写入到输出流中
        wb.write(out);
        //关闭poi方法
        wb.close();
    }

    /**
     * 插入excel表中项目信息
     *
     * @param style
     * @param sheet
     */
    private void toResultListValueInfo(CellStyle style, Sheet sheet, List<Plant> plantList) {
        //从第五行开始赋值
        int row_column = 4;
        //遍历数据集合
        for (Plant obj : plantList) {
            //创建一行的方法
            Row row = sheet.createRow(row_column);
            // 给第一列序号赋值赋值
            POIClass.toCellValue(row,0, obj.getId() + "");
            // 给第二列工厂编码赋值
            POIClass.toCellValue(row, 1, obj.getPlantCode() + "");
            // 给第3列工厂描述赋值
            POIClass.toCellValue(row, 2, obj.getPlantDesc() + "");
            // 给第4列工厂状态赋值
            POIClass.toCellValue(row, 3, obj.getPlantStatus() + "");
            //给法人编码赋值
            POIClass.toCellValue(row, 4, obj.getLegalPersonId() + "");
            //给法人名称赋值
            POIClass.toCellValue(row, 5, obj.getLegalPersonName() + "");
            row_column++;
        }
        //隐藏第一列的结果
//        sheet.setColumnHidden(0, true);
        //设置样式：比如颜色
//        toCellColor(sheet.createRow(row_column), style);
        //合并列和行，参数：起始行号，终止行号， 起始列号，终止列号
//        CellRangeAddress cra = new CellRangeAddress(row_column + 1, row_column + 1, 0,1);
//        sheet.addMergedRegion(cra);
    }
    // 验证导入数据
    public Result<List<CustomerInfo>> validImportDate(List<CustomerInfo> customerInfoList){
        //1.验证必填数据是否为空//2.验证数据长度是否合法
        Set<String> nameList =new HashSet<>();//页面名称集合
        for (CustomerInfo customerInfo: customerInfoList){//遍历集合，获取数据
            if (StringUtils.isEmpty(customerInfo.getCustomerName())){
                return ResultVo.error("500","客户名称不能为空！");
            }else {
                if (customerInfo.getCustomerName().length()>20){
                    return ResultVo.error("500","客户名称字符长度不允许超过20个字节！");
                }
                nameList.add(customerInfo.getCustomerName());//获取所有页面的名字集合
            }
            if (StringUtils.isEmpty(customerInfo.getStatusName())){
                return ResultVo.error("500","状态信息不能为空！");
            }else {
                if (customerInfo.getStatusName().length()>20){
                    return ResultVo.error("500","状态信息字符长度不允许超过20个字节！");
                }
            }
            if (StringUtils.isEmpty(customerInfo.getCompany())){
                return ResultVo.error("500","公司名称不能为空！");
            }else {
                if (customerInfo.getCompany().length()>20){
                    return ResultVo.error("500","公司名称字符长度不允许超过20个字节！");
                }
            }

        }
        //3.验证表格中的数据是否存在(用批量操作，不用for循环）
      /*  List<CustomerInfo> customerInfoList1 = storeBinMapper.queryCustomerByNames(nameList);//customerInfoList1数据库名称，nameList页面名称（根据名称验证是否存在）
        Set<String> cutomerNameList = new HashSet<>();//数据库返回的名称集合
        for (CustomerInfo customerInfo: customerInfoList1){//遍历数据库名称集合
            cutomerNameList.add(customerInfo.getCustomerName());
        }
        if (customerInfoList1.size()!=nameList.size()){//数据库名称与前端名称长度比较
            nameList.removeAll(cutomerNameList);//页面名称移除数据库中含有的
            return ResultVo.error("500","不存在的客户名称为："+nameList);
        }*/
        //4.对需要数据转换的字段进行验证，验证转换字段是否存在！
        for (CustomerInfo customerInfo: customerInfoList){
            if (!customerInfo.getStatusName().equals(CustomerStatusNameEnum.QIYONG.getStatus())
                    &&!customerInfo.getStatusName().equals(CustomerStatusNameEnum.JINYONG.getStatus())){
                return ResultVo.error("500","状态信息必须为启用或禁用状态！");
            }
        }
        //5.对需要转换的字段进行转换
        for (CustomerInfo customerInfo: customerInfoList){
            if (customerInfo.getStatusName().equals(CustomerStatusNameEnum.QIYONG.getStatus())){//客户状态操作
                customerInfo.setStatus(CustomerStatusEnum.QIYONG.getStatus());//数据库显示字段的状态为启用（入库操作）
            }else {
                customerInfo.setStatus(CustomerStatusEnum.JINYONG.getStatus());
            }
        }
        return ResultVo.success(customerInfoList);
    }

    /**
     * 客户信息导入模板标题
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
                    System.out.println(String.valueOf(xssfRow.getCell(0)).trim());
                    System.out.println(String.valueOf(xssfRow.getCell(1)).trim());
                    System.out.println(String.valueOf(xssfRow.getCell(2)).trim());
                    if ((!StringUtils.isEmpty(String.valueOf(xssfRow.getCell(0)).trim())&&
                                        String.valueOf(xssfRow.getCell(0))!=null) ||
                            (!StringUtils.isEmpty(String.valueOf(xssfRow.getCell(1)).trim())&&
                                    !String.valueOf(xssfRow.getCell(1)).equals("null"))
                                    ||
                            (!StringUtils.isEmpty(String.valueOf(xssfRow.getCell(2)).trim())&&
                                    !String.valueOf(xssfRow.getCell(2)).equals("null"))
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
