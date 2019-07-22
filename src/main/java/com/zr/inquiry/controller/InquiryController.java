package com.zr.inquiry.controller;

import com.zr.inquiry.model.InquiryTotal;
import com.zr.inquiry.model.vo.*;
import com.zr.inquiry.service.InquiryService;
import com.zr.plant.mode.AllRecords;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lenovo on 2019/6/23.
 */
@RestController
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    /**
     *报价列表分页查询
     */
    @PostMapping("/inquiry/queryQuationPage")
    public Result<AllRecords<InquiryQuationSelectVo>> queryQuationPage(@RequestBody  InquiryQuationSelectVo inquiryQuationSelectVo){
        return inquiryService.queryQuationPage(inquiryQuationSelectVo);
    }

    /**
     *修改状态
     */
    @PostMapping("/inquiry/updateStatus")
    public Result<InquiryStatusVo> updateStatus(@RequestBody @Valid InquiryStatusVo inquiryStatusVo,BindingResult bindingResult ){
        if (bindingResult.hasErrors()) {
            return ResultVo.error("500",bindingResult.getFieldError().getDefaultMessage());
        }
        return inquiryService.updateStatus(inquiryStatusVo);
    }

    /**
     * 分页查询接口
     * @param queryInquiryPage
     * @return
     */
    /**
     * 查看询价单信息
     */
    @GetMapping("/inquiry/queryInquiryById")
    public Result<InquiryTotalVo> queryInquiryById(@RequestParam("id") Integer id){
        return inquiryService.queryInquiryById(id);
    }

    /**
     * 询价单分页查询接口
     * @param inquirySelectVo
     * @return
     */
    @PostMapping("/inquiry/queryInquiryPage")
    public Result<AllRecords<InquiryPartsVo>> queryInquiryPage(@RequestBody InquirySelectVo inquirySelectVo){//查询不需要必填，不需要验证
        return inquiryService.queryInquiryPage(inquirySelectVo);
    }
    /**
     * 询价单新增接口
     * @param inquiryTotalAddVo
     * @param bindingResult
     * @return
     */
    @PostMapping("/inquiry/addInquiry")
    public Result<InquiryPartsVo> addInquiry(@RequestBody @Valid InquiryTotalAddVo inquiryTotalAddVo, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVo.error("500",bindingResult.getFieldError().getDefaultMessage());
        }
        return inquiryService.addInquiry(inquiryTotalAddVo);
    }
    /**
     * 询价单修改接口
     * @param inquiryTotalupdateVo
     * @param bindingResult
     * @return
     */
    @PostMapping("/inquiry/updateInquiry")
    public Result<InquiryPartsVo> updateInquiry(@RequestBody @Valid InquiryTotalUpdateVo inquiryTotalupdateVo, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVo.error("500",bindingResult.getFieldError().getDefaultMessage());
        }
        return inquiryService.updateInquiry(inquiryTotalupdateVo);
    }
}
