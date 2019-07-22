package com.zr.feignproxy;

import com.zr.inquiry1.model.InquiryParts;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

/**
 * Created by lenovo on 2019/7/15.
 */
@FeignClient(name = "provider")
public interface FeignProxy {

    @PostMapping("/queryByPartCodes")
    public List<InquiryParts> queryByPartCodes (@RequestBody Set<String> codeList);
}
