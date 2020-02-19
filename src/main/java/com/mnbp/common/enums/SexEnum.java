package com.mnbp.common.enums;

/**
 * 性别枚举
 *
 * @author: xu_yh
 * @date: 2020/2/13 14:37
 * @version: V1.0
 */
public enum SexEnum {
    MALE("0", "男"), FEMALE("1", "女"), UNKNOWN("2", "未知");

    private final String code;
    private final String info;

    SexEnum(String code, String info) {
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
