package com.mnbp.project.weixin.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mnbp.project.business.domain.SchemeClause;
import lombok.Data;

/**
 * 客户承保信息VO
 *
 * @author: yinhui xu
 * @date: 2019/12/30 11:03
 */
@Data
public class WeixinInsuranceInfoVo implements Serializable {

    private static final long serialVersionUID = -1666848300017707684L;

    /**
     * 保险人
     */
    private String customerName;

    /**
     * 保障期限
     */
    private Date exminatidonDate;

    /**
     * 保障期限时间戳
     */
    private long exDateStamp;

    /**
     * 条款list
     */
    List<SchemeClause> schemeClauseList;
}
