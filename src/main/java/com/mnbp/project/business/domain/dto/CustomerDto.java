package com.mnbp.project.business.domain.dto;

import java.io.Serializable;

import com.mnbp.framework.aspectj.lang.annotation.Excel;
import com.mnbp.project.business.domain.Customer;

import lombok.Data;

/**
 * 客户对象 customerDto
 *
 * @author: yinhui xu
 * @date: 2020/1/3 14:25
 */
@Data
public class CustomerDto extends Customer implements Serializable {

    private static final long serialVersionUID = 8576047255796373015L;

    /** 导入失败原因 */
    @Excel(name = "失败原因", type = Excel.Type.EXPORT)
    private String failureCause;

}
