package com.mnbp.project.business.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.mnbp.common.converter.SexStringConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户对象 customerDto
 *
 * @author: yinhui xu
 * @date: 2020/1/3 14:25
 */
@Data
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 8576047255796373015L;

    /** 客户姓名 */
    @ExcelProperty("姓名")
    private String customerName;

    /** 证件类型，数据字典 */
    @ExcelProperty("证件类型")
    private String idType;

    /** 证件号 */
    @ExcelProperty("证件号")
    private String idNumber;

    /** 性别，（0男 1女 2未知） */
    @ExcelProperty(value = "性别", converter = SexStringConverter.class)
    private String sex;

    /** 出生日期 */
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty("出生日期")
    private Date birthdate;

    /** 联系电话 */
    @ExcelProperty("联系电话")
    private String phonenumber;

    /** 到检日期（体检） */
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty("到检日期")
    private Date examinatidonDate;

    /** 省份 */
    @ExcelProperty("省份")
    private String province;

    /** 城市 */
    @ExcelProperty("城市")
    private String city;

    /** 分公司名称 */
    @ExcelProperty("分公司名称")
    private String branchName;

    /** 方案代码 */
    @ExcelProperty("方案代码")
    private String schemeCode;

    /** 导入失败原因 */
    @ExcelProperty("失败原因")
    private String failureCause;

}
