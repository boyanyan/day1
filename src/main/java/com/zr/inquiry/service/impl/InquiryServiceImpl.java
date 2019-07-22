package com.zr.inquiry.service.impl;

import com.zr.inquiry.mapper.InquiryMapper;
import com.zr.inquiry.model.*;
import com.zr.inquiry.model.vo.*;
import com.zr.inquiry.service.InquiryService;
import com.zr.inquiry1.model.P2S;
import com.zr.plant.mode.AllRecords;
import com.zr.plant.mode.Plant;
import com.zr.plant.mode.vo.PlantSelectVo;
import com.zr.plant.mode.vo.PlantVo;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

/**
 * Created by lenovo on 2019/6/26.
 */
@Service
@Slf4j
public class InquiryServiceImpl implements InquiryService {
    @Autowired
    private InquiryMapper inquiryMapper;

    /*    接收参数的验证：
       1.新增时，验证表头信息的参数不能为空
       2.验证备件明细集合不为空
       3.验证备件明细中询价数量不为空，交货周期不为空
       4.验证供货商集合不为空
       5.验证询价数量是否大于0

       代码逻辑验证：
       1.验证备件集合，供应商集合是否存在重复的数据
       2.验证备件，供应商是否合法
       3.验证备件是否存在合法的供应商
       4.验证供应商是否存在合法的备件
       5.结束日期是否在开启日期之后，结束日期是否在当前日期之后*/


    /**
     *修改状态
     */
    @Override
    public Result<InquiryStatusVo> updateStatus(InquiryStatusVo inquiryStatusVo) {
        //1.验证数据是否存在
        InquiryTotalVo inquiryTotal = inquiryMapper.queryInquiryById(inquiryStatusVo.getId());//头信息
        if (inquiryTotal == null){
            return ResultVo.error("500","查看对象不存在！");
        }
        //2.如果是提交状态的话，验证之前是否为保存状态
        if (inquiryStatusVo.getStatus() == InquiryStatusEnum.TIJIAO.getStatus()){
            if (inquiryTotal.getStatus() != InquiryStatusEnum.SAVE.getStatus() ){
                return ResultVo.error("500","当前状态不允许提交！");
            }
        }
        //3.如果是审核状态的话，验证之前是否为提交状态
        if (inquiryStatusVo.getStatus() == InquiryStatusEnum.SHENHE.getStatus()){
            if (inquiryTotal.getStatus() != InquiryStatusEnum.TIJIAO.getStatus()){
                return ResultVo.error("500","非提交状态不允许审核！");
            }
        }
        //4.如果是审批状态的话，验证之前是否为审核状态
        if (inquiryStatusVo.getStatus() == InquiryStatusEnum.SHENPI.getStatus()){
            if (inquiryTotal.getStatus() != InquiryStatusEnum.SHENHE.getStatus()  ){
                return ResultVo.error("500","非审核状态不允许审批！");
            }
        }
        //5.如果是作废状态的话，其余状态下都可以(前端传接口，数据库操作）
        inquiryStatusVo.setUpdateId(10);
        inquiryStatusVo.setUpdateName("测试名称");
        inquiryStatusVo.setUpdateTime(new Date());
        inquiryMapper.updateStatus(inquiryStatusVo);

        return ResultVo.success(inquiryStatusVo);
    }

    /**
     *报价列表分页查询
     */
    @Override
    public Result<AllRecords<InquiryQuationSelectVo>> queryQuationPage(InquiryQuationSelectVo inquiryQuationSelectVo) {
        //1.查询条数
//        int count = inquiryMapper.queryCount(inquiryQuationSelectVo);
//        log.info("日志记录的查询条数为==="+ count);
        //1.根据供应商id找出此供应商能生产哪些备件
        Integer supplierId = 4;
        List<Parts> partsList = inquiryMapper.findPartBySupplierId(supplierId);
        List<Integer> partIdList = new ArrayList<>();
        for (Parts parts : partsList){
            partIdList.add(parts.getPartsId());
        }
        inquiryQuationSelectVo.setPartIdList(partIdList);
        if (CollectionUtils.isEmpty(partIdList)){//集合为空(备件集合为空)
            return ResultVo.error("500","此供应商没有可生产的备件！");
        }
        inquiryQuationSelectVo.setSupplierId(supplierId);
        //2.根据供应商能生产的备件查出询价单中供应商能生产的备件(已查处的备件再和中间表里查）
        List<InquiryQuationPartsVo> inquiryQuationPartsVoList = inquiryMapper.findInquiryQuationPartPage(inquiryQuationSelectVo);
        int count = 0;
        System.out.println(count);
        //2.查询数据
        List<InquiryTotal> plantList =  new ArrayList<>();
//        List<InquiryTotal> plantList =  inquiryMapper.queryPage(inquiryQuationSelectVo);
        log.info("日志记录的查询数据为==="+ plantList);
        System.out.println(plantList);
        AllRecords allRecords = new  AllRecords();
        allRecords.setPageIndex(inquiryQuationSelectVo.getPageIndex());
        allRecords.setPageSize(inquiryQuationSelectVo.getPageSize());
        allRecords.setTotalNumber(count);
        allRecords.setDataList(plantList);
        //总页数方法
        allRecords.resetTotalNumber(count);
        //3.给AllRecords赋值返回数据
        return ResultVo.success(allRecords);
    }

