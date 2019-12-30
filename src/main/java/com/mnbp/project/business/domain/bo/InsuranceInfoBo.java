package com.mnbp.project.business.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户承保信息BO
 *
 * @author: yinhui xu
 * @date: 2019/12/30 11:01
 */
@Data
public class InsuranceInfoBo implements Serializable {
    private static final long serialVersionUID = 7308264192913801913L;

    /** 客户姓名 */
    private String customerName;

    /** 证件号 */
    private String idNumber;
}
