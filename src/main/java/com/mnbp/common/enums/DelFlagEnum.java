package com.mnbp.common.enums;

/**
 * delFlag枚举
 *
 * @author: yinhui xu
 * @date: 2020/1/14 13:21
 */
public enum DelFlagEnum {
    OK("0", "正常"), DELETED("2", "删除");

    private final String code;
    private final String info;

    DelFlagEnum(String code, String info) {
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
