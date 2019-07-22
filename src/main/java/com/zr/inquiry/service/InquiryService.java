package com.zr.inquiry.service;

import com.zr.inquiry.model.vo.*;
import com.zr.plant.mode.AllRecords;
import com.zr.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lenovo on 2019/6/23.
 */

public interface InquiryService {
    Result<InquiryPartsVo> addInquiry( InquiryTotalAddVo inquiryTotalAddVo);
    Result<InquiryPartsVo> updateInquiry(InquiryTotalUpdateVo inquiryTotalUpdateVo);
    Result<AllRecords<InquiryPartsVo>> queryInquiryPage(InquirySelectVo inquirySelectVo );
    Result<InquiryTotalVo> queryInquiryById(Integer id);
    Result<InquiryStatusVo> updateStatus( InquiryStatusVo inquiryStatusVo);
    Result<AllRecords<InquiryQuationSelectVo>> queryQuationPage(InquiryQuationSelectVo inquiryQuationSelectVo);

}