    /**
     * 分页查询接口
     * @param queryInquiryPage
     * @return
     */
    @Transactional
   public Result<AllRecords<InquiryPartsVo>> queryInquiryPage(InquirySelectVo inquirySelectVo ) {
        //1.查询条数
        int count = inquiryMapper.queryCount(inquirySelectVo);
        log.info("日志记录的查询条数为==="+ count);
        System.out.println(count);
        //2.查询数据
        List<InquiryTotal> plantList =  inquiryMapper.queryPage(inquirySelectVo);
        log.info("日志记录的查询数据为==="+ plantList);
        System.out.println(plantList);
        AllRecords allRecords = new  AllRecords();
        allRecords.setPageIndex(inquirySelectVo.getPageIndex());
        allRecords.setPageSize(inquirySelectVo.getPageSize());
        allRecords.setTotalNumber(count);
        allRecords.setDataList(plantList);
        //总页数方法
        allRecords.resetTotalNumber(count);
        //3.给AllRecords赋值返回数据
        return ResultVo.success(allRecords);
    }

    /**
     * 查看询价单信息
     * @param id
     * @return
     */
    @Override
    public Result<InquiryTotalVo> queryInquiryById(Integer id) {
        //1.验证ID是否为空
        if (id == 0 || id == null){
            return ResultVo.error("500","查看时询价单ID不能为空！");
        }
        //2.验证数据是否存在    //3.验证id查询表头信息
        InquiryTotalVo inquiryTotal = inquiryMapper.queryInquiryById(id);//头信息
        if (inquiryTotal == null){
            return ResultVo.error("500","查看对象不存在！");
        }
        //4.验证id查询备件信息
        List<InquiryPartsVo> inquiryPartsVoList = inquiryMapper.queryPartByInquiryId(id);/
        //5.验证id查询供应商信息
        List<InquirySupplierVo> inquirySupplierVoList = inquiryMapper.querySupplierByInquiryId(id);
        //头信息里包含：备件信息+供应商信息
        inquiryTotal.setInquiryPartsVoList(inquiryPartsVoList);
        inquiryTotal.setSupplierVoList(inquirySupplierVoList);
        return ResultVo.success(inquiryTotal);
    }


    /**
         * 修改接口
         * @param inquiryTotalUpdateVo
         * @return
         */
    @Transactional
   public Result<InquiryPartsVo> updateInquiry(InquiryTotalUpdateVo inquiryTotalUpdateVo){
           InquiryTotal inquiryTotal = new InquiryTotal();
           BeanUtils.copyProperties(inquiryTotalUpdateVo,inquiryTotal);
           // 验证数据是否合法
           Result<InquiryTotal> inquiryTotalResult = validateInquiry(inquiryTotal);
           if (!inquiryTotalResult.getStatus()){
               return ResultVo.error(inquiryTotalResult.getErrorCode(),inquiryTotalResult.getMessage());
           }
           //修改主表数据(操作数据库)
           Date nowDate = new Date();
           inquiryTotal.setUpdateTime(nowDate);
           inquiryTotal.setUpdateName("小明");
           inquiryTotal.setUpdateId(10);
           // 添加数据的方法(询价单—头信息）
           inquiryMapper.updateInquiryTotal(inquiryTotal);

           Integer inquiryId =  inquiryTotal.getId();//插入数据的ID，用int接收
           //删除备件数据
           inquiryMapper.deletePartsByInquiryId(inquiryId);
           //删除供应商数据
           inquiryMapper.deleteSupplierByInquiryId(inquiryId);
           //插入备件数据
           List<InquiryPartsVo> inquiryPartsVoList = inquiryTotal.getInquiryPartsVoList();//新建用于接收的备件集合
           for (InquiryPartsVo inquiryPartsVo: inquiryPartsVoList){
               inquiryPartsVo.setInquiryId(inquiryId);//前端传参，插入备件
           }
           inquiryMapper.insertInquiryParts(inquiryPartsVoList);
           //插入供应商数据
           List<InquirySupplierVo> inquirySupplierVoList = inquiryTotal.getSupplierVoList();//新建用于接收的供应商集合
           for (InquirySupplierVo inquirySupplierVo: inquirySupplierVoList){
               inquirySupplierVo.setInquiryId(inquiryId);//前端传参，插入供应商
           }
           inquiryMapper.insertInquirySupplier(inquirySupplierVoList);
           return ResultVo.success(inquiryTotalUpdateVo);
       }


