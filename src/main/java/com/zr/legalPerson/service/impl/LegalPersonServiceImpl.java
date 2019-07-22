package com.zr.legalPerson.service.impl;

import com.zr.legalPerson.mapper.LegalPersonMapper;
import com.zr.legalPerson.mode.vo.LegalPersonVo;
import com.zr.legalPerson.service.LegalPersonService;
import com.zr.util.Result;
import com.zr.util.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2019/6/13.
 */
@Service
public class LegalPersonServiceImpl implements LegalPersonService {
    @Autowired
    private LegalPersonMapper legalPersonMapper;

    @Override
    public Result<List<LegalPersonVo>> queryLegalPersonByUserId(Integer id) {
        List<LegalPersonVo> legalPersonVos = legalPersonMapper.queryLegalPersonByUserId(id);
        return ResultVo.success(legalPersonVos);
    }
}

