package com.zr.kucun3.service;

import com.zr.kucun3.model.CustomerInfo;
import com.zr.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Lx on 2018/10/22.
 */
public interface StoreBinService {
    Result<List<CustomerInfo>> importFile(MultipartFile multipartFile)throws Exception;
    void exportExcel(HttpServletResponse response)throws Exception;
}
