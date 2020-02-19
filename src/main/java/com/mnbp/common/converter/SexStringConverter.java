/**
 * @author: xu_yh
 * @date: 2020/2/13 14:34
 * @Copyright ©2020 xu_yh. All rights reserved.
 */
package com.mnbp.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.mnbp.common.enums.SexEnum;

/**
 * excel性别字符串转换器
 *
 * @author: xu_yh
 * @date: 2020/2/13 14:34
 * @version: V1.0
 */
public class SexStringConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty,
            GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        if (SexEnum.MALE.getInfo().equals(value)) {
            return SexEnum.MALE.getCode();
        }
        if (SexEnum.FEMALE.getInfo().equals(value)) {
            return SexEnum.FEMALE.getCode();
        }

        return SexEnum.UNKNOWN.getCode();
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty excelContentProperty,
            GlobalConfiguration globalConfiguration) throws Exception {
        if (SexEnum.MALE.getCode().equals(value)) {
            return new CellData(SexEnum.MALE.getInfo());
        }
        if (SexEnum.FEMALE.getCode().equals(value)) {
            return new CellData(SexEnum.FEMALE.getInfo());
        }

        return new CellData("");
    }
}