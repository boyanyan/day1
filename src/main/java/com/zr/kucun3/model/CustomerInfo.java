package com.zr.kucun3.model;

import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
public class CustomerInfo {
	//客户id
    private String customerId;
    //客户名称
    private String customerName;
    //客户状态
    private String statusName;
    //客户状态（入库操作）
    private Boolean status;
    //公司
    private String company;

}