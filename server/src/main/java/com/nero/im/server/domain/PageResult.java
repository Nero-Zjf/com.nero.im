package com.nero.im.server.domain;

/**
 * 用于分页方法返回多数据
 */
public class PageResult {
    /**
     * 当前页数据总条数
     */
    private long dataCount;
    /**
     * 总共多少页
     */
    private long pageCount;
    /**
     * 总共多少条
     */
    private long totalCount;
    /**
     * 第几页
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 实例化时指定第几页、每页数量
     * @param pageNum
     * @param pageSize
     */
    public PageResult(int pageNum,int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageResult() {
    }

    /**
     * 设置分页结果
     * @param dataCount 当前页条数
     * @param pageCount 共几页
     * @param totalCount 总共几条
     */
    public void setResult(long dataCount,long pageCount,long totalCount ) {
        this.dataCount = dataCount;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
