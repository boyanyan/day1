package com.zr.inquiry2.mapper;

import com.zr.inquiry1.model.InquiryParts;
import com.zr.inquiry1.model.InquiryTotal;
import com.zr.inquiry1.model.P2S;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Created by Lx on 2018/10/16.
 */
@Mapper
public interface InquiryMapper {

//    List<InquiryParts> queryByPartCodes(Set<String> partCodes);
    Set<P2S> queryP2SByPartIds(Set<Integer> partIds);
    int ininsertInquiryTotal(InquiryTotal inquiryTotal);
    void insertInquiryTotal(InquiryTotal inquiryTotal);
}
