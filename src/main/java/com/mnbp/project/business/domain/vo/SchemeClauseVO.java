package com.mnbp.project.business.domain.vo;

import com.mnbp.framework.aspectj.lang.annotation.Excel;

import lombok.Data;

/**
 * 方案条款VO对象
 *
 * @author: yinhui xu
 * @date: 2019/12/27 15:15
 */
@Data
public class SchemeClauseVO {
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

    /**
     * 方案代码
     */
    @Excel(name = "方案代码")
    private String schemeCode;
}