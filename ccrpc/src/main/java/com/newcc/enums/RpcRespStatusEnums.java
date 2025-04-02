package com.newcc.enums;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum RpcRespStatusEnums {

    SUCCESS(200, "请求成功"),
    FAIL(500, "服务器返回错误"),
    NOT_FOUNT(404, "该页面不存在");

    private final Integer code;
    private final String msg;

    RpcRespStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean isSuccessful(Integer code){
     return code.equals(SUCCESS.getCode());
    }

    public static boolean isFailed(Integer code){
        return !isSuccessful(code);
    }

}
