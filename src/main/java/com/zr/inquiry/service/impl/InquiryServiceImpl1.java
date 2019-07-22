package com.zr.inquiry.service.impl;

import com.zr.inquiry.mapper.InquiryMapper;
import com.zr.inquiry.model.InquiryParts;
import com.zr.inquiry.model.InquiryTotal;
import com.zr.inquiry.model.vo.*;
import com.zr.inquiry.service.InquiryService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lenovo on 2019/6/23.
 */
@Service
public class InquiryServiceImpl1 implements InquiryService {
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

//     1.新增时，验证表头信息的参数不能为空
    @Override
    public Result<InquiryPartsVo> addInquiry(InquiryTotalAddVo inquiryTotalAddVo) {
        InquiryTotal inquiryTotal = new InquiryTotal();
        BeanUtils.copyProperties(inquiryTotalAddVo,inquiryTotal);
        //验证数据是否合法
        Result<InquiryTotal> validate = validate(inquiryTotal);
        if (!validate.getStatus()) {
            return ResultVo.error(validate.getErrorCode(),validate.getMessage());
        }
        // 2.验证备件、供应商是否存在，批量查询数据库是否存在此备件、供应商
        Result<InquiryTotal> inquiryTotalResult = validateExist(inquiryTotal);
        if (!inquiryTotalResult.getStatus() ) {
            return ResultVo.error(inquiryTotalResult.getErrorCode(),inquiryTotalResult.getMessage());
        }
        // 3.验证备件是否存在合法的供应商，//        4.验证供应商是否有合法的备件
        Result<InquiryTotal> validateLegal = validateLegal(inquiryTotal);
        if (!validateLegal.getStatus()){
            return ResultVo.error(validateLegal.getErrorCode(),validateLegal.getMessage());
        }
        return ResultVo.success(inquiryTotal);
    }

        //验证主表数据
        //验证备件数据
        //验证供应商数据

    public Result<InquiryTotal> validateLegal(InquiryTotal inquiryTotal) {
        //前端传过来的备件ID集合
        Set<Integer> partIds = new HashSet<>();
        for (InquiryPartsVo inquiryPartsVo:inquiryTotal.getInquiryPartsVoList()){
            partIds.add(inquiryPartsVo.getPartsId());
        }
        //前端传过来的供应商ID集合
        Set<Integer> supplierIds = new HashSet<>();
        for (InquirySupplierVo inquirySupplierVo :inquiryTotal.getSupplierVoList()){
            supplierIds.add(inquirySupplierVo.getSupplierId());
        }
        // 根据备件ID集合查询出生产这些备件的供应商
        Set<PartIdAndSupplierId> partIdAndSupplierIds = InquiryMapper.queryPartIdAndSupplierIdByPartIds(partIds);
        for (Integer partId : partIds){// 遍历前端传过来的备件id
            List<Integer> returnSupplierIds = new ArrayList<>();// 用于接收返回来的供应商id
            for (PartIdAndSupplierId  p2S1 : partIdAndSupplierIds){// 遍历中间表的供应商id
                if (p2S1.getPartsId() == partId){// 如果中间表和备件表有ID一致，表示备件存在
                    returnSupplierIds.add(p2S1.getSupplierId());//用户接收中间表返回来的供应商备件id
                }
            }
            //用前端的供应商ID集合与后台查询出来的供应商ID集合进行对比，找出不同的数据,disjoint为true时没有交集，false有交集。
            if (Collections.disjoint(returnSupplierIds,supplierIds)){
                return ResultVo.error("500",partId+"此备件没有可生产它的供应商，可生产他的供应商为："+returnSupplierIds);
            }
        }
        return ResultVo.success(inquiryTotal);
    }

    //验证备件集合是否存在方法
    public Result<InquiryTotal> validateExist(InquiryTotal inquiryTotal) {
        Set<String> partCodeList = new HashSet<>();
        for (InquiryPartsVo inquiryPartsVo: inquiryTotal.getInquiryPartsVoList()) {
            partCodeList.add(inquiryPartsVo.getPartsCode());
        }
        //根据备件code，查询备件信息,如果备件不存在，提示给用户哪个备件不存在
        List<InquiryParts> inquiryParts = inquiryMapper.quiryByPartCodes(partCodeList);
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

    //验证新增时是否有重方法
    public Result<InquiryTotal> validate(InquiryTotal inquiryTotal) {
     /*  代码逻辑验证：
       1.验证备件集合，供应商集合是否存在重复的数据
       2.验证备件，供应商是否合法
       3.验证备件是否存在合法的供应商
       4.验证供应商是否存在合法的备件
       5.结束日期是否在开启日期之后，结束日期是否在当前日期之后*/


        //备件集合
        List<InquiryPartsVo> inquiryPartsVoList = inquiryTotal.getInquiryPartsVoList();
        //供应商集合
        List<InquirySupplierVo> inquirySupplierVoList = inquiryTotal.getSupplierVoList();
        //1.验证备件集合，供应商集合是否存在重复的数据
        //备件数据验重
       /* Set<String> partsSet = new HashSet<>();//备件编码,去重
        for (InquiryPartsVo inquiryPartsVo:inquiryPartsVoList) {
            partsSet.add(inquiryPartsVo.getPartsCode());//备件编码遍历
        }
        if (partsSet.size() != inquiryPartsVoList.size()) {//如果不相等说明有重复
            return ResultVo.error("500","备件存在重复数据");
        }
        //供应商数据验重
        Set<String> supplierSet = new HashSet<>();
        for (InquirySupplierVo inquirySupplierVo:inquirySupplierVoList) {
            supplierSet.add(inquirySupplierVo.getSupplierCode());
        }
        if (supplierSet.size() != inquirySupplierVoList.size()) {
            return ResultVo.error("500","供应商存在重复数据");
        }*/

       //优化，准确提供给用户哪个备件不存在
        List<String> partList = new  ArrayList();
        for (InquiryPartsVo inquiryPartsVo:inquiryPartsVoList) {
            partList.add(inquiryPartsVo.getPartsCode());
        }
        List<String> codeList = new ArrayList<>();
        List<String> errorCodeList = new ArrayList<>();
        for (String code : partList) {
            if (codeList.contains(code)) {
                codeList.add(code);
            }else{
                errorCodeList.add(code);
            }
        }
        if (codeList.size() > 0) {
            return ResultVo.error("500","备件存在重复数据"+errorCodeList);
        }
        //优化，准确提供给用户哪个供货商不存在
        List<String> supplierList = new ArrayList<>();
        for (InquirySupplierVo inquirySupplierVo:inquirySupplierVoList) {
            supplierList.add(inquirySupplierVo.getSupplierCode());
        }
        List<String> supplierCodeList = new ArrayList<>();
        Set<String> errorSupplierCodeList = new ArrayList<>();
        for (String supplierCode: supplierList) {
            if (supplierCodeList.contains(supplierCode)) {
                supplierCodeList.add(supplierCode);
            }else {
                errorSupplierCodeList.add(supplierCode);
            }
            if (supplierCodeList.size() > 0) {
                return  ResultVo.error("500","供应商存在重复数据"+errorSupplierCodeList);
            }
        }

      /* 2.验证备件明细集合不为空
       3.验证备件明细中询价数量不为空，交货周期不为空
       4.验证供货商集合不为空
       5.验证询价数量是否大于0*/

        return ResultVo.success(inquiryTotal);
    }
}
