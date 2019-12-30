package com.mnbp.project.business.domain.bo;

import lombok.Data;

/**
 * 方案条款DTO对象
 *
 * @author: yinhui xu
 * @date: 2019/12/27 14:09
 */
@Data
public class SchemeClauseBO {

    /** 方案名称 */
    private String schemeCode;

    /** 条款名称 */
    private String clauseName;
}
