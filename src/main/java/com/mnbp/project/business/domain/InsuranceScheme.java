package com.mnbp.project.business.domain;

import com.mnbp.framework.aspectj.lang.annotation.Excel;
import com.mnbp.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 方案对象 insurance_scheme
 * 
 * @author yinhui xu
 * @date 2019-12-26
 */
public class InsuranceScheme extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 方案代码 */
    @NotEmpty(message = "方案代码不能为空")
    @Length(max = 8, min = 8, message = "方案代码长度必须为8位")
    @Excel(name = "方案代码")
    private String schemeCode;

    /** 方案名称 */
    @NotEmpty(message = "方案名称不能为空")
    @Excel(name = "方案名称")
    private String schemeName;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setSchemeCode(String schemeCode) 
    {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() 
    {
        return schemeCode;
    }
    public void setSchemeName(String schemeName) 
    {
        this.schemeName = schemeName;
    }

    public String getSchemeName() 
    {
        return schemeName;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("schemeCode", getSchemeCode())
            .append("schemeName", getSchemeName())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
