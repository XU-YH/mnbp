package com.mnbp.project.business.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.mnbp.common.converter.SexStringConverter;
import com.mnbp.framework.web.domain.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 客户对象 customer
 *
 * @author xuyinhui
 * @date 2019-12-26
 */
@Data
public class Customer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 客户姓名
     */
    @ExcelProperty("姓名")
    @NotNull(message = "姓名不能为空")
    private String customerName;

    /**
     * 证件类型，数据字典
     */
    @ExcelProperty("证件类型")
    @NotNull(message = "证件类型不能为空")
    private String idType;

    /**
     * 证件号
     */
    @ExcelProperty("证件号")
    @NotNull(message = "证件号不能为空")
    private String idNumber;

    /**
     * 性别，（0男 1女 2未知）
     */
    @ExcelProperty(value = "性别", converter = SexStringConverter.class)
    private String sex;

    /**
     * 出生日期
     */
    @ExcelProperty("出生日期")
    @NotNull(message = "出生日期不能为空")
    private Date birthdate;

    /**
     * 联系电话
     */
    @ExcelProperty("联系电话")
    private String phonenumber;

    /**
     * 到检日期（体检）
     */
    @NotNull(message = "到检日期不能为空")
    @ExcelProperty("到检日期")
    private Date examinatidonDate;

    /**
     * 省份
     */
    @NotNull(message = "省份不能为空")
    @ExcelProperty("省份")
    private String province;

    /**
     * 城市
     */
    @NotNull(message = "城市不能为空")
    @ExcelProperty("城市")
    private String city;

    /**
     * 分公司名称
     */
    @NotNull(message = "分公司名称不能为空")
    @ExcelProperty("分公司名称")
    private String branchName;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 方案代码
     */
    @NotNull(message = "方案代码不能为空")
    @Length(max = 8, min = 8, message = "方案代码长度必须为8位")
    @ExcelProperty("方案代码")
    private String schemeCode;

}
