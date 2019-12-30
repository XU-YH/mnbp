package com.mnbp.project.business.domain.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 客户承保信息VO
 *
 * @author: yinhui xu
 * @date: 2019/12/30 11:03
 */
@Data
public class InsuranceInfoVo implements Serializable {
    private static final long serialVersionUID = 8665345401377484253L;

    /** 客户姓名 */
    private String customerName;

    /** 证件号 */
    private String idNumber;

    /** 分公司名称 */
    private String branchName;

    /** 方案名称 */
    private String schemeName;

    /** 方案id */
    private String schemeId;
}
