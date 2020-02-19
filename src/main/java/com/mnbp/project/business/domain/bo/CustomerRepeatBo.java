/**
 * @author: xu_yh
 * @date: 2020/2/14 15:29
 * @Copyright ©2020 xu_yh. All rights reserved.
 */
package com.mnbp.project.business.domain.bo;

import lombok.Data;

import java.util.Date;

/**
 * 人员导入，证件号和到检日期唯一
 *
 * @author: xu_yh
 * @date: 2020/2/14 15:29
 * @version: V1.0
 */
@Data
public class CustomerRepeatBo {

    public CustomerRepeatBo(String idNumber, Date examinatidonDate) {
        this.idNumber = idNumber;
        this.examinatidonDate = examinatidonDate;
    }

    /**
     * 证件号
     */
    private String idNumber;

    /**
     * 到检日期（体检）
     */
    private Date examinatidonDate;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CustomerRepeatBo that = (CustomerRepeatBo) o;

        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null)
            return false;
        return examinatidonDate != null ?
                examinatidonDate.equals(that.examinatidonDate) :
                that.examinatidonDate == null;
    }

    @Override
    public int hashCode() {
        int result = idNumber != null ? idNumber.hashCode() : 0;
        result = 31 * result + (examinatidonDate != null ? examinatidonDate.hashCode() : 0);
        return result;
    }
}