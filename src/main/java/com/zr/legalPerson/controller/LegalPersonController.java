package com.zr.legalPerson.controller;

import com.zr.legalPerson.mode.vo.LegalPersonVo;
import com.zr.legalPerson.service.LegalPersonService;
import com.zr.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lenovo on 2019/6/12.
 */
@RestController
public class LegalPersonController {
    @Autowired
    private LegalPersonService legalPersonService;

    /**
     * 根据用户ID查询法人列表信息
     * @param id
     * @return
     */
    @GetMapping("/queryLegalPersonByUserId")
    public Result<List<LegalPersonVo>> queryLegalPersonByUserId(@RequestParam("userId" ) Integer id){
        return legalPersonService.queryLegalPersonByUserId(id);
    }

}


