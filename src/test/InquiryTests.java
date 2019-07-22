package com.example.demo;

import com.example.demo.inquiry.model.InquiryParts;
import com.example.demo.inquiry.model.vo.InquiryPartsVo;
import com.example.demo.inquiry.model.vo.InquiryTotalAddVo;
import com.example.demo.inquiry.model.vo.InquiryTotalVo;
import com.example.demo.inquiry.service.InquiryService;
import com.example.demo.util.Result;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InquiryTests {
	@Autowired
	private InquiryService inquiryService;

	@Test
	public void contextLoads() {
		InquiryTotalAddVo inquiryTotalAddVo =new InquiryTotalAddVo();
        List<InquiryPartsVo> inquiryPartsVoList = new ArrayList<>();
        InquiryPartsVo inquiryPartsVo = new InquiryPartsVo();
        inquiryPartsVo.setInquiryId(1);
        inquiryPartsVoList.add(inquiryPartsVo);
        inquiryTotalAddVo.setInquiryPartsVoList(inquiryPartsVoList);
        Result<InquiryTotalVo> inquiryTotalVoResult = inquiryService.addInquiry(inquiryTotalAddVo);
        Assert.assertEquals(inquiryTotalVoResult.getData().getInquiryNo(),1);
    }

    @Test
    public void  test(){
	    int i = inquiryService.test();
	    //断言
        Assert.assertEquals(i,1);
    }

    @Test
    public void testBigDecimal(){
        //加法
        BigDecimal one = new BigDecimal("2");
        BigDecimal two = new BigDecimal("3");
        BigDecimal add = one.add(two);//2+3
        System.out.println("加法的结果为"+add);
        //减法
        BigDecimal subtract = two.subtract(one);
        BigDecimal subtract1 = one.subtract(two);//2-3
        System.out.println("减法的结果为==="+subtract);
        System.out.println("减法2-3的结果为==="+subtract1);
        //乘法
        BigDecimal multiply = one.multiply(two);//2*3
        System.out.println("乘法的结果为==="+multiply);
        //除法
        BigDecimal divide = one.divide(two,5,BigDecimal.ROUND_HALF_UP);//2/3  保留5位有效数字
        System.out.println("除法的结果为==="+divide);
        //四舍五入
        BigDecimal divide1 = one.divide(two,2, BigDecimal.ROUND_HALF_UP);
        System.out.println("四舍五入的结果是====="+divide1);
        //保留X位小数点
    }

 /*
 比较大小或者值是否相等，用compareTo方法
 ROUND_CEILING    //向正无穷方向舍入
    ROUND_DOWN    //向零方向舍入
    ROUND_FLOOR    //向负无穷方向舍入
    ROUND_HALF_DOWN    //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向下舍入, 例如1.55 保留一位小数结果为1.5
    ROUND_HALF_EVEN    //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP，如果是偶数，使用ROUND_HALF_DOWN
    ROUND_HALF_UP    //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
    ROUND_UNNECESSARY    //计算结果是精确的，不需要舍入模式
    ROUND_UP    //向远离0的方向舍入*/

}
