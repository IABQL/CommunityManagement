package com.openlab.payment.util;

public enum PaymentTaskType {
    SAVE("1","存储数据"),
    DELET("2","删除数据");


    private String id;
    private String message;
    PaymentTaskType(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
