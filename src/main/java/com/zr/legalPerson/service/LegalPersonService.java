package com.zr.legalPerson.service;

import com.zr.legalPerson.mode.vo.LegalPersonVo;
import com.zr.util.Result;

import java.util.List;

/**
 * Created by lenovo on 2019/6/13.
 */
public interface LegalPersonService {
    Result<List<LegalPersonVo>> queryLegalPersonByUserId(Integer id);
}
