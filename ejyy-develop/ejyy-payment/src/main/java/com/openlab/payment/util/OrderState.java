package com.openlab.payment.util;

public enum OrderState {
    FALSE(1,"订单处理失败"),
    DEALING(2,"订单处理中"),
    FINNISH(3,"订单处理成功");

    private final Integer state;
    private final String message;

    OrderState(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public Integer getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }
}
