package com.mnbp.common.enums;

/**
 * 证件类型枚举
 *
 * @author: yinhui xu
 * @date: 2020/1/9 10:43
 */
public enum IdTypeEnum {

    IDENTITY_CARD("10", "居民身份证"), PASSPORT("11", "护照");

    private final String code;
    private final String info;

    IdTypeEnum(String code, String info) {
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
