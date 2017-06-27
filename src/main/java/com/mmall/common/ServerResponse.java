package com.mmall.common;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.net.SocketPermission;

/**
 * Created by Keane on 6/25/17.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的湿乎乎如果是null的对象key也会消失

public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;

    }



    private ServerResponse(int stauts, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int stauts, T data, String msg) {

        this.status = status;
        this.data = data;
        this.msg = msg;

    }

    public ServerResponse(int code, String msg, T data) {
    }
    @JsonIgnore
//使之不在json 序列化结果当中
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }
public static <T> ServerResponse<T> creatByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
}
public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
    return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);

}

public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage)
{
    return new ServerResponse<T>(errorCode,errorMessage);
}

}




