package com.zr.inquiry1.service.impl;

import com.zr.inquiry1.mapper.InquiryMapper;
import com.zr.inquiry1.model.*;
import com.zr.inquiry1.model.vo.InquiryPartsVo;
import com.zr.inquiry1.model.vo.InquirySupplierVo;
import com.zr.inquiry1.model.vo.InquiryTotalAddVo;
import com.zr.inquiry1.model.vo.InquiryTotalVo;
import com.zr.inquiry1.service.InquiryService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Lx on 2018/10/16.
 */
@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryMapper inquiryMapper;

    /**
     * 新增接口
     * @param inquiryTotalAddVo
     * @return
     */
    @Override
    public Result<InquiryTotalVo> addInquiry(InquiryTotalAddVo inquiryTotalAddVo) {
        InquiryTotal inquiryTotal = new InquiryTotal();
        BeanUtils.copyProperties(inquiryTotalAddVo,inquiryTotal);
        //验证数据是否合法
        Result<InquiryTotal> inquiryTotalResult = validateInquiry(inquiryTotal);
        if (!inquiryTotalResult.getStatus()){
            return ResultVo.error(inquiryTotalResult.getErrorCode(),inquiryTotalResult.getMessage());
        }
        inquiryTotal.setInquiryNo(UUID.randomUUID().toString());
        //插入主表数据
        Date nowDate = new Date();
        inquiryTotal.setCreateTime(nowDate);
        inquiryTotal.setCreateName("小明");
        inquiryTotal.setCreateId(10);
        inquiryTotal.setUpdateTime(nowDate);
        inquiryTotal.setUpdateName("小明");
        inquiryTotal.setUpdateId(10);
        inquiryMapper.insertInquiryTotal(inquiryTotal);
        //插入备件数据
        int inquiryId = inquiryTotal.getId();
        List<InquiryPartsVo> inquiryPartsVoList = inquiryTotal.getInquiryPartsVoList();
        for (InquiryPartsVo inquiryPartsVo: inquiryPartsVoList){
            inquiryPartsVo.setInquiryId(inquiryId);
        }
//        inquiryMapper.insertInquiryParts(inquiryPartsVoList);
        //插入供应商数据
        List<InquirySupplierVo> inquirySupplierVoList = inquiryTotal.getSupplierVoList();
        for (InquirySupplierVo inquirySupplierVo: inquirySupplierVoList){
            inquirySupplierVo.setInquiryId(inquiryId);
        }
//        inquiryMapper.insertInquiryParts(inquiryPartsVoList);
        return ResultVo.success(inquiryTotalAddVo);
    }

    @Override
    public int test() {
        return 0;
    }

    public Result<InquiryTotal> validateInquiry(InquiryTotal inquiryTotal){
//        5.结束日期是否在开始日期之后，结束日期是否在当前时间之后
//        6.效验询价数量是否大于0
        //验证数据是否合法
        Result<InquiryTotal> validate = validate(inquiryTotal);
        if (!validate.getStatus()){
            return ResultVo.error(validate.getErrorCode(),validate.getMessage());
        }
        // 2.验证备件、供应商是否存在，批量查询数据库是否存在此备件、供应商。
        Result<InquiryTotal> inquiryTotalResult = validateExist(inquiryTotal);
        if (!inquiryTotalResult.getStatus()){
            return ResultVo.error(inquiryTotalResult.getErrorCode(),inquiryTotalResult.getMessage());
        }
//        3.验证备件是否存在合法的供应商，//        4.验证供应商是否有合法的备件
        Result<InquiryTotal> validateLegal = validateLegal(inquiryTotal);
        if (!validateLegal.getStatus()){
            return ResultVo.error(validateLegal.getErrorCode(),validateLegal.getMessage());
        }
        return ResultVo.success(inquiryTotal);
    }


    public Result<InquiryTotal> validateLegal(InquiryTotal inquiryTotal){

        //前端传过来的备件ID集合
        Set<Integer> partIds = new HashSet<>();
        for (InquiryPartsVo inquiryPartsVo: inquiryTotal.getInquiryPartsVoList()){
            partIds.add(inquiryPartsVo.getPartsId());
        }
        // //前端传过来的供应商ID集合
        Set<Integer> supplierIds = new HashSet<>();
        for (InquirySupplierVo inquirySupplierVo:inquiryTotal.getSupplierVoList()){
            supplierIds.add(inquirySupplierVo.getSupplierId());
        }
        //根据备件ID集合查询出生产这些备件的供应商
        Set<P2S> p2SSet = inquiryMapper.queryP2SByPartIds(partIds);
//        List<PartIdAndSupplierId> partIdAndSupplierIdList = new ArrayList<>();

        for (Integer partId: partIds){
            List<Integer> returnSupplierIds = new ArrayList<>();
            for (P2S p2S1 : p2SSet){
                if (p2S1.getPartsId()==partId){
                    returnSupplierIds.add(p2S1.getSupplierId());
                }
            }
            //用前端的供应商ID集合与后台查询出来的供应商ID集合进行对比，找出不同的数据,disjoint为true时没有交集，false有交集。
            if (Collections.disjoint(returnSupplierIds, supplierIds)){
               return ResultVo.error("500",partId+"此备件没有可生产它的供应商，可生产他的供应商为："+returnSupplierIds);
            }
        }
        return ResultVo.success(inquiryTotal);

    }