    /**
     * 新增接口
     * @param inquiryTotalAddVo
     * @return
     */
    @Transactional
    @Override
    public Result<InquiryPartsVo> addInquiry(InquiryTotalAddVo inquiryTotalAddVo) {
        InquiryTotal inquiryTotal = new InquiryTotal();
        BeanUtils.copyProperties(inquiryTotalAddVo,inquiryTotal);
        // 验证数据是否合法
       Result<InquiryTotal> inquiryTotalResult = validateInquiry(inquiryTotal);
        if (!inquiryTotalResult.getStatus()){
            return ResultVo.error(inquiryTotalResult.getErrorCode(),inquiryTotalResult.getMessage());
        }
        // 获取随机数（询价单号）
        inquiryTotal.setInquiryNo(UUID.randomUUID().toString());
        //插入主表数据
        Date nowDate = new Date();
        inquiryTotal.setCreateTime(nowDate);
        inquiryTotal.setCreateName("小明");
        inquiryTotal.setCreateId(10);
        inquiryTotal.setUpdateTime(nowDate);
        inquiryTotal.setUpdateName("小明");
        inquiryTotal.setUpdateId(10);
        // 添加数据的方法
        inquiryMapper.insertInquiryTotal(inquiryTotal);
        //插入备件数据
        int inquiryId = inquiryTotal.getId();//插入数据的ID，用int接收
        List<InquiryPartsVo> inquiryPartsVoList = inquiryTotal.getInquiryPartsVoList();//新建用于接收的备件集合
        for (InquiryPartsVo inquiryPartsVo: inquiryPartsVoList){
            inquiryPartsVo.setInquiryId(inquiryId);//前端传参，插入备件
        }
        inquiryMapper.insertInquiryParts(inquiryPartsVoList);
        //插入供应商数据
        List<InquirySupplierVo> inquirySupplierVoList = inquiryTotal.getSupplierVoList();//新建用于接收的供应商集合
        for (InquirySupplierVo inquirySupplierVo: inquirySupplierVoList){
            inquirySupplierVo.setInquiryId(inquiryId);//前端传参，插入供应商
        }
        inquiryMapper.insertInquirySupplier(inquirySupplierVoList);
        return ResultVo.success(inquiryTotalAddVo);
    }

