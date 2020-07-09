package com.nero.im.server.domain;



import java.util.Collection;

public class RequestResult {
    /**
     * 返回结果
     */
    private Object result;

    /**
     * 分页结果
     */
    private PageResult pageResult;

    /**
     * 错误编码
     */
    private String errorNum;
    /**
     * 错误信息
     */
    private String errorInfo;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
        if (Collection.class.isAssignableFrom(result.getClass()))
        {
            this.pageResult = new PageResult();
            this.pageResult.setDataCount(((Collection) result).size());
        }
    }

    public void setResult(Object result,PageResult pageResult) {
        this.result = result;
        this.pageResult = pageResult;
    }

    public String getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    public void setErrorNum(String errorNum,String errorMsg) {
        this.errorNum = errorNum;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public PageResult getPageResult() {
        return pageResult;
    }

    public void setPageResult(PageResult pageResult) {
        this.pageResult = pageResult;
    }
}
