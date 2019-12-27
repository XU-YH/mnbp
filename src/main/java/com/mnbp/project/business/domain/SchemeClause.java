package com.mnbp.project.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mnbp.framework.aspectj.lang.annotation.Excel;
import com.mnbp.framework.web.domain.BaseEntity;

/**
 * 方案条款对象 scheme_clause
 * 
 * @author yinhui xu
 * @date 2019-12-27
 */
public class SchemeClause extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 方案id */
    @Excel(name = "方案id")
    private Integer schemeId;

    /** 条款名称 */
    @Excel(name = "条款名称")
    private String clauseName;

    /** 赔偿限额 */
    @Excel(name = "赔偿限额")
    private Double compensationLimit;

    /** 条款内容 */
    @Excel(name = "条款内容")
    private String clauseContent;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setClauseName(String clauseName) {
        this.clauseName = clauseName;
    }

    public String getClauseName() {
        return clauseName;
    }

    public void setCompensationLimit(Double compensationLimit) {
        this.compensationLimit = compensationLimit;
    }

    public Double getCompensationLimit() {
        return compensationLimit;
    }

    public void setClauseContent(String clauseContent) {
        this.clauseContent = clauseContent;
    }

    public String getClauseContent() {
        return clauseContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
            .append("schemeId", getSchemeId()).append("clauseName", getClauseName())
            .append("compensationLimit", getCompensationLimit()).append("clauseContent", getClauseContent())
            .append("createBy", getCreateBy()).append("createTime", getCreateTime()).append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime()).toString();
    }
}
