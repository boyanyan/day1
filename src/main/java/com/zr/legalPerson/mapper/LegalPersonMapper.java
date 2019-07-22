package com.zr.legalPerson.mapper;

import com.zr.legalPerson.mode.vo.LegalPersonVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by lenovo on 2019/6/13.
 */
@Mapper
public interface LegalPersonMapper {
    List<LegalPersonVo> queryLegalPersonByUserId(Integer id);
}