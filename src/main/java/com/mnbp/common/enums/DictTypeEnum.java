package com.mnbp.common.enums;

/**
 * 数据字典类型 Enum
 *
 * @author: yinhui xu
 * @date: 2020/1/9 11:26
 */
public enum DictTypeEnum {

    ID_TYPE("id_type", "证件类型");

    private final String code;
    private final String info;

    DictTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
