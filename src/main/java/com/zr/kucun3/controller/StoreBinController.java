package com.zr.kucun3.controller;

import com.zr.kucun3.model.CustomerInfo;
import com.zr.kucun3.service.StoreBinService;
import com.zr.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    @PostMapping("/importFile")
    public Result<List<CustomerInfo>> importFile(MultipartFile file)throws Exception{
       return storeBinService.importFile(file);
    }
    /**
     * 导出
     *
     */
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse resp,String plantCode) throws Exception{
        storeBinService.exportExcel(resp);
    }
}
