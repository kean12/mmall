package com.mmall.common;

/**
 * Created by Keane on 6/25/17.
 */
public enum ResponseCode {
    SUCCESS(0, "SUCCSESS"), ERROR(1, "ERROR"), NEED_LOGIN(10, "NEED_LOGIN"), ILLEGAL_ARGUMENT(2,
        "INLLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;

    }

    public String getDesc() {
        return desc;
    }

}
