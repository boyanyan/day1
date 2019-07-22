package com.zr.inquiry2.service;

import com.zr.inquiry1.model.vo.InquiryTotalAddVo;
import com.zr.inquiry1.model.vo.InquiryTotalVo;
import com.zr.util.Result;

/**
 * Created by Lx on 2018/10/16.
 */
public interface InquiryService {
    Result<InquiryTotalVo> addInquiry(InquiryTotalAddVo inquiryTotalAddVo);
    int test();
}
