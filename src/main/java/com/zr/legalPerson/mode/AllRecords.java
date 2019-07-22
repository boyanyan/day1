package com.zr.legalPerson.mode;

import lombok.Data;

import java.util.List;


/**
 *
 */
@Data
public class AllRecords<T> {

    //总页数
    private long totalPage;
    //总数量
    private long totalNumber;
    //索引
    private int pageIndex;
    //每页条数
    private long pageSize;
    //分页的列表数据
    private List<T> dataList;

    //计算总页数
    public void resetTotalNumber(long totalNumber) {
        if (totalNumber!=0){
            this.totalNumber = totalNumber;
            this.totalPage = totalNumber / this.pageSize;
            if(totalNumber % this.pageSize != 0L) {
                ++this.totalPage;
            }
        }

    }

    public AllRecords() {
    }

    public long getTotalPage() {
        return this.totalPage;
    }

    public long getTotalNumber() {
        return this.totalNumber;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof AllRecords)) {
            return false;
        } else {
            AllRecords<?> other = (AllRecords)o;
            if(!other.canEqual(this)) {
                return false;
            } else if(this.getTotalPage() != other.getTotalPage()) {
                return false;
            } else if(this.getTotalNumber() != other.getTotalNumber()) {
                return false;
            } else if(this.getPageIndex() != other.getPageIndex()) {
                return false;
            } else if(this.getPageSize() != other.getPageSize()) {
                return false;
            } else {
                Object this$dataList = this.getDataList();
                Object other$dataList = other.getDataList();
                if(this$dataList == null) {
                    if(other$dataList != null) {
                        return false;
                    }
                } else if(!this$dataList.equals(other$dataList)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof AllRecords;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        long $totalPage = this.getTotalPage();
        result = result * 59 + (int)($totalPage >>> 32 ^ $totalPage);
        long $totalNumber = this.getTotalNumber();
        result = result * 59 + (int)($totalNumber >>> 32 ^ $totalNumber);
        result = result * 59 + this.getPageIndex();
        long $pageSize = this.getPageSize();
        result = result * 59 + (int)($pageSize >>> 32 ^ $pageSize);
        Object $dataList = this.getDataList();
        result = result * 59 + ($dataList == null?43:$dataList.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "AllRecords(totalPage=" + this.getTotalPage() + ", totalNumber=" + this.getTotalNumber() + ", pageIndex=" + this.getPageIndex() + ", pageSize=" + this.getPageSize() + ", dataList=" + this.getDataList() + ")";
    }

}
