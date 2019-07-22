package com.zr.plant.mode;

import lombok.Data;

/**
 * 分装分页参数VO，所有用到分页的实体继承此类，接受分页参数
 * Created by wujian on 2017/5/16.
 */
public class PageVO {

    //当前页数
    private int pageIndex;

    //偏移量
    private int offset;

    //默认每页条数
    private int pageSize;

    public  void  setPageIndex(int pageIndex){
        this.pageIndex = pageIndex;
        initParamter();
    }

    public  void  setPageSize (int pageSize){
        this.pageSize = pageSize;
        initParamter();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 根据前端传值，计算分页参数
     */
    private void initParamter(){
        if(pageIndex<1){
            pageIndex = 1;//默认第一页
        }
        if (this.pageSize == 0) {
            this.pageSize = 10;//默认每页显示10条
        }

        final int offset = (this.pageIndex - 1) * this.pageSize;
        setOffset(offset);
    }

}
