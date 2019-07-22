package com.zr.kucun1.service;

import com.zr.kucun.model.CustomerInfo;
import com.zr.util.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Lx on 2018/10/22.
 */
public interface StoreBinService {
    Result<List<CustomerInfo>> importFile(MultipartFile multipartFile)throws Exception;
}