    /**
     * 验证方法
     */
    public Result<InquiryTotal> validateInquiry (InquiryTotal inquiryTotal){
        // 1.验证数据是否合法
        Result<InquiryTotal> validate = validate(inquiryTotal);
        if (!validate.getStatus()){
            return ResultVo.error(validate.getErrorCode(),validate.getMessage());
        }
        // 2.验证备件、供应商是否存在，批量查询数据库是否存在此备件、供应商
        Result<InquiryTotal> inquiryTotalResult = validateExist(inquiryTotal);
        if (!inquiryTotalResult.getStatus()){
            return ResultVo.error(inquiryTotalResult.getErrorCode(),inquiryTotalResult.getMessage());
        }
        // 3.验证备件是否存在合法的供应商，// 4.验证供应商是否有合法的备件
        Result<InquiryTotal> validateLegal = validateLegal(inquiryTotal);
        if (!validateLegal.getStatus()){
            return ResultVo.error(validateLegal.getErrorCode(),validateLegal.getMessage());
        }

        return ResultVo.success(inquiryTotal);
    }
    /**
     * 3.验证备件是否存在合法的供应商，// 4.验证供应商是否有合法的备件
     */
    public Result<InquiryTotal> validateLegal(InquiryTotal inquiryTotal) {
        //前端传过来的备件ID集合
        Set<String> partIds = new HashSet<>();
        for (InquiryPartsVo inquiryPartsVo :inquiryTotal.getInquiryPartsVoList() ){
            partIds.add(inquiryPartsVo.getPartsId());
        }
        //前端传过来的供应商ID集合
        Set<String> supplierIds = new HashSet<>();
        for (InquirySupplierVo inquirySupplierVo :inquiryTotal.getSupplierVoList() ) {
            supplierIds.add(inquirySupplierVo.getSupplierId());
        }
        //根据备件ID集合查询出生产这些备件的供应商(中间表)
        Set<P2S> p2SSet = inquiryMapper.queryP2SByPartIds(partIds);
        for (Integer partId:partIds){//页面备件id
            List<Integer> returnSupplierIds = new ArrayList<>();//用于接收供应商ID
            for (P2S p2S1 :p2SSet){// 遍历中间表
                if (p2S1.getPartsId() == partId){// 中间表ID等于页面ID，表示备件存在
                    returnSupplierIds.add(p2S1.getPartsId());// 根据备件ID获取供应商
                }
            }
            //用前端的供应商ID集合与后台查询出来的供应商ID集合进行对比，找出不同的数据,disjoint为true时没有交集，false有交集。
            if (Collections.disjoint(returnSupplierIds , supplierIds)){
                return ResultVo.error("500",partId+"此备件没有可生产它的供应商，可生产他的供应商为："+returnSupplierIds);
            }
        }

        return ResultVo.success(inquiryTotal);
    }

    /**
     * 2.验证备件、供应商是否存在，批量查询数据库是否存在此备件、供应商
     */
    public Result<InquiryTotal> validateExist(InquiryTotal inquiryTotal) {
        //验证备件是否存在，如果不存在提示用户哪个备件不存在

        //页面请求过来的备件编码集合
        Set<String> partCodeList = new HashSet<>();//set,去重
       for (InquiryPartsVo inquiryPartsVo : inquiryTotal.getInquiryPartsVoList()){// 备件编码与数据库备件编码
           partCodeList.add(inquiryPartsVo.getPartsCode());// 获取页面备件编码
       }
        //根据备件code集合查询备件信息,如果哪个备件不存在，提示用户该备件不存在
        List<InquiryParts> inquiryPartsList = inquiryMapper.queryByPartCodes(partCodeList);//查询数据库备件集合的方法
        //根据备件编码集合查询数据库响应回来的编码集合
       Set<String> returnCodeSet = new HashSet<>();//数据库响应回来的编码集合
        for (InquiryParts inquiryParts : inquiryPartsList){
            returnCodeSet.add(inquiryParts.getPartsCode());
        }
        if (partCodeList.size() != returnCodeSet.size())//页面集合不等于数据库返回的集合
            partCodeList.removeAll(returnCodeSet);//页面的移除数据库中有的
                return ResultVo.error("500","不存在的备件编码为："+partCodeList);

        return ResultVo.success(inquiryTotal);
    }

    /**
     * 1.验证备件集合，供应商集合是否存在重复的数据
     */
    public Result<InquiryTotal> validate (InquiryTotal inquiryTotal){
        // 备件集合
        Set<InquiryPartsVo> inquiryPartsVoList = new HashSet<>();
        // 供应商集合
        Set<InquirySupplierVo> inquirySupplierVoList = new HashSet<>();
        //优化代码，准确提示给用户哪个备件不存在
        List<String> partList = new ArrayList<>();// 新建备件集合，用于接收备件
        for (InquiryPartsVo inquiryPartsVo:inquiryPartsVoList){
            partList.add(inquiryPartsVo.getPartsCode());
        }
        List<String> codeList = new ArrayList<>();//根据code判断是否有重复备件
        Set<String> errorCodeList = new HashSet<>();
        for (String code : partList){
            if (!codeList.contains(code)) {
                codeList.add(code);
            }else {
                errorCodeList.add(code);
            }
        }
        if (errorCodeList.size() > 0){
            return ResultVo.error("500","存在重复的备件，备件为"+errorCodeList);
        }
        return ResultVo.success(inquiryTotal);
    }
    //优化代码，提示给用户哪个供应商不存在
}
