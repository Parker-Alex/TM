package com.tmall.enums;

public enum OrderEnum {

    WAIT_PAY(1, "等待支付"),
    WAIT_DELIVERY(2, "等待发送"),
    WAIT_CONFIRM(3, "等待确认"),
    WAIT_COMMENT(4, "等待评价"),
    FINISH(0, "完成"),
    DELETE(5, "删除");

    private int code;

    private String msg;

    OrderEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static OrderEnum getInstance(int index) {
        for (OrderEnum orderEnum : values()) {
            if (orderEnum.getCode() == index) {
                return orderEnum;
            }
        }
        return null;
    }

//    无set方法，防止被修改
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
