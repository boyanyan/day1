package com.zr.inquiry.mapper;

import com.zr.inquiry.model.InquiryParts;
import com.zr.inquiry.model.InquiryTotal;
import com.zr.inquiry.model.Parts;
import com.zr.inquiry.model.vo.*;
import com.zr.inquiry1.model.InquirySupplier;
import com.zr.inquiry1.model.P2S;
import com.zr.util.Result;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by lenovo on 2019/6/23.
 */
public interface InquiryMapper {
    List<InquiryParts> queryByPartCodes(Set<String> partCodes);
    Set<P2S> queryP2SByPartIds(Set<String> partIds);
    int insertInquiryTotal(InquiryTotal inquiryTotal);
    int updateInquiryTotal(InquiryTotal inquiryTotal);
    int deletePartsByInquiryId(@Param("id") Integer id);
    int deleteSupplierByInquiryId(@Param("id") Integer id);
    int insertInquiryParts(@Param("inquiryPartsVoList") List<InquiryPartsVo> inquiryPartsVoList);
    int insertInquirySupplier(@Param("inquirySupplierVoList") List<InquirySupplierVo> inquirySupplierVoList);
    int queryCount(InquirySelectVo inquirySelectVo);//总条数
    List<InquiryTotal> queryPage(InquirySelectVo inquirySelectVo);//分页

    InquiryTotalVo queryInquiryById(Integer id);
    List<InquiryPartsVo> queryPartByInquiryId(Integer id);
    List<InquirySupplierVo> querySupplierByInquiryId(Integer id);
    int updateStatus(InquiryStatusVo inquiryStatusVo);

    List<Parts> findPartBySupplierId(@Param("supplierId") Integer supplierId);
    List<InquiryQuationPartsVo> findInquiryQuationPartPage(InquiryQuationSelectVo inquiryQuationSelectVoList);
    int findInquiryQuationPartPageCount(InquiryQuationSelectVo inquiryQuationSelectVoList);

}
