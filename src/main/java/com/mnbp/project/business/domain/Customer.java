package com.mnbp.project.business.domain;

import java.util.Date;

import com.mnbp.framework.aspectj.lang.annotation.Excel;
import com.mnbp.framework.web.domain.BaseEntity;

import lombok.Data;

/**
 * 客户对象 customer
 * 
 * @author xuyinhui
 * @date 2019-12-26
 */
@Data
public class Customer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 客户姓名 */
    @Excel(name = "姓名")
    private String customerName;

    /** 证件类型，数据字典 */
    @Excel(name = "证件类型")
    private String idType;

    /** 证件号 */
    @Excel(name = "证件号")
    private String idNumber;

    /** 性别，（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 出生日期 */
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthdate;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phonenumber;

    /** 到检日期（体检） */
    @Excel(name = "到检日期", dateFormat = "yyyy-MM-dd")
    private Date examinatidonDate;

    /** 省份 */
    @Excel(name = "省份")
    private String province;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 分公司名称 */
    @Excel(name = "分公司名称")
    private String branchName;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 方案代码 */
    @Excel(name = "方案代码")
    private String schemeCode;
}
