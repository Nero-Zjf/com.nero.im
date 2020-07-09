package com.nero.im.server.domain.rm;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

/**
 * 权限模块
 * @author Nero
 * @version 1.0
 * @created 08-六月-2018 16:03:48
 */
public class RightModule   {

    /**
     * 权限模块ID
     */
    private long rightModuleId;
    /**
     * 模块名称
     */
    private String moduleName;

    public RightModule(long rightModuleId, String moduleName) {
        this.rightModuleId = rightModuleId;
        this.moduleName = moduleName;
    }

    public long getRightModuleId() {
        return rightModuleId;
    }

    public void setRightModuleId(long rightModuleId) {
        this.rightModuleId = rightModuleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}