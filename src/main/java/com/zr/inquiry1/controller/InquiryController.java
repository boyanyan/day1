package com.zr.inquiry1.controller;

import com.zr.inquiry1.model.vo.InquiryTotalAddVo;
import com.zr.inquiry1.model.vo.InquiryTotalVo;
import com.zr.inquiry1.service.InquiryService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Lx on 2018/10/16.
 */
@RestController
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;
    @PostMapping("/inquiry/addInquiry")
   /* 接收参数的验证：
 1.新增时验证表头信息的参数不为空
2.验证备件明细集合不为空
3.验证备件明细中询价数量不能为空、交货周期不能为空
4.验证供应商集合不为空
*/
    public Result<InquiryTotalVo> addInquiry(@RequestBody @Valid InquiryTotalAddVo inquiryTotalAddVo, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVo.error("500",bindingResult.getFieldError().getDefaultMessage());
        }
        return inquiryService.addInquiry(inquiryTotalAddVo);
    }
}
