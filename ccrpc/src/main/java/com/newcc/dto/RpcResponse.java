package com.newcc.dto;

import com.newcc.enums.RpcRespStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {
    private String respId;

    private Integer code;
    private String msg;

    private T data;


    public static <T> RpcResponse<T> success(String respId, T data){
        RpcResponse<T> reqResp = new RpcResponse<T>();
        reqResp.setRespId(respId);
        reqResp.setData(data);
        reqResp.setCode(RpcRespStatusEnums.SUCCESS.getCode());
        return reqResp;
    }

    public static <T> RpcResponse<T> fail(String respId, RpcRespStatusEnums status){
        RpcResponse<T> reqResp = new RpcResponse<>();
        reqResp.setRespId(respId);
        reqResp.setCode(status.getCode());
        reqResp.setMsg(status.getMsg());
        return reqResp;
    }
    public static <T> RpcResponse<T> fail(String respId, String msg){
        RpcResponse<T> reqResp = new RpcResponse<>();
        reqResp.setRespId(respId);
        reqResp.setCode(RpcRespStatusEnums.FAIL.getCode());
        reqResp.setMsg(msg);
        return reqResp;
    }

}
