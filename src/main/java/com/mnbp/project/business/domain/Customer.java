package com.mnbp.project.business.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mnbp.framework.aspectj.lang.annotation.Excel;
import com.mnbp.framework.web.domain.BaseEntity;

/**
 * 客户对象 customer
 * 
 * @author xuyinhui
 * @date 2019-12-26
 */
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 证件类型，数据字典 */
    @Excel(name = "证件类型，数据字典")
    private String idType;

    /** 证件号 */
    @Excel(name = "证件号")
    private String idNumber;

    /** 性别，（0男 1女 2未知） */
    @Excel(name = "性别，", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 出生日期 */
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthdate;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phonenumber;

    /** 到检日期（体检） */
    @Excel(name = "到检日期", readConverterExp = "体检")
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

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setIdType(String idType) 
    {
        this.idType = idType;
    }

    public String getIdType() 
    {
        return idType;
    }
    public void setIdNumber(String idNumber) 
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber() 
    {
        return idNumber;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setBirthdate(Date birthdate) 
    {
        this.birthdate = birthdate;
    }

    public Date getBirthdate() 
    {
        return birthdate;
    }
    public void setPhonenumber(String phonenumber) 
    {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() 
    {
        return phonenumber;
    }
    public void setExaminatidonDate(Date examinatidonDate) 
    {
        this.examinatidonDate = examinatidonDate;
    }

    public Date getExaminatidonDate() 
    {
        return examinatidonDate;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setBranchName(String branchName) 
    {
        this.branchName = branchName;
    }

    public String getBranchName() 
    {
        return branchName;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setSchemeCode(String schemeCode) 
    {
        this.schemeCode = schemeCode;
    }

    public String getSchemeCode() 
    {
        return schemeCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerName", getCustomerName())
            .append("idType", getIdType())
            .append("idNumber", getIdNumber())
            .append("sex", getSex())
            .append("birthdate", getBirthdate())
            .append("phonenumber", getPhonenumber())
            .append("examinatidonDate", getExaminatidonDate())
            .append("province", getProvince())
            .append("city", getCity())
            .append("branchName", getBranchName())
            .append("delFlag", getDelFlag())
            .append("schemeCode", getSchemeCode())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
