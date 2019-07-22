package com.zr.kucun.controller;

import com.zr.kucun.model.CustomerInfo;
import com.zr.kucun.service.StoreBinService;
import com.zr.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Lx on 2018/10/22.
 */
@RestController
public class StoreBinController {
    @Autowired
    private StoreBinService storeBinService;
    /**
     * 导入
     */
    @PostMapping("importFile")
    public Result<List<CustomerInfo>> importFile(MultipartFile file)throws Exception{
       return storeBinService.importFile(file);
    }
}