/*    PartIdAndSupplierId partIdAndSupplierId = new PartIdAndSupplierId();
                    partIdAndSupplierId.setPartsId(partId);
                    partIdAndSupplierId.setSupplierId(p2S1.getSupplierId());
                    partIdAndSupplierIdList.add(partIdAndSupplierId);*/

    public Result<InquiryTotal> validateExist(InquiryTotal inquiryTotal){
        //验证备件是否存在，如果不存在提示用户哪个备件不存在

        //页面请求过来的备件编码集合
        Set<String> partCodeList = new HashSet<>();
        for (InquiryPartsVo inquiryPartsVo: inquiryTotal.getInquiryPartsVoList()){
            partCodeList.add(inquiryPartsVo.getPartsCode());
        }
        //根据备件code集合查询备件信息,如果哪个备件不存在，提示用户该备件不存在
        List<InquiryParts> inquiryPartsList = inquiryMapper.queryByPartCodes(partCodeList);
        //根据备件编码集合查询数据库响应回来的编码集合
        Set<String> returnCodeSet = new HashSet<>();
        for (InquiryParts inquiryParts :inquiryPartsList){
            returnCodeSet.add(inquiryParts.getPartsCode());
        }
        if (partCodeList.size()!=inquiryPartsList.size()){
            partCodeList.removeAll(returnCodeSet);
            return ResultVo.error("500","不存在的备件编码为："+partCodeList);
        }
        return ResultVo.success(inquiryTotal);

    }


    public Result<InquiryTotal> validate(InquiryTotal inquiryTotal){
       /* 1.验证备件集合、供应商集合是否存在重复的数据
       */
        //备件集合
        List<InquiryPartsVo> inquiryPartsVoList = inquiryTotal.getInquiryPartsVoList();
        //供应商集合
        List<InquirySupplierVo> inquirySupplierVoList = inquiryTotal.getSupplierVoList();
        //1.验证备件集合、供应商集合是否存在重复的数据
        /*Set<String> partSet = new HashSet<>();
        for (InquiryPartsVo inquiryPartsVo: inquiryPartsVoList){
            partSet.add(inquiryPartsVo.getPartsCode());
        }
        if (partSet.size()!=inquiryPartsVoList.size()){
            return ResultVo.error("500","备件存在重复的数据！");
        }*/
        //优化代码，准确提示给用户哪个备件不存在
        List<String> partList = new ArrayList<>();
        for (InquiryPartsVo inquiryPartsVo: inquiryPartsVoList){
            partList.add(inquiryPartsVo.getPartsCode());
        }
        List<String> codeList = new ArrayList<>();
        Set<String> errorCodeList = new HashSet<>();
        for (String code: partList){
            if (!codeList.contains(code)){
                codeList.add(code);
            }else {
                errorCodeList.add(code);
            }
        }
        if (errorCodeList.size()>0){
            return ResultVo.error("500","存在重复的备件信息，重复的备件为"+errorCodeList);
        }
        //2.验证备件集合、供应商集合是否存在重复的数据

        return ResultVo.success(inquiryTotal);
    }
}
