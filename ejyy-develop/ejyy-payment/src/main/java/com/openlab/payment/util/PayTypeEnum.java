package com.openlab.payment.util;

public enum PayTypeEnum {

    WATER(1,"水费"),
    ELECTRIC(2,"电费"),
    GAS(3,"气费");

    private final Integer code;
    private final String message;

    PayTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
